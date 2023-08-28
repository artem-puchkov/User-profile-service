FROM amazoncorretto:17-alpine
ENV APP_HOME=/opt/app
ARG JAR_FILE=./build/libs/user-profile-service-*.jar
ARG KEYSTORE=./security/e2e_testing_keystore.p12
WORKDIR $APP_HOME
COPY $JAR_FILE app.jar
COPY $KEYSTORE security/e2e_testing_keystore.p12
EXPOSE 8080
EXPOSE 8443
ENTRYPOINT ["java", "-jar", "app.jar"]