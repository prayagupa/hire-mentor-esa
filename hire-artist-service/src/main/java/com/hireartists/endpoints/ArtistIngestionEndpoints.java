package com.hireartists.endpoints;

import com.eventstream.events.JsonEvent;
import com.eventstream.producer.EventProducer;
import lombok.val;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;

/**
 * Created by prayagupd
 * on 10/11/15.
 * http://kafka.apache.org/07/quickstart.html
 */

@Controller
public class ArtistIngestionEndpoints {

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(value = "/ingest", method = RequestMethod.GET)
    public String ingest(Model model){
        String json = "{\"ArtistAdded\" : { \"id\" : \"1\", \"artist\" : \"urayagppd\" }}";

        val jsonEvent = new JsonEvent(json, s -> new JSONObject(s).getJSONObject("ArtistAdded").getString("id"),
                s -> Collections.singletonList(new JSONObject(s).getJSONObject("ArtistAdded").getString("id")));

        val eventProduced = eventProducer.publish(jsonEvent);
        model.addAttribute("partition", eventProduced.getPartition());
        return "index";
    }
}
