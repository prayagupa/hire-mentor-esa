package hireartists.controller;

import hireartists.services.ArtistsConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArtistsConsumerController {

    @Autowired
    ArtistsConsumeService artistsConsumeService;

    @RequestMapping("/consume")
    public String consume(@RequestParam(value = "name", required = false, defaultValue = "consume") String name, Model model) {
        artistsConsumeService.consume();
        model.addAttribute("name", name);
        return "consume";
    }

}
