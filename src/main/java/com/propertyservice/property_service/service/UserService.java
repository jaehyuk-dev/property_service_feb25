package com.propertyservice.property_service.service;

import com.propertyservice.property_service.domain.office.OfficeUser;
import com.propertyservice.property_service.dto.user.PasswordChangeRequest;
import com.propertyservice.property_service.dto.user.UserInfoResponse;
import com.propertyservice.property_service.error.ErrorCode;
import com.propertyservice.property_service.error.exception.BusinessException;
import com.propertyservice.property_service.repository.office.OfficeUserRepository;
import com.propertyservice.property_service.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final OfficeUserRepository officeUserRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 현재 사용자 정보 조회
     */
    public UserInfoResponse getCurrentUserInfo() {
        String currentUserEmail = SecurityUtil.getCurrentUserEmail();
        if (currentUserEmail == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        OfficeUser user = officeUserRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return UserInfoResponse.from(user);
    }

    /**
     * 비밀번호 변경
     */
    @Transactional
    public void changePassword(PasswordChangeRequest request) {
        // 비밀번호 확인 검증
        if (!request.getNewPassword().equals(request.getNewPasswordConfirm())) {
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH);
        }

        String currentUserEmail = SecurityUtil.getCurrentUserEmail();
        if (currentUserEmail == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        OfficeUser user = officeUserRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 새 비밀번호를 암호화하여 저장
        String encodedPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPasswordHash(encodedPassword);

        officeUserRepository.save(user);

        log.info("사용자 {} 비밀번호 변경 완료", currentUserEmail);
    }
}