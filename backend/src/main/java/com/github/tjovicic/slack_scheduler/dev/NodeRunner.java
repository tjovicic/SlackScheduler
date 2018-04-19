package com.github.tjovicic.slack_scheduler.dev;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.devtools.restart.Restarter;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class NodeRunner implements CommandLineRunner {
    private final Environment environment;

    @Autowired
    public NodeRunner(final Environment environment) {
        this.environment = environment;
    }

    @Override
    public void run(final String... args) throws Exception {
        if (this.environment.acceptsProfiles("development")) {
            final AtomicBoolean registered = (AtomicBoolean) Restarter.getInstance().getOrAddAttribute("nodeStarted", AtomicBoolean::new);
            final boolean alreadyRun = registered.getAndSet(true);
            if (!alreadyRun) {
                nodeStart();
            }
        }
    }

    private void nodeStart() throws IOException {
        ProcessExecutor process = new ProcessExecutor()
                .directory(new File("frontend"))
                .command("npm", "start")
                .redirectOutput(Slf4jStream.of(LoggerFactory.getLogger("node")).asInfo())
                .redirectError(Slf4jStream.of(LoggerFactory.getLogger("node")).asError());

        if (isWindows()) {
            process = process.command("cmd", "/c", "npm", "start");
        }

        process.start();
    }

    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
}
