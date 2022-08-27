import { Component, Input, OnInit } from '@angular/core';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  @Input() public isLogged: boolean;
  @Input() public username: string;


  constructor(private loginService: LoginService) { }

  ngOnInit(): void {
  }

  public logout(): void {
    this.loginService.logout();
  }

}
