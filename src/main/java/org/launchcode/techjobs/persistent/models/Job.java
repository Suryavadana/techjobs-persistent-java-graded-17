package org.launchcode.techjobs.persistent.models;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//TASK-3
//extended Abstract entity, modified code by removing id and name fields.
//Replaced type of th employer string to Employer and refactored constructors and getter and setters
@Entity
public class Job extends AbstractEntity {

    @ManyToOne
    private Employer employer;

    @ManyToMany
    @JoinTable(
            name = "job_skill",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills = new ArrayList<>();
   // private String skills;


    public Job() {
    }

    public Job(Employer employer, List<Skill> skills) {
        this.employer = employer;
        this.skills = skills;
    }
// Getters and setters.

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public void addSkill(Skill skill) {
        this.skills.add(skill);
        skill.getJobs().add(this);
    }

    public void removeSkill(Skill skill) {
        this.skills.remove(skill);
        skill.getJobs().remove(this);
    }
}
