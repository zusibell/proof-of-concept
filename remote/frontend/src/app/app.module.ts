import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AuthConfig, OAuthModule } from 'angular-oauth2-oidc';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { authConfig } from './auth/auth.config';
import { AuthService } from './auth/auth.service';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CommonModule,
    HttpClientModule,
    OAuthModule.forRoot({
      resourceServer: {
          allowedUrls: ['http://localhost:3107/api/home'],
          sendAccessToken: true
      }
    }),
  ],
  providers: [
    { provide: AuthConfig, useValue: authConfig },
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(private readonly authService: AuthService) {
    if (!this.authService.authorizationCodeFound) {
      throw new Error("AppModule stopping.")
    };
    
  }
 }
