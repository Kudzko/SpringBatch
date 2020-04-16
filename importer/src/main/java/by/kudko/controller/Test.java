package by.kudko.controller;

import java.io.IOException;

public class Test {
    public static void main(String[] args) {
//        public static final String CLASS_NAME = "TempEntity.java";
//        public static final String CLASSPATH_TO_COMPILE = "\\importer\\src\\main\\java\\by\\kudko\\model\\" + CLASS_NAME;
//        public static final String LOMBOK_CLASSPATH = "D:\\gitRep\\SpringBatch\\importer\\src\\main\\resources\\lombok-1.18.12.jar";
//        public static final String COMMAND = "javac -cp %s %s";
        String command = "javac -d  gitRep\\SpringBatch\\importer\\target\\classes -cp  gitRep\\SpringBatch\\importer\\src\\main\\java\\;gitRep\\SpringBatch\\importer\\src\\main\\resources\\lombok-1.18.12.jar;gitRep\\SpringBatch\\importer\\src\\main\\resources\\javax.persistence-api-2.2.jar gitRep/SpringBatch/importer/src/main/java/by/kudko/model/TempEntity.java";

//        String command = "javac Hello.java";
//        ClassCreator.execConsoleCommand(command);
//       String command2 = "java Hello";
//        ClassCreator.execConsoleCommand(command);

        Runtime rt = Runtime.getRuntime();
        try {
            Process pr = rt.exec(command);
            Thread.sleep(2000);
            System.out.println("Process: " + pr.exitValue());
        } catch (IOException | InterruptedException e) {
            System.out.println("Command can not execute");
            e.printStackTrace();
        }
        System.out.println("execConsoleCommand");
    }
    /*
    * javac -d gitRep\SpringBatch\importer\target\classes -cp gitRep\SpringBatch\importer\src\main\java\ gitRep/SpringBatch/importer/src/main/java/by/kudko/controller/Test.java
    * java -cp gitRep\SpringBatch\importer\target\classes by/kudko/controller/Test
     */
}
