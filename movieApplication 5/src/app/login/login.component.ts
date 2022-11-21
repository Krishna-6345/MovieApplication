import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgToastService } from 'ng-angular-popup';
import { MainService } from '../services/main.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
 
  userName=''
  password=''

  loginForm!: FormGroup;
  
  @Input()
  error!: string | null;


  constructor(private router : Router, private loginService : MainService, private toast : NgToastService) {}

  ngOnInit(): void {
  this.loginForm=new FormGroup({
    userName:new FormControl("",[Validators.required]),
    password: new FormControl("",[Validators.required])
  })}

  login(){
    this.loginService.login(this.loginForm.value).subscribe(
      (data:any)=>{
        if(data){
          window.localStorage.setItem('userName',this.loginForm.value.userName);
          sessionStorage.setItem('token',data.token);
          sessionStorage.setItem('userName',this.loginForm.value.userName);
          this.router.navigateByUrl('userDashboard');
          this.toast.success({ detail: 'Login Successfull..', duration: 4000 });
        }
      },
      (err:any)=>{
        this.toast.error({ detail: 'Invalid Credentials', summary: 'Are you sure you have registered..?', duration: 4000 })
      }
    );
  }

}
