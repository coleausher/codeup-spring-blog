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
        model.addAttribute("states", statesDao.findAll());
        return "parks/create";
    }

    @PostMapping("/parks/create")
    public String submitNewPark(@RequestParam String name, @RequestParam long stateId) {
        // Spring Recommended syntax for .findById() method.

        if(statesDao.findById(stateId).isPresent()) {
            State state = statesDao.findById(stateId).get();
            Park newPark = new Park(name, state);
            parksDao.save(newPark);

        }
        return "redirect:/parks";
    }

}