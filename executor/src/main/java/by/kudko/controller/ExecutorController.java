package by.kudko.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@Log4j2
@RestController
public class ExecutorController {
    public static final String COMMAND = "java D:\\gitRep\\SpringBatch\\importer\\target\\classes\\by\\kudko\\ImporterApplication";

    @GetMapping("/load")
    public String load() {
        Runtime rt = Runtime.getRuntime();
        try {
            Process pr = rt.exec(COMMAND);
            System.out.println(pr.exitValue());
        } catch (IOException e) {
            log.warn("Command can not execute", e);
        }
        log.debug("execConsoleCommand");

        return "Hello";
    }

}
