import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MainService {
  
  constructor(private http:HttpClient) {}

  isLoggedIn = new BehaviorSubject(sessionStorage.getItem('username') ? true :false);

  isAuthenticate: boolean=false;

  
  
  login(loginCreds:any){
    return this.http.post('http://localhost:8085/api/v1/login',loginCreds)
  }

  signUp(details:any){
    return this.http.post('http://localhost:8086/api/v2/register',details)
  }

  loggedIn(){
    this.isLoggedIn.next(true);
    this.isAuthenticate=true;
    sessionStorage.setItem('value',JSON.stringify(this.isAuthenticate));
  }

  logout(){
    this.isLoggedIn.next(false);
    this.isAuthenticate=false;
    sessionStorage.clear();
    window.localStorage.clear();
  }



}
