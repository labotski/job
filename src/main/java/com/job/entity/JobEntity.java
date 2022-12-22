package com.job.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
// Test job entity, we can add/remove fields which we need on persistence layer
public class JobEntity {

    @Id
    @NotBlank
    private String jobId;

    @NotBlank
    private String payload;
}
