package com.rogerio.serviceplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PlatformService {

    @Autowired
    @Lazy
    private RestTemplate restTemplate;

    private String API_KEY = "1e2a083242e5ee3f55493d2e59bb0879";
    private String URL_API_MAIN = "https://api.themoviedb.org/3";
    private String URL_API_GENRES = String.format("%s/genre/movie/list?language=pt-BR&api_key=%s",URL_API_MAIN,API_KEY);
    private String URL_API_MOVIES_NOW_PLAYING = String.format("%s/movie/now_playing?language=pt&api_key=%s",URL_API_MAIN,API_KEY);
    private String URL_API_MOVIES_POPULAR = String.format("%s/movie/popular?language=pt&api_key=%s",URL_API_MAIN,API_KEY);
    private String URL_API_MOVIES_TOP_RATED = String.format("%s/movie/top_rated?language=pt&api_key=%s",URL_API_MAIN,API_KEY);
    private String URL_API_MOVIES_UPCOMING = String.format("%s/movie/upcoming?language=pt&api_key=%s",URL_API_MAIN,API_KEY);
    private String URL_API_MOVIES_TRENDING = String.format("%s/trending/movie/week?language=pt&api_key=%s",URL_API_MAIN,API_KEY);
    private String URL_API_MOVIES_ADULT = "https://www.eporner.com/api/v2/video/search/?query=teen&per_page=10&page=2&thumbsize=big&order=top-weekly&gay=0&lq=1&format=json";

    @Cacheable("movie-genres")
    public Object getGenres() {
        Object list = restTemplate.getForObject(URL_API_GENRES, Object.class);
        return list;
    }

    @Cacheable("movie-now-palying")
    public Object getMoviesNowPlaying() {
        Object list = restTemplate.getForObject(URL_API_MOVIES_NOW_PLAYING, Object.class);
        return list;
    }

    @Cacheable("movie-popular")
    public Object getMoviesPopular() {
        Object list = restTemplate.getForObject(URL_API_MOVIES_POPULAR, Object.class);
        return list;
    }

    @Cacheable("movie-top-rated")
    public Object getMoviesTopRated() {
        Object list = restTemplate.getForObject(URL_API_MOVIES_TOP_RATED, Object.class);
        return list;
    }

    @Cacheable("movie-up-coming")
    public Object getMoviesUpcoming() {
        Object list = restTemplate.getForObject(URL_API_MOVIES_UPCOMING, Object.class);
        return list;
    }

    @Cacheable("movie-trending")
    public Object getMoviesTrending() {
        Object list = restTemplate.getForObject(URL_API_MOVIES_TRENDING, Object.class);
        return list;
    }

    @Cacheable("movie-adult")
    public Object getMoviesAdult() {
        Object list = restTemplate.getForObject(URL_API_MOVIES_ADULT, Object.class);
        return list;
    }
}
