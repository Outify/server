package com.mcc.outify.config;

import com.mcc.outify.weathers.batch.LocationReader;
import com.mcc.outify.weathers.batch.WeatherDataProcessor;
import com.mcc.outify.weathers.batch.WeatherDataWriter;
import com.mcc.outify.weathers.entity.LocationEntity;
import com.mcc.outify.weathers.entity.WeatherDataEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WeatherBatchConfig {

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

    private final LocationReader locationReader;

    private final WeatherDataProcessor weatherDataProcessor;

    private final WeatherDataWriter weatherDataWriter;

    @Bean
    public Job weatherDataJob() {
        return new JobBuilder("weatherDataJob", jobRepository)
                .start(weatherDataStep())
                .build();
    }

    @Bean
    public Step weatherDataStep() {
        return new StepBuilder("weatherDataStep", jobRepository)
                .<LocationEntity, List<WeatherDataEntity>>chunk(10, transactionManager)
                .reader(locationReader)
                .processor(weatherDataProcessor)
                .writer(weatherDataWriter)
                .faultTolerant()
                .skip(Exception.class)
                .skipLimit(100)
                .listener(skipListener())
                .build();
    }

    @Bean
    public SkipPolicy skipPolicy() {
        return new AlwaysSkipItemSkipPolicy();
    }

    @Bean
    public SkipListener<WeatherDataEntity, WeatherDataEntity> skipListener() {
        return new SkipListener<>() {
            @Override
            public void onSkipInRead(Throwable t) {
                System.err.println("Skipped due to read error: " + t.getMessage());
            }

            @Override
            public void onSkipInWrite(WeatherDataEntity item, Throwable t) {
                System.err.println("Skipped due to write error on item " + item + ": " + t.getMessage());
            }

            @Override
            public void onSkipInProcess(WeatherDataEntity item, Throwable t) {
                System.err.println("Skipped due to process error on item " + item + ": " + t.getMessage());
            }
        };

    }

}
