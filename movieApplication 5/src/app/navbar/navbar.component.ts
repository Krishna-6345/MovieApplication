import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NgToastService } from 'ng-angular-popup';
import { MainService } from '../services/main.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  @ViewChild('myModalClose') modalClose: any;

  constructor(
    private router: Router,
    private login : MainService,
    private http: HttpClient,
    private toast: NgToastService
    ) { }

  userName: any;
  pictureName: any;
  profileUrl: File | undefined;
  //location: any;
   
  loginSignUpButton : boolean = false;
  profileButton : boolean = true;

    ngOnInit(): void {
      if(window.localStorage.getItem('userName')==null){
        this.userName="User"
        this.loginSignUpButton=true;
        this.profileButton=false;
      }
      else{
        this.userName=window.localStorage.getItem('userName')
        this.loginSignUpButton=false;
        this.profileButton=true;
        this.getImage();
      }
    }

    logOut(){
      this.router.navigateByUrl('dashboard');
      this.login.logout();
      this.loginSignUpButton=true;
      this.profileButton=false;
    }

    goToUserDashboard(){
      this.router.navigateByUrl('userDashboard');
    }

    goToWatched(){
      this.router.navigateByUrl('watched');
    }
   
    saveProfile(event: any) {
      if (event.target.files) {
        this.pictureName = <File>event.target.files[0];
        console.log("hn yahan aaye ha");
        
      }
    }
  
    onUpload() {
      console.log(this.pictureName, this.userName);
      
      const fd = new FormData();
      fd.append('imageFile', this.pictureName, this.userName )
      this.http.post<any>('http://localhost:8085/addProfilePic/upload', fd)
        .subscribe(res => {
          console.log(res);
          this.toast.success({ detail: 'Profile picture uploaded successfuly..', duration: 4000 });
          this.getImage();
        },
          error => {
            this.toast.error({ detail: 'Error uploading picture..', duration: 4000 });
          })
    }
  
    fileName:any;
     getImage() {
       this.fileName=window.localStorage.getItem('userName');
       this.http.get<any>('http://localhost:8085/addProfilePic/getProfilePic/' + this.fileName) 
         .subscribe(res => {
           res=this.profileUrl;
         },
           error => {
             if (error.status == 200) {
               this.profileUrl = error.url;
               console.log(this.profileUrl);
               
             }
             else {
              // this.toast.error({ detail: 'Error fetching the picture', duration: 4000 });
             }
           })
         }

}
