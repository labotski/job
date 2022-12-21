package com.job.jobs;

import com.job.app.ExecutionType;
import com.job.app.JobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Task{

    private int jobId;

    private JobType jobType;

    private ExecutionType executionType;

    private Runnable job;

}
