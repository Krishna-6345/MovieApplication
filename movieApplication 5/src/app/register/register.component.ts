import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from '../User';
import { Movie } from '../Movie';
import { MainService } from '../services/main.service';
import { NgToastService } from 'ng-angular-popup';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  
  movies : Movie=new Movie(0,"",0,"","",0,"",false);
  watch : Movie=new Movie(0,"",0,"","",0,"",true);
  user: User=new User("","","","",this.movies,this.watch);
  
  registerForm!: FormGroup;

  constructor(private router: Router, private sign : MainService, private toast: NgToastService) {
  }

  ngOnInit(): void {
    this.registerForm = new FormGroup({
      userName:new FormControl('',Validators.required),
      password: new FormControl('',Validators.required),
      gender:new FormControl('',Validators.required),
      emailId:new FormControl('',Validators.required)
    })
  }

  register(){
    let userDetails = this.registerForm.value;
    userDetails.movies = [this.movies];
    userDetails.watched= [this.watch];
    this.sign.signUp(this.registerForm.value).subscribe(
      (data:any)=>{
        console.log(data);
        alert('User Registered Sucessfully');
        this.router.navigateByUrl('login');
      }
    );
    (err:any)=>{
      this.toast.error({ detail: 'Invalid Credentials', summary: 'This Username is already taken please try with a different ', duration: 4000 })
    }
  }

}
