package com.hireartists.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by prayagupd
 * on 10/11/15.
 */

@Controller
public class ArtistController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("name", "prayag");
        return "index";
    }
}
