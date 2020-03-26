package by.kudko.SpringBatch.batchprocessing;

import by.kudko.SpringBatch.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Log4j2
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private final JdbcTemplate jdbcTemplate;

//    @Autowired
//    @Qualifier("taDataSource")
//    private DataSource dataSource;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

        //    jdbcTemplate.setDataSource(dataSource);
            jdbcTemplate.query("SELECT name, surname, nickName FROM user",
                    (rs, row) -> new User(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3))
            ).forEach(user -> log.info("Found <" + user + "> in the database."));
        }
    }
}