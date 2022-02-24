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

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, AccountRepository accountRepository) {
        super(authenticationManager);
        this.accountRepository = accountRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String jwtHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        String token = request.getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");
        String email = JWT.require(Algorithm.HMAC512("cos")).build()
                .verify(token).getClaim("email").asString();

        if (email != null) {
            Account account = accountRepository.findByEmail(email);

            UserAccount userAccount = new UserAccount(account);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userAccount, userAccount.getPassword(), userAccount.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request,response);
        }
    }
}
