package com.mcc.outify.users.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AccountCheckRequestDto {

    @NotBlank
    private String account;

}
