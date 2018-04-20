package com.github.tjovicic.slack_scheduler.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/**")
      .addResourceLocations("classpath:/static/")
      .resourceChain(false)
      .addResolver(new PushStateResourceResolver());
  }

  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins("*")
      .allowedHeaders("Content-Type")
      .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS");
  }

  private class PushStateResourceResolver implements ResourceResolver {
    private final Resource index = new ClassPathResource("/static/index.html");
    private final List<String> handledExtensions = Arrays.asList("html", "js", "json", "csv", "css", "png", "svg", "eot", "ttf", "woff", "appcache", "jpg", "jpeg", "gif", "ico");
    private final List<String> ignoredPaths = Collections.singletonList("api");

    @Override
    public Resource resolveResource(final HttpServletRequest request, final String requestPath, final List<? extends Resource> locations, final ResourceResolverChain chain) {
      return resolve(requestPath, locations);
    }

    @Override
    public String resolveUrlPath(final String resourcePath, final List<? extends Resource> locations, final ResourceResolverChain chain) {
      final Resource resolvedResource = resolve(resourcePath, locations);

      if (resolvedResource == null) {
        return null;
      }

      try {
        return resolvedResource.getURL().toString();
      } catch (final IOException e) {
        return resolvedResource.getFilename();
      }
    }

    private Resource resolve(final String requestPath, final List<? extends Resource> locations) {
      if (isIgnored(requestPath)) {
        return null;
      }

      if (isHandled(requestPath)) {
        return locations.stream()
          .map(loc -> createRelative(loc, requestPath))
          .filter(resource -> resource != null && resource.exists())
          .findFirst()
          .orElseGet(null);
      }

      return this.index;
    }

    private Resource createRelative(final Resource resource, final String relativePath) {
      try {
        return resource.createRelative(relativePath);
      } catch (final IOException e) {
        return null;
      }
    }

    private boolean isIgnored(final String path) {
      return this.ignoredPaths.contains(path);
    }

    private boolean isHandled(final String path) {
      final String extension = StringUtils.getFilenameExtension(path);
      return this.handledExtensions.stream().anyMatch(ext -> ext.equals(extension));
    }
  }
}
