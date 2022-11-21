import { Component, OnInit } from '@angular/core';
import { NgToastService } from 'ng-angular-popup';
import { Movie } from '../Movie';
import { MovieService } from '../services/movie.service';
  

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css']
})
export class UserDashboardComponent implements OnInit {
  
  favorites : Movie[] = []
  watched : Movie[] = []

  page1 : number = 1;
 
  
  favLength : any;
  


constructor (private movie: MovieService, private toast: NgToastService){}
  ngOnInit(): void {
    this.movie.getFavouriteMoviesByUserName(window.localStorage.getItem('userName')).subscribe(
      (data:any)=>{
        this.favorites = data;
        this.favLength= data.length;
        console.log(data);
      }
    );

    
  }

  deleteFavoriteMovie(id:any){
    this.movie.removeFavMovie(window.localStorage.getItem('userName'),id).subscribe(
      (data:any)=>{
        console.log(data);
      },
      error=>{
        this.toast.success({detail: 'Delete Successfull..', duration: 4000}),
        location.reload()
      }
    )
    
    
  }


  favorite : Movie=new Movie(0,"",0,"","",0,"",true);
  updateFavoriteMovie(item:any)
  {
    const watch=false;
    this.favorite = 
      new Movie(
        item.movieId, 
        item.overview,
        item.popularity,
        item.releaseDate,
        item.title,
        item.voteAverage,
        item.posterPath,
        item.watched=watch,
      )
      console.log(this.favorite);
      this.movie.updateFavToWatched(window.localStorage.getItem('userName'),this.favorite).subscribe(
        (data:any)=>{
          if(data){
            console.log(data);
          }
        },
        error=>{
          this.toast.success({detail: 'Added to Watched List', duration: 4000})
        }
      );
  }

}
