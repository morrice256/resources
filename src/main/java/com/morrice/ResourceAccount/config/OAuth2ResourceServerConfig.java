package com.morrice.ResourceAccount.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {
		
    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config.tokenServices(tokenServices());
    }
 
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
    
    //TODO: Change magic number
    /**
     * Asymmetric disabled because signature encode fail  
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
    	JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    	converter.setSigningKey("123");
//        Resource resource = new ClassPathResource("public.txt");
//        String publicKey = null;
//        try {    	
//            publicKey = IOUtils.toString(resource.getInputStream());
//        } catch (final IOException e) {
//            throw new RuntimeException(e);
//        }
//        converter.setVerifierKey(publicKey);
        return converter;
    }
    
 
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }
}