package board.api.authentication;

import board.api.modules.account.Account;
import board.api.utils.RequestUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final String jwtSecretKey;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        Authentication authentication = null;
        log.info("로그인 시도 Address: {}", RequestUtils.getRemoteAddress(request));

        try {
            UsernamePasswordAuthenticationToken authenticationToken = createAuthenticationToken(request);
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            log.error("로그인 시도 - Email, Password 읽기 실패 Address {}", RequestUtils.getRemoteAddress(request));
        }

        log.info("로그인 성공 Address: {}, {}", RequestUtils.getRemoteAddress(request), authentication.getPrincipal());
        return authentication;
    }

    private UsernamePasswordAuthenticationToken createAuthenticationToken(HttpServletRequest request)
            throws IOException {
        Account account = objectMapper.readValue(request.getInputStream(), Account.class);
        return new UsernamePasswordAuthenticationToken(account.getEmail(), account.getPassword());
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException {
        UserAccount userAccount = (UserAccount) authResult.getPrincipal();

        String jwtToken = createJwtToken(userAccount);
        addAuthorizationHeader(response, jwtToken);
        addNicknameToBody(response, userAccount);
    }

    private String createJwtToken(UserAccount userAccount) {
        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", userAccount.getAccount().getId())
                .withClaim("email", userAccount.getAccount().getEmail())
                .sign(Algorithm.HMAC512(jwtSecretKey));
    }

    private void addAuthorizationHeader(HttpServletResponse response, String jwtToken) {
        response.addHeader(HttpHeaders.AUTHORIZATION, JwtProperties.TOKEN_PREFIX + jwtToken);
    }

    private void addNicknameToBody(HttpServletResponse response, UserAccount userAccount) throws IOException {
        response.getWriter().println(objectMapper.writeValueAsString(new HashMap<>(){
            {
                put("accountId", userAccount.getAccount().getId());
                put("nickname", userAccount.getAccount().getNickname());
            }
        }));
    }
}
