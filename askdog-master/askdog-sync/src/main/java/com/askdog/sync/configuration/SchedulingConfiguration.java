package com.askdog.sync.configuration;

import com.askdog.sync.model.entity.BatchSchedule;
import com.askdog.sync.model.repository.BatchScheduleRepository;
import com.google.common.collect.Iterables;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

import static org.springframework.batch.core.BatchStatus.COMPLETED;

@Configuration
@EnableScheduling
public class SchedulingConfiguration {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private List<Job> jobs;

    @Autowired
    private BatchScheduleRepository batchScheduleRepository;

    @Scheduled(cron = "${askdog.job.index-sync.cron}")
    public void updateIndex() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        BatchSchedule batchSchedule = getBatchSchedule();
        for (Job job : jobs) {
            JobExecution execution = jobLauncher.run(job, new JobParametersBuilder()
                    .addDate("date_from", batchSchedule.getDateFrom())
                    .addDate("date_to", batchSchedule.getDateTo())
                    .toJobParameters()
            );
            updateBatchSchedule(batchSchedule, execution);
        }
    }

    private BatchSchedule getBatchSchedule() {
        BatchSchedule batchSchedule = Iterables.getFirst(batchScheduleRepository.findAll(), null);
        if (batchSchedule == null) {
            batchSchedule = new BatchSchedule();
        } else {
            batchSchedule.setDateFrom(batchSchedule.getDateTo());
        }

        batchSchedule.setDateTo(new Date());
        return batchSchedule;
    }

    private void updateBatchSchedule(BatchSchedule batchSchedule, JobExecution execution) {
        if (execution.getStatus() == COMPLETED) {
            batchScheduleRepository.save(batchSchedule);
        }
    }

}
