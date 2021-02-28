FROM openjdk:11

EXPOSE 8761

ADD ./build/libs/*.jar eureka.jar

ENTRYPOINT ["java","-jar","/eureka.jar"]
