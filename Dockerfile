FROM amazoncorretto:17
COPY target/expenseservice-0.0.1-SNAPSHOT.jar expenseservice-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/expenseservice-0.0.1-SNAPSHOT.jar"]
