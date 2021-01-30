package com.springbatchdbdb.config;

import com.springbatchdbdb.batch.UserRowMapper;
import com.springbatchdbdb.dto.UserDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig extends DefaultBatchConfigurer {

    @Autowired
    private Environment env;

    @Override
    public void setDataSource(DataSource dataSource) {
    }

    @Bean
    public Job job(
            JobBuilderFactory jobBuilderFactory,
            StepBuilderFactory stepBuilderFactory,
            ItemReader<UserDTO> itemReader,
            ItemProcessor<UserDTO, UserDTO> itemProcessor,
            ItemWriter<UserDTO> itemWriter) {

        Step step = stepBuilderFactory.get("ETL-file-load")
                .<UserDTO, UserDTO>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        return jobBuilderFactory.get("ETL-load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("");
        dataSource.setUrl("");
        dataSource.setUsername("");
        dataSource.setPassword("");

        return dataSource;
    }

    @Bean
    public JdbcCursorItemReader<UserDTO> reader() {
        JdbcCursorItemReader<UserDTO> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource());
        reader.setSql("SELECT id, name FROM user");
        reader.setRowMapper(new UserRowMapper());

        return reader;
    }

}
