package com.codeup.codeupspringblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MathController {

    public int addition(@PathVariable int a, @PathVariable int b) {
        return a + b;
    }

    public int subtraction(@PathVariable int a, @PathVariable int b) {
        return a - b;
    }

    public int multiplication(@PathVariable int a, @PathVariable int b) {
        return a * b;
    }

    public int division(@PathVariable int a, @PathVariable int b) {
        return a / b;
    }
}
