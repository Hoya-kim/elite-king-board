package board.api.authentication;

import board.api.modules.account.Account;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class UserAccount extends User {

    private final Account account;

    public UserAccount(Account account) {
        super(account.getEmail(), account.getPassword(),
                List.of(new SimpleGrantedAuthority(account.getRole().getKey())));
        this.account = account;
    }
}
