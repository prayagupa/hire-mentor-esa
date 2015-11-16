package com.hireartists.repository;

import com.hireartists.domains.Artist;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * Created by prayagupd
 * on 11/11/15.
 */

@Repository
public class ArtistRepository {
    public List<Artist> findAll() {
        List<Artist> artists = Arrays.asList(new Artist("Porcupine Tree", "Band"), new Artist("BMTH", "Band"),
                new Artist("Steven Wilson", "Solo"));
        return artists;
    }
}
