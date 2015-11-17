package com.hireartists;

import hireartists.services.ArtistsConsumeService;
import org.junit.Before;
import org.junit.Test;

public class ArtistServiceTest {

    ArtistsConsumeService artistsConsumeService;

    @Before
    public void setup(){
        artistsConsumeService = new ArtistsConsumeService();
    }

    @Test
    public void searchShouldReturnAllArtistsWithCriteria(){
        artistsConsumeService.consume();
    }
} 
