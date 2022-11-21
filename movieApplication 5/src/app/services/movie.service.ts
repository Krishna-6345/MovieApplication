import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Movie } from '../Movie';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  private MyAPIKey:string ='d694e973c66a1901ff31a3b88bdc1108';

  constructor(private http:HttpClient, private router:Router) { }

  movieName:string |undefined;
  movieInfo:any
  currentPage:number=1
  userName:any
  favMovieObj:any={}
  recommendedMovieId:any;

  //for perticular movie
  selectedMovie(data:any)
  {
    this.movieInfo=data
    this.router.navigate(['/movieDetails'])
  }


  // to display all movies
  getAllMovies(currentPage:number)
  {
    let url=`https://api.themoviedb.org/3/discover/movie?api_key=${this.MyAPIKey}&page=${currentPage}`
    return this.http.get<any>(url);
  }

  // get one movie details
  getParticularMovieDetails(movieId:number)
  {
    let url = `https://api.themoviedb.org/3/movie/${movieId}?api_key=${this.MyAPIKey}&append_to_response=credits`;
    return this.http.get<any>(url);
  }

   //to get all the favourite movies from the Api by Movie Id.
   getAllFavouriteMoviesFromApi(movieId:number)
  {
    let url = `https://api.themoviedb.org/3/movie/${movieId}?api_key=${this.MyAPIKey}&append_to_response=credits`;
    return this.http.get<any>(url);
  }

  saveMovieByUser(name: any, movie: Movie){
    return this.http.put("http://localhost:8086/api/v2/authorized/savemovie/"+name, movie)
  }

  getFavouriteMoviesByUserName(name : any)
  {
    return this.http.get("http://localhost:8086/api/v2/authorized/getmovie/"+name)
  }

  getWatchedMoviesByUserName(name : any){
    return this.http.get("http://localhost:8087/api/v3/authorized/watchedmovies/"+name)
  }

  removeFavMovie(name:any,movieId:any){
    return this.http.delete('http://localhost:8086/api/v2/authorized/deletemovie/'+name+'/'+movieId)
  }

  updateFavToWatched( name:any, movie:Movie){
    return this.http.put("http://localhost:8086/api/v2/authorized/updateWatched/" + name , movie)
  }

  removeWatchedMovie(name:any,movieId:any){
    return this.http.delete('http://localhost:8087/api/v3/authorized/deleteWatched/'+name+'/'+movieId)
  }

}
