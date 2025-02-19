package com.propertyservice.property_service.service;

import com.propertyservice.property_service.domain.client.Client;
import com.propertyservice.property_service.domain.client.ClientExpectedTransactionType;
import com.propertyservice.property_service.domain.client.ClientRemark;
import com.propertyservice.property_service.domain.client.enums.ClientStatus;
import com.propertyservice.property_service.domain.common.eums.Gender;
import com.propertyservice.property_service.domain.common.eums.TransactionType;
import com.propertyservice.property_service.dto.client.ClientRegisterRequest;
import com.propertyservice.property_service.dto.client.ClientSummaryDto;
import com.propertyservice.property_service.dto.common.SearchCondition;
import com.propertyservice.property_service.error.ErrorCode;
import com.propertyservice.property_service.error.exception.BusinessException;
import com.propertyservice.property_service.repository.client.ClientExpectedTransactionTypeRepository;
import com.propertyservice.property_service.repository.client.ClientRemarkRepository;
import com.propertyservice.property_service.repository.client.ClientRepository;
import com.propertyservice.property_service.repository.office.OfficeUserRepository;
import com.propertyservice.property_service.utils.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientExpectedTransactionTypeRepository clientExpectedTransactionTypeRepository;
    private final ClientRemarkRepository clientRemarkRepository;
    private final OfficeService officeService;

    @Transactional
    public void registerClient(ClientRegisterRequest request) {
        Client newClient = saveClientInfo(request);
        saveClientExpectedTransactionType(request, newClient);
        saveClientRemark(request.getClientRemark(), newClient);
    }

    private Client saveClientInfo(ClientRegisterRequest request) {
        return clientRepository.save(
                Client.builder()
                        .pocOffice(officeService.getCurrentUserEntity().getOffice())
                        .picUser(officeService.getCurrentUserEntity())
                        .status(ClientStatus.CONSULTING)
                        .name(request.getClientName())
                        .phoneNumber(request.getClientPhoneNumber())
                        .gender(Gender.fromValue(request.getClientGenderCode()))
                        .source(getValidValue(request.getClientSource()))
                        .type(getValidValue(request.getClientType()))
                        .moveInDate(DateTimeUtil.parseYYYYMMDD(request.getExpectedMoveInDate()).orElseThrow(
                                () -> new BusinessException(ErrorCode.INVALID_DATE_FORMAT)
                        ))
                        .build()
        );
    }

    private void saveClientExpectedTransactionType(ClientRegisterRequest request, Client newClient) {
        for (Integer i : request.getExpectedTransactionTypeCodeList()) {
            clientExpectedTransactionTypeRepository.save(
                    ClientExpectedTransactionType.builder()
                            .client(newClient)
                            .expectedTransactionType(TransactionType.fromValue(i))
                            .build()
            );
        }
    }

    private void saveClientRemark(String remark, Client newClient) {
        clientRemarkRepository.save(
                ClientRemark.builder()
                        .client(newClient)
                        .remark(remark)
                        .build()
        );
    }

    private String getValidValue(String value) {
        return (value == null || value.trim().isEmpty()) ? "기타" : value;
    }

    public List<ClientSummaryDto> searchClientSummaryInfoList(SearchCondition searchCondition) {
        return clientRepository.searchClientSummaryList(searchCondition, officeService.getCurrentUserEntity().getOffice().getOfficeId());
    }
}
