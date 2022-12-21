package com.job.test.app;

import com.job.app.ExecutionType;
import com.job.app.Job;
import com.job.app.JobType;
import com.job.exceptions.DuplicateFoundException;
import com.job.jobs.CheckCancelJob;
import com.job.jobs.Task;
import lombok.extern.java.Log;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import static org.junit.Assert.assertEquals;

@Log
public class JobTest {

    Job job = new Job();

    Task task1 = new Task(1, JobType.JOB_TYPE_FIRST, ExecutionType.IMMEDIATELY, new Runnable() {
        @Override
        public void run() {
            log.info("Run task 1");
        }
    });

    Task task2 = new Task(2, JobType.JOB_TYPE_SECOND, ExecutionType.PERIODIC_1_HOUR, new Runnable() {
        @Override
        public void run() {
            log.info("Run task 2");
        }
    });

    @Test(expected = DuplicateFoundException.class)
    public void testDublicateIds() throws DuplicateFoundException {
        task2.setJobId(1);
        List<Task> jobs = Arrays.asList(task1, task2);
        Map<Task, ScheduledFuture> featuresMap = job.setupJobs(jobs, job.POOL);
    }

    @Test
    public void testCancelTasksAll() throws DuplicateFoundException {
        List<Task> jobs = Arrays.asList(task1, task2);
        Map<Task, ScheduledFuture> featuresMap = job.setupJobs(jobs, job.POOL);
        new CheckCancelJob(featuresMap,Arrays.asList(task1.getJobId(), task2.getJobId())).run();
        long canceledCount = featuresMap.entrySet().stream().filter(i-> i.getValue().isDone()).count();
        assertEquals(2, canceledCount);
    }

    @Test
    public void testCancelTasksOne() throws DuplicateFoundException {
        List<Task> jobs = Arrays.asList(task1, task2);
        Map<Task, ScheduledFuture> featuresMap = job.setupJobs(jobs, job.POOL);
        new CheckCancelJob(featuresMap,Arrays.asList(task1.getJobId())).run();
        long canceledCount = featuresMap.entrySet().stream().filter(i-> i.getValue().isDone()).count();
        assertEquals(1, canceledCount);
    }

    @Test
    public void testImmediatelyTaskDone() throws DuplicateFoundException, InterruptedException {
        List<Task> jobs = Arrays.asList(task1, task2);
        Map<Task, ScheduledFuture> featuresMap = job.setupJobs(jobs, job.POOL);
        Thread.sleep(100); // sleep for 100ms and then check IMMEDIATELY tasks is done
        long doneCount = featuresMap.entrySet().stream().filter(i-> i.getValue().isDone()).count();
        assertEquals(1, doneCount);
    }

}
