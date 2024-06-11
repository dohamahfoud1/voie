package com.voie.project.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.voie.project.service.UserDetailService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/connexion", "/traiter_connexion").permitAll()
            .antMatchers("/admin", "/gestionUsers", "/gestionProfils","societe","cloturerExercice","banque","compteBancaire","modepaiement","listeDevises").hasAuthority("admin")
           .anyRequest().authenticated() 
            .and()
            .formLogin()
                .loginPage("/connexion")
                .loginProcessingUrl("/traiter_connexion")
                .defaultSuccessUrl("/", true)
                .failureUrl("/connexion?error")
                .successHandler(this::loginSuccessHandler)
                .usernameParameter("nom")
                .passwordParameter("mot_de_passe")
                .permitAll()
            .and()
            .logout()
                .logoutUrl("/deconnexion")
                .logoutSuccessUrl("/connexion?logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
            .and()
            .exceptionHandling()
                .accessDeniedPage("/connexion?accessDenied")
            .and()
            .csrf().disable();
    }


    private void loginSuccessHandler(
              HttpServletRequest request,
              HttpServletResponse response,
              Authentication authentication) throws IOException {

              String redirectURL = "/";
              Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

              for (GrantedAuthority authority : authorities) {
                  String profile = authority.getAuthority();
                  if ("admin".equals(profile)) {
                      redirectURL = "/admin";
                      break;
                  } else {
                      redirectURL = "/profile";
                      break;
                  }
              }

              response.sendRedirect(request.getContextPath() + redirectURL);
            }
}
