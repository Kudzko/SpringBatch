package by.kudko.SpringBatch.spring;

import by.kudko.SpringBatch.batchprocessing.JobCompletionNotificationListener;
import by.kudko.SpringBatch.batchprocessing.UserItemProcessor;
import by.kudko.SpringBatch.model.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

   // For starters, the @EnableBatchProcessing annotation adds many critical beans that support jobs
    // and save you a lot of leg work

    @Bean
    public FlatFileItemReader<User> reader() {
        return new FlatFileItemReaderBuilder<User>()
                .name("personItemReader")
                .resource(new ClassPathResource("travel_agency_user.csv"))
                .delimited()
                .names(new String[]{"name", "surname", "nickName"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<User>() {{
                    setTargetType(User.class);
                }})
                .build();
    }

    @Bean
    public UserItemProcessor processor() {
        return new UserItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<User> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<User>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO user (name, surname, nickname) VALUES (:name, :surname)")
                .dataSource(dataSource)
                .build();
    }

    /*
    * chunk of code defines the input, processor, and output.
        * reader() creates an ItemReader. It looks for a file called sample-data.csv and
             parses each line item with enough information to turn it into a Person.
        * processor() creates an instance of the PersonItemProcessor that you defined earlier,
             meant to converth the data to upper case.
        * write(DataSource) creates an ItemWriter. This one is aimed at a JDBC destination and
            automatically gets a copy of the dataSource created by @EnableBatchProcessing.
            It includes the SQL statement needed to insert a single Person, driven by Java bean properties.
     */


    //The last chunk (from src/main/java/com/example/batchprocessing/BatchConfiguration.java) shows the actual
    // job configuration:

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<User> writer) {
        return stepBuilderFactory.get("step1")
                .<User, User> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }
}
