package hireartists.consumer;

/**
 * Created by prayagupd
 * on 1/14/16.
 */

public interface EventListener<E> {
    void handleEvent(E event);
}
