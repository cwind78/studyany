package com.example.studyany.vo.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginVo {
    @NotBlank(message = "ID is nessary")
    private String id;
    @NotBlank(message = "Password is nessary")
    private String pwd;
}
