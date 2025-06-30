package com.propertyservice.property_service.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeRequest {

    @NotBlank(message = "새 비밀번호는 필수입니다.")
    @Size(min = 8, max = 255, message = "비밀번호는 8자 이상이어야 합니다.")
    @Schema(description = "새 비밀번호", example = "newPassword123")
    private String newPassword;

    @NotBlank(message = "비밀번호 확인은 필수입니다.")
    @Schema(description = "새 비밀번호 확인", example = "newPassword123")
    private String newPasswordConfirm;
}