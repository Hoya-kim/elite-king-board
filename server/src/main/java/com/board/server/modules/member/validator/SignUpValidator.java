package com.board.server.modules.member.validator;

import com.board.server.config.CacheConfig;
import com.board.server.modules.member.AccountRepository;
import com.board.server.modules.member.dto.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class SignUpValidator {

    private static final String EXISTS_EMAIL_ERROR_MESSAGE = "이미 사용중인 이메일 입니다.";

    private final AccountRepository accountRepository;
    private final CacheManager cacheManager;

    public void validate(Object target, Errors errors) {
        SignUpRequestDto signUpRequestDto = (SignUpRequestDto) target;
        String email = signUpRequestDto.getEmail();

        if (isExistsInCache(email) || accountRepository.existsByEmail(email)) {
            errors.rejectValue("email", "duplicate.email", EXISTS_EMAIL_ERROR_MESSAGE);
        }
    }

    private boolean isExistsInCache(String email) {
        return cacheManager.getCache(CacheConfig.ACCOUNT_CACHE_NAME).get(email) != null;
    }
}
