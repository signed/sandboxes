package com.github.signed.sandboxes.spring.data.bg;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long jobKey;
    public JobType type;
    public JobState state;
    public Long referenceKey;
}
