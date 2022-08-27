import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Injector, NgModule } from '@angular/core';
import { createCustomElement } from '@angular/elements';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AuthConfig, OAuthModule } from 'angular-oauth2-oidc';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { authConfig } from './auth/auth.config';
import { AuthService } from './auth/auth.service';
import { MyTableComponent } from './my-table/my-table.component';


@NgModule({
  declarations: [
    AppComponent,
    MyTableComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CommonModule,
    HttpClientModule,
    FormsModule,
    OAuthModule.forRoot({
      resourceServer: {
          allowedUrls: ['http://localhost:3107/api/home'],
          sendAccessToken: true
      }
    }),
    AppRoutingModule,
  ],
  providers: [
    { provide: AuthConfig, useValue: authConfig },
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(private readonly authService: AuthService, private injector: Injector) {
    if (!this.authService.authorizationCodeFound) {
      throw new Error("AppModule stopping.")
    };
    if (customElements.get('my-private-table') == null || undefined) {
      const webComponent = createCustomElement(MyTableComponent, { injector });
      customElements.define('my-private-table', webComponent);
    }
    
  }
 }
