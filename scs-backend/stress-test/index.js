import { group, sleep } from 'k6';
import http from 'k6/http';
import { Trend, Rate, Counter } from 'k6/metrics';
import { check, fail } from 'k6';
import { htmlReport } from "https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js";

export let GetMovieDuration = new Trend('get_movie_duration');
export let GetMovieFailRate = new Rate('get_movie_fail_rate');
export let GetMovieSuccessRate = new Rate('get_movie_success_rate');
export let GetMovieReqs = new Trend('get_movie_reqs');

const url = "http://localhost:8989/api/movie/popular"
const token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsYXJpc3NhX2dtYWlsQGdtYWlsLmNvbSIsImlhdCI6MTY5MjA5MTY5NywiZXhwIjoxNzIzMTk1Njk3fQ.aA3KqHGblkhIScMTRHEKxrk6T9i5tMZu2VnnBO1yXXw"
const headers = {
    'Authorization': `Bearer ${token}`,
};
const errorRate = 0.2; // 20% de taxa de erro

export function handleSummary(data) {
    return {
      "summary.html": htmlReport(data),
    };
  }

export default () => {
    group('Endpoint GetMovies - Controller Movie - Service Platform API', () => {
        getMovies();
    });
    sleep(1);
}

export let options = {
    vus: 1000,
    duration: '60s',
};

function getMovies() {

    let res = http.get(url, { headers });

    GetMovieDuration.add(res.timings.duration);
    GetMovieReqs.add(1);
    GetMovieFailRate.add(res.status == 0 || res.status > 499);
    GetMovieSuccessRate.add(res.status < 499);

    let durationMsg = 'Max Duration ${1000/1000}s'
    
    if (!check(res, {
        'max duration': (r) => r.timings.duration < 1000,
    })) {
        fail(durationMsg);
    }
    
    sleep(1);
}