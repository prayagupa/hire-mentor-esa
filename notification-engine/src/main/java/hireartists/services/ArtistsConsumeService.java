package hireartists.services;

import hireartists.consumer.HireArtistMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by prayagupd
 * on 10/31/15.
 */

@Service
public class ArtistsConsumeService {
    private Logger logger = LoggerFactory.getLogger(ArtistsConsumeService.class);


    public ArtistsConsumeService(){

    }

    public void consume(){

    }

    @PostConstruct
    public void init(){
        new HireArtistMessageListener().start();
    }
}
