package com.mcc.outify.weathers;

import com.mcc.outify.weathers.entity.WeatherSourceEntity;
import com.mcc.outify.weathers.repository.WeatherSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class WeatherDataRunner implements ApplicationRunner {

    private final JobLauncher jobLauncher;

    private final Job weatherDataJob;

    private final WeatherSourceRepository weatherSourceRepository;

    @Override
    public void run(ApplicationArguments args) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(this::weatherAPIScheduled, 10, TimeUnit.SECONDS);
    }

    @Scheduled(cron = "0 5 15 * * ?")
    public void weatherAPIScheduled() {
        saveSources();
        executeWeatherJob();
    }

    private void saveSources() {
        Arrays.stream(WeatherSourceEntity.WeatherSource.values()).forEach(sourceType -> {
            WeatherSourceEntity weatherSource = weatherSourceRepository.findBySource(sourceType);
            if (weatherSource == null) {
                weatherSource = new WeatherSourceEntity(sourceType);
                weatherSourceRepository.save(weatherSource);
            }
        });
    }

    private void executeWeatherJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                            .toJobParameters();
            jobLauncher.run(weatherDataJob, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("날씨 데이터 배치 작업 실행 중 오류가 발생했습니다.\n" + "Caused by: " + e.getMessage(), e);
        }
    }

}