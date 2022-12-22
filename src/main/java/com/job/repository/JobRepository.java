package com.job.repository;

import com.job.entity.JobEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends CrudRepository<JobEntity, String> {

    // We can add additional persistance methods which we need

}
