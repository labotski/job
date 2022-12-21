package com.job.app;

public enum ExecutionType{

    IMMEDIATELY(0),
    PERIODIC_1_HOUR(1),
    PERIODIC_2_HOUR(2),
    PERIODIC_6_HOUR(6),
    PERIODIC_12_HOUR(12);

    int hour;

    ExecutionType(int hour) {
        this.hour = hour;
    }
}
