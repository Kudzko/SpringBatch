package by.kudko;

import by.kudko.classgenerator.SourceCode;
import by.kudko.model.Hotel;
import by.kudko.repository.HotelRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;


@Log4j2
@SpringBootApplication
public class SpringBatchApplication {

    private static final String CLASS_NAME = "Hotel";
    public static void main(String[] args) {
//        String currentDir = new File("").getAbsolutePath();
//        File file = new File(currentDir + "\\BatchStartTechPrimers\\src\\main\\java\\by\\kudko\\model\\Hotel.java");
//        System.out.println(file.getAbsolutePath());
//
//
//        System.out.println();
//        try {
//            String str = "" +
//                    "package by.kudko.model;\n" +
//                    "\n" +
//                    "\n" +
//                    "import lombok.AllArgsConstructor;\n" +
//                    "import lombok.Data;\n" +
//                    "import lombok.NoArgsConstructor;\n" +
//                    "\n" +
//                    "import javax.persistence.Entity;\n" +
//                    "\n" +
//                    "@Data\n" +
//                    "@NoArgsConstructor\n" +
//                    "@AllArgsConstructor\n" +
//                    "@Entity\n" +
//                    "public class Hotel extends BEntity {\n" +
//                    "\n" +
//                    "    private String name;\n" +
//                    "    private Integer stars;\n" +
//                    "\n" +
//                    "    public Hotel(int id, String name, Integer stars) {\n" +
//                    "        super(id);\n" +
//                    "        this.name = name;\n" +
//                    "        this.stars = stars;\n" +
//                    "    }\n" +
//                    "}\n" +
//                    "\n";
//            byte[] bs = str.getBytes();
//            Files.write(file.toPath(),bs);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try{

//            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//
//            String classCode = "" +
//                    "package by.kudko.model;\n" +
//                    "\n" +
//                    "\n" +
//                    "import lombok.AllArgsConstructor;\n" +
//                    "import lombok.Data;\n" +
//                    "import lombok.NoArgsConstructor;\n" +
//                    "\n" +
//                    "import javax.persistence.Entity;\n" +
//                    "\n" +
//                    "@Data\n" +
//                    "@NoArgsConstructor\n" +
//                    "@AllArgsConstructor\n" +
//                    "@Entity\n" +
//                    "public class Hotel extends BEntity {\n" +
//                    "\n" +
//                    "    private String name;\n" +
//                    "    private Integer stars;\n" +
//                    "\n" +
//                    "    public Hotel(int id, String name, Integer stars) {\n" +
//                    "        super(id);\n" +
//                    "        this.name = name;\n" +
//                    "        this.stars = stars;\n" +
//                    "    }\n" +
//                    "}\n" +
//                    "\n";
//            SourceCode sourceCode = new SourceCode(CLASS_NAME, classCode);
//
//            JavaCompiler.CompilationTask task = compiler.getTask(null, null, null, null, null, Collections.singletonList(sourceCode));
//
//            System.out.println(task.call());
        }catch (Exception e){

            e.printStackTrace();
        }

        SpringApplication.run(SpringBatchApplication.class, args);
    }

}
