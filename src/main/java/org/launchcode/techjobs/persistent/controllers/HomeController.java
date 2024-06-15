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


import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SkillRepository skillRepository;


    @GetMapping
    public String index(Model model) {

        model.addAttribute("title", "MyJobs");
        model.addAttribute("jobs",jobRepository.findAll());

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute("job", new Job());

        model.addAttribute("employers", employerRepository.findAll());  // fetch all employers
        model.addAttribute("skills", skillRepository.findAll()); //fetch all skills
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                    Errors errors, Model model, @RequestParam int employerId,
                                    @RequestParam(name = "skills", required = false) List<Integer> skillIds){

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            return "add";
        }
        // Fetch the selected employer from the repository
        Optional<Employer> optEmployer = employerRepository.findById(employerId); //Retrieves employer by id.
        if (optEmployer.isPresent()) {
            Employer employer = optEmployer.get();
            // Associate the selected employer with the new job
            newJob.setEmployer(employer);
        }
        // Fetch selected skills from the repository and associate them with the new job
        //if (skillIds != null && !skillIds.isEmpty()) {
        List<Skill> selectedSkills = (List<Skill>) skillRepository.findAllById(skillIds);
        newJob.setSkills(selectedSkills);
        //}

        // Save the job to the database or perform other necessary actions
        jobRepository.save(newJob);

        return "redirect:/";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {

        return "view";
    }

}
