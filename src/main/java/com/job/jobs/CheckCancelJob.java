package com.job.jobs;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

public class CheckCancelJob implements Runnable{
    Map<Task, ScheduledFuture> featuresMap;
    List<Integer> ids;

    public CheckCancelJob(Map<Task, ScheduledFuture> featuresMap, List<Integer> ids){
        this.featuresMap = featuresMap;
        this.ids = ids;
    }

    @Override
    public void run() {
        System.out.println("Canceling jobs ids: "+ ids);
        featuresMap.entrySet().stream().filter(entry -> ids.contains(entry.getKey().getJobId())).forEach(entry -> entry.getValue().cancel(true));
    }
}
