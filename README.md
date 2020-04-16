# SpringBatch

https://spring.io/guides/gs/batch-processing/
https://spring.io/blog/2017/02/01/spring-tips-spring-batch

example of execution from command line
d:\>java -cp gitRep\SpringBatch\importer\target\classes\ by/kudko/ImporterApplication

example of compiling from command line
javac -d gitRep\SpringBatch\importer\target\classes -cp gitRep\SpringBatch\importer\src\main\java\ gitRep/SpringBatch/importer/src/main/java/by/kudko/model/TempEntity.java

absolute path
D:\gitRep\SpringBatch\importer\target\classes\by\kudko\model\TempEntity.class
D:\gitRep\SpringBatch\importer\src\main\java\by\kudko\model\TempEntity.java


compile with some jars

javac -d gitRep\SpringBatch\importer\target\classes -cp gitRep\SpringBatch\importer\src\main\java\;gitRep\SpringBatch\importer\src\main\resources\lombok-1.18.12.jar;gitRep\SpringBatch\importer\src\main\resources\javax.persistence-api-2.2.jar gitRep/SpringBatch/importer/src/main/java/by/kudko/model/TempEntity.java
