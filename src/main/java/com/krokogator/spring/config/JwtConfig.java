package com.krokogator.spring.config;

import com.krokogator.spring.config.jwt.CustomJwtAccessTokenConverter;
import com.krokogator.spring.config.jwt.SecretKeyProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@Configuration
public class JwtConfig {

    @Autowired
    private SecretKeyProvider keyProvider;

    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setTokenEnhancer(accessTokenConverter());
        defaultTokenServices.setAccessTokenValiditySeconds(3600);
        defaultTokenServices.setRefreshTokenValiditySeconds(2678400);
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new CustomJwtAccessTokenConverter();
        try {
            converter.setSigningKey(keyProvider.getKey());
        } catch (KeyStoreException | NoSuchAlgorithmException | IOException | UnrecoverableKeyException | CertificateException e) {
            e.printStackTrace();
        }

        return converter;
    }








}
