import { Component, OnInit } from '@angular/core';
import { AuthService } from './auth/auth.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'frontend';
  public username: string;
  public isLogged: boolean = false;
   constructor(private authService: AuthService){
    this.username = this.authService.getUsername();
    this.isLogged = this.authService.getIsLogged();
    this.authService.authorizationCodeFound;
   }
  
  ngOnInit(): void {
  }

  
}
