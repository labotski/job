package com.job.app;


import com.job.exceptions.DuplicateFoundException;
import com.job.jobs.Task;
import com.job.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class Job {

    @Autowired
    JobRepository jobRepository; // we can use data layer in future if we need

    public static final int POOL = 3; // pool size

    private void checkJobsWithTheSameID(List<Task> jobs) throws DuplicateFoundException {
        Set<Integer> items = new HashSet<>();
        Set<Task> set = jobs.stream().filter(n -> !items.add(n.getJobId())).collect(Collectors.toSet());
        if(!set.isEmpty()){
            throw new DuplicateFoundException("Duplicates founded:"+ set);
        }
    }

    public Map<Task, ScheduledFuture> setupJobs(List<Task> jobs, int poolSize) throws DuplicateFoundException {
        checkJobsWithTheSameID(jobs);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(poolSize);
        Map<Task, ScheduledFuture> featuresMap = new HashMap<>();
        for(Task job : jobs){
            switch (job.getExecutionType()){
                case IMMEDIATELY:
                    featuresMap.put(job, scheduledExecutorService.schedule(job.getJob(), 0, TimeUnit.SECONDS));
                    break;
                case PERIODIC_1_HOUR:
                    featuresMap.put(job, scheduledExecutorService.scheduleWithFixedDelay(job.getJob(), 0, 1, TimeUnit.SECONDS));
                    break;
                case PERIODIC_2_HOUR:
                    featuresMap.put(job, scheduledExecutorService.scheduleWithFixedDelay(job.getJob(), 0, 2, TimeUnit.SECONDS));
                    break;
                case PERIODIC_6_HOUR:
                    featuresMap.put(job, scheduledExecutorService.scheduleWithFixedDelay(job.getJob(), 0, 6, TimeUnit.SECONDS));
                    break;
                case PERIODIC_12_HOUR:
                    featuresMap.put(job, scheduledExecutorService.scheduleWithFixedDelay(job.getJob(), 0, 12, TimeUnit.SECONDS));
                    break;
            }
        }
        return featuresMap;
    }
}
