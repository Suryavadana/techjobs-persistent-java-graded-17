package org.launchcode.techjobs.persistent.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Employer extends AbstractEntity {

    @NotNull
    @NotEmpty(message = "location cannot be empty.")
    @Size(min=2, max=100 , message = "location must be between 2 and 100 characters")
    private String location;

    @OneToMany//(mappedBy = "employer")
   @JoinColumn(name = "employer_id")
    private List<Job> jobs = new ArrayList<>();
    // No-arg constructor required by Hibernate

    public Employer() {
    }

    public Employer(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
