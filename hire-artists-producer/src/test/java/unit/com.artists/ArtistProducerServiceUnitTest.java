package unit.com.artists;

import com.hireartists.domains.Artist;
import com.hireartists.repository.ArtistRepository;
import com.hireartists.services.ArtistProducerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * Created by prayagupd
 * on 11/11/15.
 */

public class ArtistProducerServiceUnitTest {

    @Resource
    ArtistProducerService artistProducerService;
    @Before
    public void setup(){
        artistProducerService = new ArtistProducerService();
        artistProducerService.artistRepository = mock(ArtistRepository.class);
    }

    @Test
    public void searchShouldReturnAllArtistsWithCriteria(){

        doReturn(Arrays.asList(new Artist("Porcupine Tree", "Band"),
                               new Artist("Bink Floyd", "Band")))
                .when(artistProducerService.artistRepository).findAll();

        List<Artist> actualArtists = artistProducerService.search("P");
        Assert.assertEquals(1, actualArtists.size());
        actualArtists.stream().forEach(artist -> {
            Assert.assertEquals("Porcupine Tree", artist.getName());
        });
    }

    @Test
    public void groupByTypeShouldReturnAllArtistsGroupedbyArtistType(){

        when(artistProducerService.artistRepository.findAll()).thenReturn(Arrays.asList(new Artist("Porcupine Tree", "Band")));
        Map<String, List<Artist>> actualArtists = artistProducerService.groupListByType("P");
        Assert.assertEquals(2, actualArtists.keySet().size());
    }
}
