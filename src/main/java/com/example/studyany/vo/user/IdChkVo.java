package com.example.studyany.vo.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdChkVo {
    @NotBlank(message = "ID is nessary")
    private String id;
}
