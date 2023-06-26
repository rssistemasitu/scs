import axios from 'axios';

const API_KEY = "1e2a083242e5ee3f55493d2e59bb0879"

const api = axios.create({
  baseURL: `https://api.themoviedb.org/3`, // Substitua pela URL da sua API
});


export const getMovies = async (path: any) => {
  try {
    let url = `https://api.themoviedb.org/3${path}`;
    const response = await api.get(url);
    const moviesList = response.data;
    return moviesList?.results
  } catch (error) {
    console.log("error getMovies: ", error);
  }
};

export const getGenres = async () => {
  try {
    let url = `https://api.themoviedb.org/3/genre/movie/list?language=pt-BR&api_key=${API_KEY}`;
    const response = await api.get(url);
    const genresList = response.data;
    return genresList
  } catch (error) {
    console.log("error getGenres: ", error);
  }
};

export type Movie = {
  adult: boolean;
  backdrop_path: string;
  genre_ids: [];
  id: number;
  media_type: string;
  original_language: string;
  original_title: string;
  overview: string;
  popularity: string;
  poster_path: string;
  release_date: string;
  title: string;
  video: boolean;
  vote_average: number;
  vote_count: number;
}

export type Genre = {
  id: number;
  name: string;
}


export const categories = [
    {
      name: "trending",
      title: "Em alta",
      path: `/trending/all/week?api_key=${API_KEY}&language=pt-BR`,
      isLarge: true,
    },
    {
      name: "netflixOriginals",
      title: "Originais Netflix",
      path: `/discover/tv?api_key=${API_KEY}&with_networks=213`,
      isLarge: false,
    },
    {
      name: "topRated",
      title: "Populares",
      path: `/movie/top_rated?api_key=${API_KEY}&language=pt-BR`,
      isLarge: false,
    },
    {
      name: "comedy",
      title: "Comédias",
      path: `/discover/tv?api_key=${API_KEY}&with_genres=35`,
      isLarge: false,
    },
    {
      name: "romances",
      title: "Romances",
      path: `/discover/tv?api_key=${API_KEY}&with_genres=10749`,
      isLarge: false,
    },
    {
      name: "documentaries",
      title: "Documentários",
      path: `/discover/tv?api_key=${API_KEY}&with_genres=99`,
      isLarge: false,
    },
  ];
  

  