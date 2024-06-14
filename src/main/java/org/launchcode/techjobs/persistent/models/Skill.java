package org.launchcode.techjobs.persistent.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Skill extends AbstractEntity {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 1 , max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @Size(max = 500 ,message = "description must at most 500 characters")
    private String description;

    // Many-to-many relationship with Job
    @ManyToMany(mappedBy = "skills")
    private List<Job> jobs = new ArrayList<Job>( ) {
    };
    // No-arg constructor required by JPA
    public Skill() {
    }

    public Skill(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    // Convenience method to add a job to the skills
    public void addJob(Job job) {
        this.jobs.add(job);
        job.getSkills().add(this);
    }

    // Convenience method to remove a job from the skills
    public void removeJob(Job job) {
        this.jobs.remove(job);
        job.getSkills().remove(this);
    }
}
