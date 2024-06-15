package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
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
@RequestMapping("skills")
public class SkillController {
    @Autowired
    private SkillRepository skillRepository;

    @GetMapping("add")
    public String displayAddSkillForm(Model model) {
        model.addAttribute("skill",new Skill());
        return "skills/add";
    }

    @PostMapping("add")
    public String processAddSkillForm(@ModelAttribute @Valid Skill newSkill,
                                      Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "skills/add";
        }

        //save the valid skill obj to the skill Repository.
        skillRepository.save(newSkill);

        return "redirect:/skills/";
    }
    @GetMapping("/")
    //@RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model){
        List<Skill> skills = (List<Skill>) skillRepository.findAll();  //fetch all skills from db
        model.addAttribute("skills", skills);  //Add skills list to the model
        return "skills/index"; //returns skills/index.html (thymeleaf)
    }



    @GetMapping("view/{skillId}")
    public String displayViewSkill(Model model, @PathVariable int skillId) {

        // Optional optEmployer = null; // remove null and replace using findById()
        Optional<Skill> optSkill = skillRepository.findById(skillId); //Retrieves employer by id.
        if (optSkill.isPresent()) {
            Skill skill = optSkill.get();
            model.addAttribute("skill", skill);
            return "skills/view";
        } else {
            return "redirect:/skills/";
        }

    }

}
