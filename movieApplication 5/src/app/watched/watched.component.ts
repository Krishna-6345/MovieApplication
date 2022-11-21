import { Component, OnInit } from '@angular/core';
import { NgToastService } from 'ng-angular-popup';
import { Movie } from '../Movie';
import { MovieService } from '../services/movie.service';

@Component({
  selector: 'app-watched',
  templateUrl: './watched.component.html',
  styleUrls: ['./watched.component.css']
})
export class WatchedComponent implements OnInit {

  constructor(private movie: MovieService, private toast: NgToastService) { }

  watched : Movie[] = []
  page2 : number = 1;
  watchedLength : any;
  

  ngOnInit(): void {

    this.movie.getWatchedMoviesByUserName(window.localStorage.getItem('userName')).subscribe(
      (data:any)=>{
        this.watched = data;
        this.watchedLength=data.length;
        console.log(data);
      }
    );

  }

  deleteWatchedMovie(id:any){
    this.movie.removeWatchedMovie(window.localStorage.getItem('userName'),id).subscribe(
      (data:any)=>{
        console.log(data);
        window.location.reload();
      },
      error=>{
        this.toast.success({detail: 'Delete Successfull..', duration: 4000}),
        location.reload()
      }
    );
  }

}
