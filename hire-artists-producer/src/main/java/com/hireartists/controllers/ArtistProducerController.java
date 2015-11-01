package com.hireartists.controllers;

import com.hireartists.services.ArtistProducerService;
import kafka.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by prayagupd
 * on 10/11/15.
 * http://kafka.apache.org/07/quickstart.html
 */

@Controller
public class ArtistProducerController {

    @Autowired
    ArtistProducerService artistProducerService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("name", "prayag");
        return "index";
    }

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String send(Model model){
        String xml = "<name>prayagupd</name><available>1</available>";
        artistProducerService.produce(xml);
        model.addAttribute("name", "prayag");
        return "index";
    }
}
