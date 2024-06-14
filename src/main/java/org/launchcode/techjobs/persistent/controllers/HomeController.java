package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.*;


/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("job")
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SkillRepository skillRepository;

    @RequestMapping("/")
    public String index(Model model) {

        model.addAttribute("title", "MyJobs");

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());
        model.addAttribute(new Job());
        return "add";
    }
    @PostMapping("/jobs/add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                    Errors errors, Model model,
                                    @RequestParam int employerId,
                                    @RequestParam(name = "skills", required = false) List<Integer> selectedSkillIds) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            return "add";
        }

        Optional<Employer> optEmployer = employerRepository.findById(employerId);
        if (optEmployer.isPresent()) {
            Employer employer = optEmployer.get();
            newJob.setEmployer(employer);
        } else {
            // Redirect if employer is not found
            return "redirect:/jobs/add";
        }

        // Ensure that selectedSkillIds is not null and not empty
        if (selectedSkillIds != null && !selectedSkillIds.isEmpty()) {
            List<Skill> skillSet = new ArrayList<>();
            for (Integer skillId : selectedSkillIds) {
                Optional<Skill> optSkill = skillRepository.findById(skillId);
                optSkill.ifPresent(skillSet::add);
            }
            newJob.setSkills(skillSet);
        } else {
            // Redirect if no skills are selected
            return "redirect:/jobs/add";
        }

        jobRepository.save(newJob);
        return "redirect:/job"; // Redirect after adding a job
    }



    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {

        return "view";
    }

}