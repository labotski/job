package com.job;

import com.job.app.ExecutionType;
import com.job.app.Job;
import com.job.app.JobType;
import com.job.exceptions.DuplicateFoundException;
import com.job.jobs.CheckCancelJob;
import com.job.jobs.CheckLifeCycleJob;
import com.job.jobs.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


@SpringBootApplication
public class App {

    public static void main(String[] args) throws DuplicateFoundException {
        SpringApplication.run(App.class, args);
        Job job = new Job();

        Task task1 = new Task(1, JobType.JOB_TYPE_FIRST, ExecutionType.IMMEDIATELY, new Runnable() {
            @Override
            public void run() {
                System.out.println("Run task 1");
            }
        });

        Task task2 = new Task(2, JobType.JOB_TYPE_SECOND, ExecutionType.PERIODIC_1_HOUR, new Runnable() {
            @Override
            public void run() {
                System.out.println("Run task 2");
            }
        });

        Task task3 = new Task(3, JobType.JOB_TYPE_THIRD, ExecutionType.PERIODIC_2_HOUR, new Runnable() {
            @Override
            public void run() {
                System.out.println("Run task 3");
            }
        });

        List<Task> jobs = Arrays.asList(task1, task2, task3); // add new jobs to this list
        Map<Task, ScheduledFuture> featuresMap = job.setupJobs(jobs, job.POOL); // run all jobs

        // Executor to check life cycle for all started jobs for every 5 sec
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleWithFixedDelay(new CheckLifeCycleJob(featuresMap), 5, 5, TimeUnit.SECONDS);


        // Run executor for check cancellation tasks, take list of job ids and delay 10 sec
        executor.schedule(new CheckCancelJob(featuresMap, Arrays.asList(2,3)), 10, TimeUnit.SECONDS);
    }
}
