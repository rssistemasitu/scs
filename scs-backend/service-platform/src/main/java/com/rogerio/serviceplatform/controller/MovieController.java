package com.rogerio.serviceplatform.controller;

import com.rogerio.serviceplatform.service.PlatformService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/platform/movie")
public class MovieController {
    private static Logger log = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private PlatformService platformService;

    @GetMapping("/genres")
    public ResponseEntity<Object> getAllGenres() {
        final Object genreList = platformService.getGenres();
        log.info("platform-controller - [flow: get-movies-genres]");
        return ResponseEntity.ok(genreList);
    }

        @GetMapping("/now-playing")
    public ResponseEntity<Object> getMoviesNowPlaying() {
        final Object moviesList = platformService.getMoviesNowPlaying();
        log.info("platform-controller - [flow: get-movies-now-playing]");
        return ResponseEntity.ok(moviesList);
    }

    @GetMapping("/popular")
    public ResponseEntity<Object> getMoviesPopular() {
        final Object moviesList = platformService.getMoviesPopular();
        log.info("platform-controller - [flow: get-movies-popular]");
        return ResponseEntity.ok(moviesList);
    }

    @GetMapping("/top-rated")
    public ResponseEntity<Object> getMoviesTopRated() {
        final Object moviesList = platformService.getMoviesTopRated();
        log.info("platform-controller - [flow: get-movies-top-rated]");
        return ResponseEntity.ok(moviesList);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<Object> getMoviesUpcoming() {
        final Object moviesList = platformService.getMoviesUpcoming();
        log.info("platform-controller - [flow: get-movies-upcoming]");
        return ResponseEntity.ok(moviesList);
    }

    @GetMapping("/trending")
    public ResponseEntity<Object> getMoviesTrending() {
        final Object moviesList = platformService.getMoviesTrending();
        log.info("platform-controller - [flow: get-movies-trending]");
        return ResponseEntity.ok(moviesList);
    }
}
