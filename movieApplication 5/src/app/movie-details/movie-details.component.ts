import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgToastService } from 'ng-angular-popup';
import { Movie } from '../Movie';
import { MovieService } from '../services/movie.service';

@Component({
  selector: 'app-movie-details',
  templateUrl: './movie-details.component.html',
  styleUrls: ['./movie-details.component.css']
})
export class MovieDetailsComponent implements OnInit {
  selectedMovieData: any;
  // selectedMovieGenres:any;
  movies: any = [];
  favouriteMovieId: number = 0;
  favouriteMovieName: any;
  particularMovieGenre: any;
  alert: boolean = false;
  warningAlert: boolean = false;
  movieData:any;
  

  constructor(private movieService:MovieService, private toast: NgToastService, private route: Router) { }

  ngOnInit(): void {
    console.log("-------------------");
    this.selectedMovieData = this.movieService.movieInfo;
    // console.log("movie details");
    console.log(this.selectedMovieData);
    this.selectedMovieData=localStorage.getItem('movieDetails')
  this.selectedMovieData=JSON.parse(this.selectedMovieData);
    this.getMovieData();
  }
 

  getMovieData() {
  console.log(this.movieData);
    this.movieService.getParticularMovieDetails(this.selectedMovieData.id).subscribe(res => {
      this.movies = res;
      console.log("this is genres");
      console.log(this.movies);
      this.movieService.recommendedMovieId = this.movies.id;
      this.particularMovieGenre = this.movies.genres;
    });
  }

  favorite : Movie=new Movie(0,"",0,"","",0,"",true);
  addToFavourite(selectedMovieData: any) {
    if(window.localStorage.getItem('userName')==null){
      //this.toast.error({ detail: 'Please Login to add favorites', duration: 4000 });
      alert('Please Login to add favorites')
      this.route.navigate(['login']);
    }
    else{
    const watch=false;
    this.favorite =
      new Movie(
        selectedMovieData.id,
        selectedMovieData.overview,
        selectedMovieData.popularity,
        selectedMovieData.release_date,
        selectedMovieData.title,
        selectedMovieData.vote_average,
        selectedMovieData.poster_path,
        selectedMovieData.watched=watch,
      )
      console.log(this.favorite);
      this.movieService.saveMovieByUser(window.localStorage.getItem('userName'),this.favorite).subscribe(
        (selectedMovieData:any)=>{
          this.toast.success({ detail: 'Movie Addeded to favorites ', duration: 1000 });
        },
        error=>{if (error.status == 404) {
          this.toast.warning({ detail: 'Movie is already present in your list..', duration: 1000 });
        }
        else{
         // this.toast.error({ detail: 'Movie Already present', duration: 4000 });
          this.toast.success({ detail: 'Movie Addeded to favorites ', duration: 1000 });
        }
        }
      );
    }
  }

  passRecommended(movieTitle: string) {
    this.movieService.movieName = movieTitle;
  }
  closeAlert() {
    this.alert = false;
    this.warningAlert = false;
  }

}
