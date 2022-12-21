package com.job.jobs;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;

public class CheckLifeCycleJob implements Runnable{

    Map<Task, ScheduledFuture> featuresMap;

    public CheckLifeCycleJob(Map<Task, ScheduledFuture> featuresMap){
        this.featuresMap = featuresMap;
    }

    @Override
    public void run() {
        featuresMap.entrySet().stream().forEach((entry) ->
                System.out.println("Job with id: " + entry.getKey().getJobId() + " is running - " + !entry.getValue().isDone()));
    }
}
