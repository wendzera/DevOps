package br.com.examplefatec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FatecAdsController {

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping({"/home", "/fatecads"})
    public String index() {
        return "index";
    }
}
