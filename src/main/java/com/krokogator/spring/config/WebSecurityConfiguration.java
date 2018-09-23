package com.krokogator.spring.config;


import com.google.common.collect.ImmutableList;
import com.krokogator.spring.resources.user.DetailsService;
import com.krokogator.spring.resources.user.User;
import com.krokogator.spring.resources.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Component
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

    private static final ImmutableList<String> allowedOrigins =
            ImmutableList.of("http://localhost:4200", "*");

    @Autowired
    DetailsService detailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService).passwordEncoder(User.PASSWORD_ENCODER);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .expressionHandler(webExpressionHandler())
                //Public endpoint for user registration
                .antMatchers("/users").permitAll()

                //Public API documentation
                .antMatchers("/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/",
                        "/csrf").permitAll()

                .antMatchers("/v2/api-docs").hasRole("ADMIN")
                //.anyRequest().hasRole("ADMIN")
                //.anyRequest().permitAll()

                //All other endpoints that require authentication
                //Now use @Secured to secure each endpoint
                //.anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .cors();

        //http.authorizeRequests().anyRequest().authenticated();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_MODERATOR > ROLE_USER");
        return roleHierarchy;
    }

    @Bean
    public RoleHierarchyVoter roleVoter() {
        return new RoleHierarchyVoter(roleHierarchy());
    }

    private SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {
        DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
        defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchy());
        return defaultWebSecurityExpressionHandler;
    }


}


/** KOPIA ZAPASOWA - publiczny swagger + zablokowane API na hasło generowane przez serwer */

//@Configuration
//@EnableWebSecurity
//public class EndpointAccessConfiguration extends WebSecurityConfigurerAdapter {
//
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        allowOnlyApi(http);
////        allowEveryone(http);
//    }
//
//    private void allowEveryone(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .regexMatchers(".*")
//                .permitAll().and()
//                .csrf().disable();
//        super.configure(http);
//
//    }
//
//
//    private void allowOnlyApi(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                //.antMatchers(HttpMethod.GET,"/notes/**", "/folders/**").permitAll()
//                //Allow swagger
//                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**", "/").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
////                .and()
////            .httpBasic()
//        ;
//    }
//
//}
