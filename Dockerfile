FROM tomcat:9.0.12-jre8	
COPY /target/spring_rest_docker.war /usr/local/tomcat/webapps/
