package com.mcc.outify.users.service;

import com.mcc.outify.users.dto.AccountCheckRequestDto;
import com.mcc.outify.users.dto.AccountCheckResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<? super AccountCheckResponseDto> accountCheck(AccountCheckRequestDto dto);

}