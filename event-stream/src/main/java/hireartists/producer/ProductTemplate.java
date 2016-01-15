package hireartists.producer;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by prayagupd
 * on 12/8/15.
 */

@Component
public class ProductTemplate {

    @Resource(name = "producerB")
    Producer producer;

    public ProductTemplate(){

    }
    public ProductTemplate(Producer producer) {
        this.producer = producer;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public String whoAmI() {
        return producer.sendEvent();
    }
}
