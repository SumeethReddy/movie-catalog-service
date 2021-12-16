package com.example.moviecatalogservice.resources;

import com.example.moviecatalogservice.models.CatalogItem;
import com.example.moviecatalogservice.models.Movie;
import com.example.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){


        RestTemplate restTemplate = new RestTemplate();
        //get all rated movie IDs
        List<Rating> ratings = Arrays.asList(
                new Rating("1234",4),
                new Rating("5678",3),
                new Rating("234",5)
                );

        return ratings.stream().map(rating ->{
                Movie movie = restTemplate.getForObject("http://localhost:8085/movies/" + rating.getMovieId(), Movie.class);

/*           Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8085/movies/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();*/
                return new CatalogItem(movie.getName(),"Test",rating.getRating());
        })
                .collect(Collectors.toList());
        //For each movie ID, call movie info service and get details

        //Put them all together

    }
}
