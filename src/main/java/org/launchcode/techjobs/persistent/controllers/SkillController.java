package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("skill")
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    //Handler to display new skills
    @GetMapping("add")
    public String displayAddSkillForm(Model model) {
        model.addAttribute(new Skill());
        return "skills/add";
    }

    // Handler to process the form for adding a new employer
    @PostMapping("add")
    public String processAddSkillForm(@ModelAttribute @Valid Skill newSkill,
                                         Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "skills/add";
        }

        skillRepository.save(newSkill); // Saving new skill using the repository
        return "redirect:";
    }

    // Handler to display details of an individual skill
    @GetMapping("view/{skillId}")
    public String displayViewSkill(Model model, @PathVariable int skillId) {
        Optional<Skill> optSkill = skillRepository.findById(skillId); // Retrieve skill by ID
        if (optSkill.isPresent( )) {
            Skill skill = optSkill.get( );
            model.addAttribute("skill", skill);
            return "skills/view";
        } else {
            // Handle case where skill is not found
            return "redirect:/skill";
        }

    }

    // Handler to display a list of all employers
    @GetMapping("")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        List<Skill> skills = (List<Skill>) skillRepository.findAll();
        model.addAttribute("skills",skills); // Passing employers list to the view
        return "skills/index";
    }

}
