package by.kudko.controller;

import by.kudko.classgenerator.ClassCreator;
import by.kudko.repository.TempEntityRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@Log4j2
@RestController

public class Trigger {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("simpleJob")
    private Job job;

    @Autowired
    TempEntityRepository tempEntityRepository;
    @Autowired
    private ClassCreator classCreator;

    @GetMapping("/load")
    public BatchStatus load() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        try {
            log.debug("classCreator.createClass in Trigger");
            classCreator.createClass();
        } catch (Exception e) {
            log.warn("Couldn't create and compile class", e);
            e.printStackTrace();
        }
        Map<String, JobParameter> maps = new HashMap<>();
        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters parameters = new JobParameters(maps);

        JobExecution jobExecution = jobLauncher.run(job, parameters);

        log.info("JobExecution: " + jobExecution.getStatus());

        log.info("Batch is Running...");
        while (jobExecution.isRunning()) {
            log.info("...");
        }
        log.info("Batch is finished");
        return jobExecution.getStatus();
    }

}
