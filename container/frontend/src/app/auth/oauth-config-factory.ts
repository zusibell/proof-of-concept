import { OAuthModuleConfig } from "angular-oauth2-oidc";

const createOAuthConfig: () => OAuthModuleConfig = (): OAuthModuleConfig => ({
        resourceServer: {
          allowedUrls: ['http://localhost:3006/places'],
          sendAccessToken: true
        }
})

export default createOAuthConfig;