import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { OAuthService } from 'angular-oauth2-oidc';
import { JwksValidationHandler } from 'angular-oauth2-oidc-jwks';
import { authConfig } from './auth.config';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public authorizationCodeFound: Promise<boolean>;

  private readonly jwtHelperService: JwtHelperService;
  private username: string;
  private isLogged: boolean;
  
  constructor(private oauthService: OAuthService, private loginService: LoginService) {
    this.jwtHelperService = new JwtHelperService();
    this.authorizationCodeFound = this.configure();
    this.authorizationCodeFound
      .then((found: boolean) => {
        console.log(found)
        if (found && this.oauthService.hasValidAccessToken()) {
          this.updateData();
        } else {
          this.oauthService.initCodeFlow();
        }
      });
    
  }

  private async configure(): Promise<boolean> {
    this.oauthService.configure(authConfig);
    this.oauthService.tokenValidationHandler = new JwksValidationHandler();
    this.oauthService.setupAutomaticSilentRefresh();
    return this.oauthService.loadDiscoveryDocumentAndTryLogin();   
  }

  public getUsername(): string {
    return this.username;
  }
  public getIsLogged(): boolean {
    return this.isLogged;
  }

  public logout(): void {
    this.oauthService.logOut();
  }

  public getAccessToken() {
    return this.oauthService.getAccessToken(); 
  }

  private updateData(): void {
      const token: Map<string, object> = this.jwtHelperService.decodeToken(this.oauthService.getAccessToken());
      console.log(token);
      const token_string = JSON.parse(JSON.stringify(token));
      this.isLogged = this.loginService.getIsLogged();
      this.username = token_string["preferred_username"];
  }
}
