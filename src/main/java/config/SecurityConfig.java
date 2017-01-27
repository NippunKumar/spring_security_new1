package config;

/**
 * Created by cashify on 18/1/17.
 */

import modelSecurity.JwtAuthenticationEntryPoint;
import modelSecurity.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;
    @Autowired
    @Qualifier("commence")
    private JwtAuthenticationEntryPoint unauthorizedHandler;



 /*   @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }*/

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

        //auth.authenticationProvider();
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username,password,enabled from users where username=?")
                .authoritiesByUsernameQuery("select username,role from user_roles where username=?");
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {




                   http.authorizeRequests()
                .antMatchers("/admin*").access("hasRole('ROLE_ADMIN')")
         .and()
         .formLogin().loginPage("/login").failureUrl("/login?error")
         .usernameParameter("username").passwordParameter("password")
         .and()
         .logout().logoutSuccessUrl("/login?logout")
         .and()
         .exceptionHandling().accessDeniedPage("/403")
         .and()
         .csrf();

       /* http
                // we don't need CSRF because our token is invulnerable
                .csrf().disable()

                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()
                .antMatchers("/auth*//**").permitAll()
                .anyRequest().authenticated();
        // Custom JWT based security filter
        http
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        http.headers().cacheControl();*/
    }

}
