package com.mcc.outify.apis;

import com.mcc.outify.users.dto.AccountCheckRequestDto;
import com.mcc.outify.users.dto.AccountCheckResponseDto;
import com.mcc.outify.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/account-check")
    public ResponseEntity<? super AccountCheckResponseDto> accountCheck
            (@RequestBody @Valid AccountCheckRequestDto requestDto) {
        ResponseEntity<? super AccountCheckResponseDto> resData = userService.accountCheck(requestDto);

        return resData;
    }

}
