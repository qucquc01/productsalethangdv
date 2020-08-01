package com.example.productsale.security;

import com.example.productsale.dto.UserPrincipal;
import com.example.productsale.exception.StorageException;
import com.example.productsale.msg.Msg;
import com.example.productsale.storage.StorageService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    StorageService storageService;

    @Autowired
    SecurityProperties securityProperties;

    @Autowired
    DataSource dataSource;

    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;

    private static String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            // login
            "/auth/login",
            "/auth/sso-login",
            "/files/**",
            "/utils/db",

    };

    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);
//        if (securityProperties.getExtraAuthWhiteList() != null && !securityProperties.getExtraAuthWhiteList().isEmpty()) {
//            AUTH_WHITELIST = ArrayUtils.addAll(AUTH_WHITELIST, securityProperties.getExtraAuthWhiteList().split(","));
//        }
//
//        http.cors().configurationSource(corsConfigurationSource())
//                .and()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers(AUTH_WHITELIST).permitAll()
//                .and()
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated();

        if (securityProperties.getExtraAuthWhiteList() != null && !securityProperties.getExtraAuthWhiteList().isEmpty()) {
            AUTH_WHITELIST = ArrayUtils.addAll(AUTH_WHITELIST, securityProperties.getExtraAuthWhiteList().split(","));
        }

        http.cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config
                .tokenServices(defaultTokenServices())
                .resourceId(securityProperties.getResourceId());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }

    @Bean
    public TokenStore tokenStore(){
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    @Primary
    public DefaultTokenServices defaultTokenServices(){
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }

//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        try {
//            File publicKey = storageService.load(securityProperties.getJwt().getPublicKeyStore(), null).toFile();
//            converter.setVerifierKey(FileUtils.readFileToString(publicKey));
//        } catch (IOException e) {
//            throw new StorageException.FileNotFound(Msg.getMessage("failed.to.read.file", new Object[] {securityProperties.getJwt().getPublicKeyStore()}));
//        }
//
//        DefaultAccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
//        UserAuthenticationConverter userTokenConverter = new DefaultUserAuthenticationConverter() {
//            @Override
//            public Authentication extractAuthentication(Map<String, ?> map) {
//                Authentication authentication = super.extractAuthentication(map);
//
//                Long id = ((Number)map.get("id")).longValue();
//                String username = (String)map.get(USERNAME);
//                String fullName = (String)map.get("full_name");
//                String email = (String)map.get("email");
//                List<String> authorities = (List<String>) map.get(AUTHORITIES);
//
//                UserPrincipal userPrincipal = new UserPrincipal(id, username, fullName, email, null, true);
//                userPrincipal.setPrivileges(authorities);
//
//                return new UsernamePasswordAuthenticationToken(userPrincipal, authentication.getCredentials(), authentication.getAuthorities());
//            }
//        };
//        tokenConverter.setUserTokenConverter(userTokenConverter);
//        converter.setAccessTokenConverter(tokenConverter);
//
//        return converter;
//    }
}
