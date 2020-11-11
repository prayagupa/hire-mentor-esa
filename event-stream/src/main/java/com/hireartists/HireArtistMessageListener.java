package com.hireartists;

import com.es.consumer.MultiEventListener;
import com.hireartists.domain.ArtistHiredEvent;
import org.springframework.stereotype.Component;

/**
 * Created by prayagupd
 * on 11/16/15.
 */

@Component
public class HireArtistMessageListener extends MultiEventListener {

    public void handleEventType1(ArtistHiredEvent event) {
        System.out.println("saving type1");
    }

    public void handleEventType2(TestEvent event) {
        System.out.println("saving type2");
    }

    class TestEvent {

    }
}
