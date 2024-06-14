package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("employers")
//@RequestMapping("/")
public class EmployerController {

    @Autowired
    private EmployerRepository employerRepository;


    // Handler to display form for adding a new employer
    @GetMapping("add")
    public String displayAddEmployerForm(Model model) {
        model.addAttribute(new Employer());
        return "employers/add";
    }

    // Handler to process the form for adding a new employer
    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid Employer newEmployer,
                                    Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "employers/add";
        }

        // Save the valid Employer object using the employerRepository
        employerRepository.save(newEmployer);

        return "redirect:";
    }

    // Handler to display a list of all employers
    @GetMapping("")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        List<Employer> employers = (List<Employer>) employerRepository.findAll();
        model.addAttribute("employers", employers); // Passing employers list to the view
        return "employers/index";
    }

    @GetMapping("view/{employerId}")
    public String displayViewEmployer(Model model, @PathVariable int employerId) {

       // Optional optEmployer = null;
        Optional<Employer> optEmployer = employerRepository.findById(employerId); // Retrieve employer by ID
        if (optEmployer.isPresent()) {
            Employer employer = (Employer) optEmployer.get();
            model.addAttribute("employer", employer);
            return "employer/view";
        } else {
            return "redirect:/employer";
        }


    }
}
