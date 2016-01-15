package com.hireartists;

import hireartists.consumer.HireArtistMessageListener;
import hireartists.consumer.methodHandle.TestMultiListener;
import hireartists.domain.ArtistHiredEvent;
import org.junit.Test;

/**
 * Created by prayagupd
 * on 1/14/16.
 */

public class EventListenerTests {

    HireArtistMessageListener hireArtistMessageListener;

    @Test
    public void onHandleShouldDispatchEvent() {
        hireArtistMessageListener = new HireArtistMessageListener();
        hireArtistMessageListener.handleEvent(new ArtistHiredEvent());
    }

    @Test
    public void onHandle() {
        TestMultiListener testMultiListener = new TestMultiListener();
        testMultiListener.handleEvent1(new ArtistHiredEvent());
    }
}
