package by.kudko.config;

import by.kudko.classgenerator.ClassCreator;
import by.kudko.classgenerator.Properties;
import by.kudko.model.TempEntity;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

@Log4j2
@Configuration
@EnableBatchProcessing
@PropertySource("classpath:application.properties")
public class SpringBachConfig {
    @Autowired
    private Properties properties;

    @Value("${app.batch.chunk}")
    private Integer chunk;
    @Value("${linesToSkipInCsvFile}")
    private Integer linesToSkip;
    @Value("${columnNames}")
    private String columnNames;

    //+++ Jobs
    @Bean
    public Job simpleJob(JobBuilderFactory jobBuilderFactory, Step step1) {
        Job job = jobBuilderFactory.get("ETL-Load")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
        return job;
    }


    //--- Jobs


    @Bean
    public FlatFileItemReader<TempEntity> itemReader(@Value("${inputFileTwo}") Resource resource) {
        System.out.println(resource);
        FlatFileItemReader<TempEntity> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(resource);
        flatFileItemReader.setName("flat-item-reader-hotel");
        flatFileItemReader.setLinesToSkip(linesToSkip);
        flatFileItemReader.setLineMapper(lineMapper());

        return flatFileItemReader;
    }

    @Bean
    @Lazy
    public LineMapper<TempEntity> lineMapper() {
        DefaultLineMapper<TempEntity> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(columnNames.split(","));

        BeanWrapperFieldSetMapper<TempEntity> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(TempEntity.class);

        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        defaultLineMapper.setLineTokenizer(lineTokenizer);

        return defaultLineMapper;
    }

    //+++ Steps
    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<TempEntity> itemReader
            , ItemProcessor<TempEntity, TempEntity> itemProcessor, ItemWriter<TempEntity> itemWriter) {
        return stepBuilderFactory.get("ETL-load-file-step")
                .<TempEntity, TempEntity>chunk(chunk)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }


    //--- Steps
}
