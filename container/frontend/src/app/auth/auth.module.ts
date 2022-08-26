import { APP_INITIALIZER, NgModule } from '@angular/core';
import { OAuthModule, OAuthModuleConfig } from 'angular-oauth2-oidc';
import { AuthService } from './auth.service';
import createOAuthConfig from './oauth-config-factory';



@NgModule({
  imports: [
    OAuthModule.forRoot()
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: (authService: AuthService): (()=> Promise<boolean>) => async (): Promise<boolean> => {
        return authService.authorizationCodeFound;
      },
      deps: [AuthService],
      multi: true
    },
    {
      provide: OAuthModuleConfig, useFactory: createOAuthConfig
    }
  ]
})
export class AuthModule { }
