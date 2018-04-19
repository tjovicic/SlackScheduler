Development
- Set _spring.profiles.active_ argument to **development** 
- Start only _Application.java_, frontend server automatically starts after tomcat has started
- Backend runs on port **8080**, frontend on **3000**

Build
- _mvn clean install_ builds entire application in one jar in _backend/target_ folder

Run
- java -jar slack-scheduler-backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=test --slack.webhook.url=https://hooks.slack.com/services/aaa/bbb/ccc --slack.channel=#general