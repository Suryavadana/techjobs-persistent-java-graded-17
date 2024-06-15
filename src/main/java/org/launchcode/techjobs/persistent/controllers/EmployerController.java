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
@RequestMapping("/employers")
public class EmployerController {

    @Autowired
    private EmployerRepository employerRepository;

    @GetMapping("add")
    public String displayAddEmployerForm(Model model) {
        model.addAttribute(new Employer());
        return "employers/add";
    }

    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid Employer newEmployer,
                                         Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "employers/add";
        }

        //save the valid employer obj to the Employer Repository.
        employerRepository.save(newEmployer);

        return "redirect:/employers/";
    }
    @GetMapping("/")
    //@RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model){
        List<Employer> employers = (List<Employer>) employerRepository.findAll();  //fetch all employers from db
        model.addAttribute("employers", employers);  //Add employers list to the model
        return "employers/index"; //returns employers/index.html (thymeleaf)
    }



    @GetMapping("view/{employerId}")
    public String displayViewEmployer(Model model, @PathVariable int employerId) {

        // Optional optEmployer = null; // remove null and replace using findById()
        Optional<Employer> optEmployer = employerRepository.findById(employerId); //Retrieves employer by id.
        if (optEmployer.isPresent()) {
            Employer employer = optEmployer.get();
            model.addAttribute("employer", employer);
            return "employers/view";
        } else {
            return "redirect:/employers/";
        }

    }
}
