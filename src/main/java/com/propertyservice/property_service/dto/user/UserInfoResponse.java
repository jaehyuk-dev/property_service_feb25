package com.propertyservice.property_service.dto.user;

import com.propertyservice.property_service.domain.office.OfficeUser;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserInfoResponse {
    private String name;
    private String email;
    private String phoneNumber;
    private String role;
    private String officeName;

    public static UserInfoResponse from(OfficeUser user) {
        return UserInfoResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().getLabel())
                .officeName(user.getOffice() != null ? user.getOffice().getOfficeName() : null)
                .build();
    }
}