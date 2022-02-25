package board.api.config;

import board.api.authentication.JwtAuthenticationFilter;
import board.api.authentication.JwtAuthorizationFilter;
import board.api.modules.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final AccountRepository accountRepository;

    @Value("${app.jwtSecretKey}")
    private String jwtSecretKey;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsFilter)
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtSecretKey));
//                .addFilter(new JwtAuthorizationFilter(authenticationManager(), accountRepository, jwtSecretKey));

        http.
                authorizeRequests()
//                .mvcMatchers("/", "/accounts/sign-up", "/login", "/accounts/authentication-mail")
                .anyRequest().permitAll();

        http.
                formLogin().disable()
                .httpBasic().disable()
                .logout()
                .logoutSuccessUrl("/");
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
