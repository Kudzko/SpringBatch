package by.kudko.config;

import by.kudko.model.Hotel;
import by.kudko.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

@Log4j2
@Configuration
@EnableBatchProcessing
@PropertySource("classpath:application.properties")
public class SpringBachConfig {

    @Value("${app.batch.chunk}")
    private Integer chunk;

    //+++ Jobs
    @Bean
    public Job simpleJob(JobBuilderFactory jobBuilderFactory, Step step1) {
        Job job = jobBuilderFactory.get("ETL-Load")
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();
        return job;
    }

    @Bean
    Job anotherJob(JobBuilderFactory jobBuilderFactory, @Qualifier("step2") Step step2) {
        log.info("anotherJob");
        return jobBuilderFactory.get("ETL-two-steps-job")
                .incrementer(new RunIdIncrementer())
                .flow(step2)
                .end()
                .build();
    }
    //--- Jobs

    @Bean
    public FlatFileItemReader<User> itemReader(@Value("${inputFile}") Resource resource) {
        System.out.println(resource);
        FlatFileItemReader<User> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(resource);
        flatFileItemReader.setName("flat-item-reader-user");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());

        return flatFileItemReader;
    }

    @Bean
    public FlatFileItemReader<Hotel> secondItemReader(@Value("${inputFileTwo}") Resource resource) {
        System.out.println(resource);
        FlatFileItemReader<Hotel> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(resource);
        flatFileItemReader.setName("flat-item-reader-hotel");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper2());

        return flatFileItemReader;
    }

    @Bean
    public LineMapper<User> lineMapper() {
        DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"id", "name", "surname", "nickName"});

        BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(User.class);

        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        defaultLineMapper.setLineTokenizer(lineTokenizer);

        return defaultLineMapper;
    }

    @Bean
    public LineMapper<Hotel> lineMapper2() {
        DefaultLineMapper<Hotel> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"id", "name", "stars"});

        BeanWrapperFieldSetMapper<Hotel> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Hotel.class);

        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        log.info("lineMapper2");
        return defaultLineMapper;
    }

    //+++ Steps
    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<User> itemReader
            , ItemProcessor<User, User> itemProcessor, ItemWriter<User> itemWriter) {
        return stepBuilderFactory.get("ETL-load-file-step")
                .<User, User>chunk(chunk)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    @Bean
    public Step step2(StepBuilderFactory stepBuilderFactory,@Qualifier("secondItemReader") ItemReader<Hotel> itemReader,
                      ItemWriter<Hotel> itemWriter) {
        log.info("step2");

        return stepBuilderFactory.get("ETL-load-file-step2")
                .<Hotel, Hotel>chunk(chunk)
                .reader(itemReader)
                .writer(itemWriter)
                .build();

    }
    //--- Steps
}
