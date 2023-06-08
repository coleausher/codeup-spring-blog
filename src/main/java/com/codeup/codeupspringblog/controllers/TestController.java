package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Park;
import com.codeup.codeupspringblog.models.State;
import com.codeup.codeupspringblog.repositories.ParkRepository;
import com.codeup.codeupspringblog.repositories.StateRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// Tells the compiler that this class will extend HttpServlet and setup our class to control different URI patterns.
@Controller
public class TestController {

    private final ParkRepository parksDao;
    private final StateRepository statesDao;
    public TestController(ParkRepository parksDao, StateRepository statesDao) {
        this.parksDao = parksDao;
        this.statesDao = statesDao;
    }

    // Get Request Mapping for /test
    @GetMapping("/test")
    // Set Response content type to text/html
    @ResponseBody
    // define method that will return text/html
    public String test() {
        return "Hello codeup!";
    }


    // /parks
    @GetMapping("/parks/{park}/{message}")
    @ResponseBody
    public String parks(@PathVariable String park, @PathVariable String message) {
        return "<h1>Welcome to " + park + "!</h1><p>" + message + "</p>";
    }

    @GetMapping("/parks")
    public String parks(Model model) {
        List<Park> parks = parksDao.findAll();
        model.addAttribute("parks", parks);
        return "parks/index";
    }

    @GetMapping("/park")
    public String park(Model model) {
        Park byName = parksDao.findByName("Big Bend National Park");
        model.addAttribute("park", byName);
        return "parks/show";
    }

    @GetMapping("/parks/create")
    public String showParksForm(Model model) {
        // Populate all the states from our database to the select form element in our create form
        model.addAttribute("states", statesDao.findAll());
        // Send a new park object to the form, so we can find the inputs to the fields
        model.addAttribute("park", new Park());
        return "parks/create";
    }


    //EDIT HINT FOR EXERCISE
//    @GetMapping("/parks/{id}/edit")
//    public String showEditParkForm(@PathVariable Long id, Model model) {}
    //1. Find the park ussing the PathVariable
    //2. Use the model to send the park object to the form
    //3. return the template for the edit.html

    @PostMapping("/parks/create")
//    Using the @ModelAttribute annotation to receive the park object being submitted from our form
    public String submitNewPark(@ModelAttribute Park park) {
        // Spring Recommended syntax for .findById() method.

//        if(statesDao.findById(stateId).isPresent()) {
//            State state = statesDao.findById(stateId).get();
//            Park newPark = new Park(name, state);
//            parksDao.save(newPark);
//
//        }
        // Save the park and its values into our database
        parksDao.save(park);

        return "redirect:/parks";
    }

}