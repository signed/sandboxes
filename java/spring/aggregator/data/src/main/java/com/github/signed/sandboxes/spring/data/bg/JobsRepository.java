package com.github.signed.sandboxes.spring.data.bg;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobsRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {

    @Query("SELECT j " +
            "FROM Job j " +
            "WHERE j.referenceKey IN (SELECT DISTINCT js.referenceKey FROM Job js WHERE js.state=:state)")
    List<Job> letsSeeAllJobsForAReferenceKeyWhereAtLeastOneJobIsIn(@Param("state") JobState state);
}
