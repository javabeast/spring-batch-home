package com.techhome.springbatchhome.config;

import com.techhome.springbatchhome.model.User;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;


@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<User> itemReader,
                   ItemProcessor<User,User> itemProcessor,
                   ItemWriter<User> itemWriter){

        Step step = stepBuilderFactory.get("FILE-LOAD")
                .<User,User>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        Job job = jobBuilderFactory.get("FILE-LOAD")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();

        return job;
    }

    @Bean
    public FlatFileItemReader<User> fileItemReader(@Value("${input}") Resource resource){
        FlatFileItemReader<User> fileItemReader = new FlatFileItemReader<User>();
        fileItemReader.setResource(resource);
        fileItemReader.setName("CSV-Reader");
        fileItemReader.setLinesToSkip(1);
        fileItemReader.setLineMapper(lineMapper());
        return fileItemReader;
    }

    @Bean
    public LineMapper<User> lineMapper() {
        DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.equals(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[] {"id","name","dept","salary"});

        BeanWrapperFieldSetMapper<User> wrapperFieldSetMapper = new BeanWrapperFieldSetMapper();
        wrapperFieldSetMapper.setTargetType(User.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(wrapperFieldSetMapper);

        return defaultLineMapper;
    }
}
