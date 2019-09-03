package residentEvil.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf()
                .csrfTokenRepository(csrfTokenRepository())
        .and()
                .authorizeRequests()
                .mvcMatchers("/css/**", "/js/**").permitAll()
                .mvcMatchers("/", "/login", "/register").permitAll()
                .mvcMatchers("/home", "/logout", "/viruses/show")
                                .hasAnyAuthority("USER", "MODERATOR")
                .mvcMatchers("/viruses/add", "/viruses/edit", "/viruses/delete")
                                .hasAnyAuthority("MODERATOR")
        .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/home")
//         .and()
//                .rememberMe()
//                .rememberMeParameter("rememberMe")
//                .key("REMEMBERMEKEY")
//                .userDetailsService(userService)
//                .rememberMeCookieName("REMEMBERMECOOKIE")
//                .tokenValiditySeconds(1200)
         .and()
                .logout()
         .and()
                .exceptionHandling()
                .accessDeniedPage("/unauthorized")
        ;

    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository =
                new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }
}
