package com.mcc.outify.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
public class StatusResponseDto {

    private String code;

    private String message;

    public StatusResponseDto() {
        this.code = StatusResponseCode.SUCCESS;
        this.message = StatusResponseMessage.SUCCESS;
    }

    public static ResponseEntity<StatusResponseDto> databaseError() {
        StatusResponseDto reponseBody = new StatusResponseDto(StatusResponseCode.DATABASE_ERROR, StatusResponseMessage.DATABASE_ERROR);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(reponseBody);
    }

    public static ResponseEntity<StatusResponseDto> validationFail() {
        StatusResponseDto reponseBody = new StatusResponseDto(StatusResponseCode.VALIDATION_FAIL, StatusResponseMessage.VALIDATION_FAIL);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(reponseBody);
    }

}
