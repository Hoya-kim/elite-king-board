package board.api.authentication;

public interface JwtProperties {

    String TOKEN_PREFIX = "Bearer ";
    int EXPIRATION_TIME = 1_800_000;
}
