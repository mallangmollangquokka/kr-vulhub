FROM openjdk:8u181-jdk

WORKDIR /app

RUN wget -q https://repo1.maven.org/maven2/org/apache/logging/log4j/log4j-core/2.14.1/log4j-core-2.14.1.jar -O log4j-core.jar && \
    wget -q https://repo1.maven.org/maven2/org/apache/logging/log4j/log4j-api/2.14.1/log4j-api-2.14.1.jar -O log4j-api.jar

COPY log4j2.xml .
COPY App.java .

RUN javac -cp "log4j-core.jar:log4j-api.jar" App.java

EXPOSE 8080

CMD ["java", \
     "-Dcom.sun.jndi.ldap.object.trustURLCodebase=true", \
     "-Dcom.sun.jndi.rmi.object.trustURLCodebase=true", \
     "-cp", ".:log4j-core.jar:log4j-api.jar", \
     "App"]