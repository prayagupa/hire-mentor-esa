package hireartists.consumer.methodHandle;

import hireartists.consumer.Event;
import hireartists.domain.ArtistHiredEvent;

/**
 * Created by prayagupd
 * on 1/15/16.
 */
public class TestMultiListener extends MultiEventsConsumer{

    public void handleEvent1(ArtistHiredEvent event) {
        System.out.println("container");
    }


    public void handle() {
        System.out.println("container");
    }
}
