package board.api.authentication;

import board.api.modules.account.Account;
import board.api.modules.account.AccountRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final AccountRepository accountRepository;
    private final String jwtSecretKey;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, AccountRepository accountRepository,
            String jwtSecretKey) {
        super(authenticationManager);
        this.accountRepository = accountRepository;
        this.jwtSecretKey = jwtSecretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || invalidFormat(authorizationHeader)) {
            chain.doFilter(request, response);
            return;
        }

        String token = removePrefix(request.getHeader(HttpHeaders.AUTHORIZATION));
        String email = getEmail(token);

        if (email != null) {
            Account account = accountRepository.findByEmail(email);
            Authentication authentication = getAuthentication(account);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        }
    }

    private boolean invalidFormat(String jwtHeader) {
        return !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX);
    }

    private String removePrefix(String authorizationHeader) {
        return authorizationHeader.replace(JwtProperties.TOKEN_PREFIX, "");
    }

    private String getEmail(String token) {
        return JWT.require(Algorithm.HMAC512(jwtSecretKey)).build()
                .verify(token).getClaim("email").asString();
    }

    private UsernamePasswordAuthenticationToken getAuthentication(Account account) {
        UserAccount userAccount = new UserAccount(account);

        return new UsernamePasswordAuthenticationToken(
                userAccount, userAccount.getPassword(), userAccount.getAuthorities());
    }
}
