import axios, { AxiosError, AxiosResponse } from 'axios';

const API_URL = `http://localhost:8989/api/v1`;

const api = axios.create({
  baseURL: API_URL,
});

const createHeader = (token: string) => {
  return  {
    headers: {
      Authorization: `Bearer ${token}`
    }
  };
}

export const signin = async (email: string, password: string) => {
  try {
    let url = `${API_URL}/auth/signin`;
    const response: AxiosResponse = await api.post(url, {email, password});
    return response.data;
  } catch (error: any) {
    throw handleApiError(error);
  }  
};

export const signout = async (token: string) => {
  try {
    let url = `${API_URL}/auth/logout`;
    const response: AxiosResponse = await api.get(url, createHeader(token));
    return response.data;
  } catch (error: any) {
    throw handleApiError(error);
  }  
};

export const signupUser = async (signupUserType: SignupUserType) => {
  try {
    const date = formatDate(signupUserType.date_birth);
    signupUserType.date_birth = date;
    const url = `${API_URL}/auth/signup`;
    const response: AxiosResponse = await api.post(url, signupUserType);
    return response.data;
  } catch (error: any) {
    throw handleApiError(error);
  }
};

export const signupDependent = async (signupDependentType: SignupDependentType) => {
  try {
    const date = formatDate(signupDependentType.date_birth);
    signupDependentType.date_birth = date;
    let url = `${API_URL}/auth/signup-dependent`;
    const response: AxiosResponse = await api.post(url, signupDependentType);
    return response.data;
  } catch (error: any) {
    throw handleApiError(error);
  }  
};

export const getUserFull = async (token: string) => {
  try {
    let url = `${API_URL}/user/user-full`;
    const response: AxiosResponse = await api.get(url, createHeader(token));
    return response.data;
  } catch (error: any) {
    throw handleApiError(error);
  }  
};

export const getMovies = async (path: any, token: string) => {
  try {    
    let url = `${API_URL}/movie/${path}`;
    const response: AxiosResponse = await api.get(url, createHeader(token));
    const moviesList = response.data;
    return moviesList?.results
  } catch (error: any) {
    throw handleApiError(error);
  }
};

export type UserInfo = {
  id: string;
  email: string;
  type: string;
  token: string;
  roles: Array<string>;
}

export type UserFull = {
  owner_id: string;
  firstName: string;
  lastName: string;
  email: string;
  cpf: string;
  dateBirth: string;
  cardName: string;
  cardNumber: string;
  expirationDate: string;
  cvv: number;
  status: boolean;
  isOwner: boolean;
  userDependents: Array<Object>;
}

export type SignupUserType = {
  first_name: string;
  last_name: string;
  email: string;
  password: string;
  cpf: string;
  date_birth: string;
  card_name: string;
  card_number: string;
  expiration_date: string;
  cvv: number;
}

export type SignupDependentType = {
  first_name: string;
  last_name: string;
  email: string;
  password: string;
  cpf: string;
  date_birth: string;
}

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

export const categories = [
    {
      name: "trending",
      title: "Em alta",
      path: `trending`,
      isLarge: true,
    },
    {
      name: "now_playing",
      title: "Em destaque",
      path: `now-playing`,
      isLarge: false,
    },
    {
      name: "topRated",
      title: "Populares",
      path: `top-rated`,
      isLarge: false,
    },
    {
      name: "upcoming",
      title: "Em breve",
      path: `upcoming`,
      isLarge: false,
    }
    // {
    //   name: "adult",
    //   title: "Adultos",
    //   path: `adult`,
    //   isLarge: false,
    // }
];

const handleApiError = (error: AxiosError): Object =>  {
    if (error.response) {
      return {
        'status' : error.response.status,
        'message' : error.response.data.message,
        'timestamp' : error.response.data.timestamp,
      }
    } else if (error.request) {
      return {
        'status' : 500,
        'message' : error.request,
        'timestamp' : new Date(),
      }
    } else {
      return {
        'status' : 500,
        'message' : error.message,
        'timestamp' : new Date(),
      }
    }
  }
   
const formatDate = (dateString: string): string => {
  const date = new Date(dateString);
  const day = date.getDate().toString().padStart(2, '0');
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const year = date.getFullYear();
  return `${day}-${month}-${year}`;
};