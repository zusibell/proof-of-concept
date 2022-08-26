import { OAuthModuleConfig } from "angular-oauth2-oidc";

const createOAuthConfig: () => OAuthModuleConfig = (): OAuthModuleConfig => ({
        resourceServer: {
          allowedUrls: ['http://localhost:3107'],
          sendAccessToken: true
        }
})

export default createOAuthConfig;