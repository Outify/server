package com.mcc.outify.users.service;

import com.mcc.outify.common.StatusResponseDto;
import com.mcc.outify.users.dto.AccountCheckRequestDto;
import com.mcc.outify.users.dto.AccountCheckResponseDto;
import com.mcc.outify.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<? super AccountCheckResponseDto> accountCheck(AccountCheckRequestDto requestDto) {
        try {
            String account = requestDto.getAccount();
            boolean isExistAccount = userRepository.existsByAccount(account);
            if (isExistAccount) return AccountCheckResponseDto.duplicateAccount();
        } catch (Exception e) {
            e.printStackTrace();
            return StatusResponseDto.databaseError();
        }

        return AccountCheckResponseDto.success();
    }

}
