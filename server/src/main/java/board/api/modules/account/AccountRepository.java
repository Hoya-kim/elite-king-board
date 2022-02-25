package board.api.modules.account;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByEmail(String email);


    Optional<Account> findByEmail(String email);
}
