package com.example.memderadmin.app;

import com.example.memderadmin.common.PasswordValidator;
import com.example.memderadmin.exception.ExceptionMessages;
import com.example.memderadmin.exception.InvalidPasswordException;
import org.springframework.stereotype.Service;

@Service
public class MemberRegistService {
    public MemberRegistResponse regist(MemberRegistRequest request) {
        validate(request);
        return MemberRegistResponse.builder().build();
    }

    private void validate(MemberRegistRequest request) {
        if (!PasswordValidator.validate(request.password())) {
            throw new InvalidPasswordException(ExceptionMessages.INVALID_PASSWORD);
        }

    }
}
