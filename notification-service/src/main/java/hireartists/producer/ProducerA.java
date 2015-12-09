package hireartists.producer;

/**
 * Created by prayagupd
 * on 12/8/15.
 */
public class ProducerA implements Producer {

    @Override
    public String sendEvent() {
        System.out.println("Event_A");
        return "Event_A";
    }
}
