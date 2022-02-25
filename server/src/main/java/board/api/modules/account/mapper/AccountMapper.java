package board.api.modules.account.mapper;

import board.api.modules.account.Account;
import board.api.modules.account.dto.SignUpResponseDto;

public class AccountMapper {

    public SignUpResponseDto toResponseDto(Account account) {
        return new SignUpResponseDto(account);
    }
}
