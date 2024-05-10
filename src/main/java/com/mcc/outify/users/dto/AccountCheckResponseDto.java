package com.mcc.outify.users.dto;

import com.mcc.outify.common.StatusResponseCode;
import com.mcc.outify.common.StatusResponseDto;
import com.mcc.outify.common.StatusResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class AccountCheckResponseDto extends StatusResponseDto {

    private AccountCheckResponseDto() {
        super();
    }

    public static ResponseEntity<AccountCheckResponseDto> success() {
        AccountCheckResponseDto responseBody = new AccountCheckResponseDto();

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<StatusResponseDto> duplicateAccount() {
        StatusResponseDto responseBody = new StatusResponseDto(StatusResponseCode.DUPLICATE_ACCOUNT, StatusResponseMessage.DUPLICATE_ACCOUNT);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

}
