import { HttpClientModule } from '@angular/common/http';
import {  CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AuthConfig, OAuthModule } from 'angular-oauth2-oidc';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { NavbarComponent } from './navbar/navbar.component';
import { authConfig } from './auth/auth.config';
import { AuthModule } from './auth/auth.module';
import { AuthService } from './auth/auth.service';



@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NavbarComponent
  ],
  imports: [
    AuthModule,
    BrowserModule,
    HttpClientModule,
    FormsModule,
    OAuthModule.forRoot({
      resourceServer: {
          allowedUrls: ['http://localhost:3007'],
          sendAccessToken: true
      }
    }),
    AppRoutingModule,
  ],
  providers: [
    { provide: AuthConfig, useValue: authConfig },
  ],
  bootstrap: [AppComponent],
  schemas:[CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { 
  constructor(private readonly authService: AuthService) {
    if (!this.authService.authorizationCodeFound) {
      throw new Error("AppModule stopping.")
    };
  }
}
