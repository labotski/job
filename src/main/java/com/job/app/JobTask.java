package com.job.app;

public interface JobTask<V>{
    int jobId();
    JobType jobType();
    ExecutionType executionType();
    Runnable job();
}
