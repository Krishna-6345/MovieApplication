import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgToastService } from 'ng-angular-popup';
import { Movie } from '../Movie';
import { MovieService } from '../services/movie.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  movies:any=[]
  allMovies:any=[]
  currentPage:number=1

  

  constructor(private movie:MovieService, private route:Router, private toast : NgToastService) {}

  ngOnInit(): void {
    this.getMovies();
  }

  getMovies()
  {
    this.movie.getAllMovies(this.currentPage).subscribe(
      res=>{
        this.movies=res
        console.log(this.movies);
        this.allMovies=this.movies.results;
        console.log(this.allMovies);
      }
    )
  }


  decreasePage()
  {
    this.currentPage--;
    this.getMovies();
  }

  increasePage()
  {
    this.currentPage++;
    this.getMovies();
  }
  favorite : Movie=new Movie(0,"",0,"","",0,"",true);
  addFavourite(data:any){
  if(window.localStorage.getItem('userName')==null){
    //this.toast.error({ detail: 'Please Login to add favorites', duration: 4000 });
    alert('Please Login to add favorites')
    this.route.navigate(['login']);
  }
  else{
    const watch=false;
    this.favorite =
      new Movie(
        data.id,
        data.overview,
        data.popularity,
        data.release_date,
        data.title,
        data.vote_average,
        data.poster_path,
        data.watched=watch,
      )
      console.log(this.favorite);
      this.movie.saveMovieByUser(window.localStorage.getItem('userName'),this.favorite).subscribe(
        (data:any)=>{
          this.toast.success({ detail: 'Movie Addeded to favorites ', duration: 4000 });
        },
        error=>{if (error.status == 404) {
          this.toast.warning({ detail: 'Movie is already present in your list..', duration: 3000 });
        }
        else{
         // this.toast.error({ detail: 'Movie Already present', duration: 4000 });
          this.toast.success({ detail: 'Movie Addeded to favorites ', duration: 4000 });
        }
        }
      );
  }
}

  movieDetailsPage(data:any)
  {
    localStorage.setItem('movieDetails',JSON.stringify(data));
    this.route.navigate(['movieDetails']);
  }

}
