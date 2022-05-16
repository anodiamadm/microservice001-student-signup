FROM openjdk:8
EXPOSE 8444
ADD target/microservice001-student-signup.jar microservice001-student-signup.jar
ENTRYPOINT ["java","-jar","/microservice001-student-signup.jar"]