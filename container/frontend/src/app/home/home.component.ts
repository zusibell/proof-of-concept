import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  public username: string = '';

  constructor() { }

  ngOnInit(): void {
    //this.username = this.keycloakService.getUsername();
  }



}
