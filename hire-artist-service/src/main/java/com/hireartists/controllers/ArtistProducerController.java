package com.hireartists.controllers;

import com.hireartists.consumer.ArtistEventListener;
import com.hireartists.consumer.SmartLifeCycleA;
import com.hireartists.services.ArtistProducerService;
import kafka.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by prayagupd
 * on 10/11/15.
 * http://kafka.apache.org/07/quickstart.html
 */

@Controller
public class ArtistProducerController {

    @Autowired
    ArtistProducerService artistProducerService;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model){
        Map<String, SmartLifeCycleA> beansOfType = applicationContext.getBeansOfType(SmartLifeCycleA.class);
        beansOfType.forEach((beanName, bean) -> {
            System.out.println(beanName + " -> " + bean.getClass());
        });
        model.addAttribute("name", "prayag");
        return "index";
    }

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String send(Model model){
        String xml = "<HireArtistEvent><name>prayagupd</name><date>10/28/2016</date></HireArtistEvent>";
        artistProducerService.produce(xml);
        model.addAttribute("name", "prayag");
        return "index";
    }

    @RequestMapping(value = "/consume", method = RequestMethod.GET)
    public String consume(Model model){
        new ArtistEventListener().start();
        return "index";
    }

    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public String stop(Model model){
        new ArtistEventListener().stop();
        return "index";
    }
}
