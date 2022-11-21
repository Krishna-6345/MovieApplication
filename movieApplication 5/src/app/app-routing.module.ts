import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { FooterComponent } from './footer/footer.component';
import { LoginComponent } from './login/login.component';
import { MovieDetailsComponent } from './movie-details/movie-details.component';
import { NavbarComponent } from './navbar/navbar.component';
import { RegisterComponent } from './register/register.component';
import { UserDashboardComponent } from './user-dashboard/user-dashboard.component';
import { WatchedComponent } from './watched/watched.component';
const routes: Routes = [
  {path:'', redirectTo:'dashboard',pathMatch:'full'},
  {path:'dashboard', component: DashboardComponent},
  {path:'login', component: LoginComponent},
  {path:'footer', component: FooterComponent},
  {path:'navbar', component: NavbarComponent},
  {path:'register', component: RegisterComponent},
  {path:'userDashboard', component: UserDashboardComponent},
  {path:'movieDetails', component:MovieDetailsComponent},
  {path:'watched', component:WatchedComponent}
  ]



@NgModule({
  imports: [RouterModule.forRoot(routes,{onSameUrlNavigation:'reload'})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
