package hireartists.producer;

/**
 * Created by prayagupd
 * on 12/8/15.
 */
public class ProducerB implements Producer {

    @Override
    public String sendEvent() {
        System.out.println("Event_B");
        return "Event_B";
    }
}
