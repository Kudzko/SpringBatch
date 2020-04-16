package by.kudko.classgenerator;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2
@Component
@PropertySource("classpath:newEntity.properties")
public class ClassCreator {
    public static final String CLASS_NAME = "TempEntity.java";
    public static final String CLASSPATH_TO_COMPILE = "\\importer\\src\\main\\java\\by\\kudko\\model\\" + CLASS_NAME;
    public static final String LOMBOK_CLASSPATH = "D:\\gitRep\\SpringBatch\\importer\\src\\main\\resources\\lombok-1.18.12.jar";
    public static final String COMMAND = "javac -cp %s %s";
    @Autowired
    private Properties properties;


    /**
     * Changes content in class in sources and compiles in runtime
     *
     * @throws Exception if can't read class in domain (SomeEntity.java) or rewrite
     */
    public void createClass()  {
        parseConfig(properties.getColumnsNameAndType());
        String currentDir = new File("").getAbsolutePath();
        log.debug("app directory: " + currentDir);
        File file = new File(currentDir + CLASSPATH_TO_COMPILE);
        log.debug("absolute path to class for runtime compilation: " + file.getAbsolutePath());

        String codeStr = buldClass();
        try {
            byte[] bs = codeStr.getBytes();
            Files.write(file.toPath(), bs);

        } catch (IOException e) {
            e.printStackTrace();
        }
String command = "javac -d importer\\target\\classes -cp importer\\src\\main\\java\\;importer\\src\\main\\resources\\lombok-1.18.12.jar;gitRep\\SpringBatch\\importer\\src\\main\\resources\\javax.persistence-api-2.2.jar gitRep/SpringBatch/importer/src/main/java/by/kudko/model/TempEntity.java";
        execConsoleCommand(command);
        log.debug("createClass: operated");
    }

    /**
     * read txt file in resources with template of class to runtime compile
     *
     * @return String of file content
     */
    private String readClassTemplate() {
        File file = new File(properties.getTextTemplateForClass());
        String codeStr = null;
        try {
            codeStr = ClassTemplateReader.readFileToString(file);
        } catch (IOException e) {
            log.warn("Couldn't read template file", e);
            e.printStackTrace();
        }
        log.debug("readClassTemplate: operated" + codeStr);
        return codeStr;
    }

    private String buldClass() {
        String[] classFields = properties.getClassFields().toArray(new String[0]);
        StringBuffer toClass = new StringBuffer();
        for (String feild:classFields){
            toClass.append("private ");
            toClass.append(feild + "\n");
        }
        String string = String.format(readClassTemplate(), properties.getTableName(),toClass.toString());
        log.debug("buldClass: operated" + string);
        return string;
    }

    private void parseConfig(String typesColumns) {
        List<String> columnNames = new ArrayList<>();
        List<String> classFields = new ArrayList<>();
        String[] typeNames = typesColumns.trim().split(properties.getPairSplit());
        for (int i = 0; i < typeNames.length; i++) {
            classFields.add(typeNames[i]
                            .replace(properties.getSplit(), " ")
                            .concat(";"));

            int indexSplitter = typeNames[i].indexOf(properties.getSplit());
            columnNames.add(typeNames[i].substring(++indexSplitter));
        }
        String[] a = new String[0];
        properties.setColumnNames(columnNames.toArray(a));
        properties.setClassFields(classFields);
        for (String column : columnNames.toArray(a)) {
            log.debug("parseConfig: " + column);
        }

        log.debug("parseConfig: end");
    }

    public static void execConsoleCommand(String command) {

        Runtime rt = Runtime.getRuntime();
        try {
            Process pr = rt.exec(command);
            Thread.sleep(1000);
            log.info("Process: " + pr.exitValue());
        } catch (IOException | InterruptedException e) {
            log.warn("Command can not execute", e);
        }
        log.debug("execConsoleCommand");

    }

    private static String makeCommand() {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"  + String.format(COMMAND, LOMBOK_CLASSPATH, CLASS_NAME));
        String string = String.format(COMMAND, LOMBOK_CLASSPATH, CLASS_NAME);
        log.debug("execConsoleCommand: " + string);
        return string;
    }
}
