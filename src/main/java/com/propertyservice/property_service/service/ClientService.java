package com.propertyservice.property_service.service;

import com.propertyservice.property_service.domain.client.Client;
import com.propertyservice.property_service.domain.client.ClientExpectedTransactionType;
import com.propertyservice.property_service.domain.client.ClientRemark;
import com.propertyservice.property_service.domain.client.ShowingProperty;
import com.propertyservice.property_service.domain.client.enums.ClientStatus;
import com.propertyservice.property_service.domain.common.eums.Gender;
import com.propertyservice.property_service.domain.common.eums.TransactionType;
import com.propertyservice.property_service.domain.schedule.Schedule;
import com.propertyservice.property_service.dto.client.ClientDetailResponse;
import com.propertyservice.property_service.dto.client.ClientRegisterRequest;
import com.propertyservice.property_service.dto.client.ClientSummaryDto;
import com.propertyservice.property_service.dto.client.ShowingPropertyDto;
import com.propertyservice.property_service.dto.common.RemarkDto;
import com.propertyservice.property_service.dto.common.SearchCondition;
import com.propertyservice.property_service.dto.schedule.ScheduleDto;
import com.propertyservice.property_service.error.ErrorCode;
import com.propertyservice.property_service.error.exception.BusinessException;
import com.propertyservice.property_service.repository.client.ClientExpectedTransactionTypeRepository;
import com.propertyservice.property_service.repository.client.ClientRemarkRepository;
import com.propertyservice.property_service.repository.client.ClientRepository;
import com.propertyservice.property_service.repository.client.ShowingPropertyRepository;
import com.propertyservice.property_service.repository.office.OfficeUserRepository;
import com.propertyservice.property_service.repository.schedule.ScheduleRepository;
import com.propertyservice.property_service.utils.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientExpectedTransactionTypeRepository clientExpectedTransactionTypeRepository;
    private final ClientRemarkRepository clientRemarkRepository;
    private final OfficeService officeService;
    private final OfficeUserRepository officeUserRepository;
    private final ScheduleRepository scheduleRepository;
    private final ShowingPropertyRepository showingPropertyRepository;

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
                        .expectedMoveInDate(DateTimeUtil.parseYYYYMMDD(request.getExpectedMoveInDate()).orElseThrow(
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
        return clientRepository.searchClientSummaryList(searchCondition, officeService.getCurrentUserEntity().getOffice().getId());
    }

    public ClientDetailResponse searchClientDetailInfo(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(
                () -> new BusinessException(ErrorCode.CLIENT_NOT_FOUND)
        );

        List<String> clientExpectedTransactionTypeList = new ArrayList<>();
        for (ClientExpectedTransactionType type : clientExpectedTransactionTypeRepository.findAllByClient(client)) {
            clientExpectedTransactionTypeList.add(type.getExpectedTransactionType().getLabel());
        }

        List<ScheduleDto> scheduleDtoList = new ArrayList<>();
        for (Schedule schedule : scheduleRepository.findAllByClient(client)) {
            scheduleDtoList.add(
                    ScheduleDto.builder()
                            .scheduleId(schedule.getId())
                            .clientName(schedule.getClient().getName())
                            .picUserName(schedule.getClient().getPicUser().getName())
                            .scheduleType(schedule.getScheduleType().getLabel())
                            .scheduleRemark(schedule.getRemark())
                            .isCompleted(false)
                            .build()
            );
        }

        List<ShowingPropertyDto> showingPropertyDtoList = showingPropertyRepository.searchShowingPropertyByClient(client);

        List<RemarkDto> clientRemarkList = new ArrayList<>();
        for (ClientRemark clientRemark : clientRemarkRepository.findAllByClient(client)) {
            clientRemarkList.add(
                    RemarkDto.builder()
                            .remarkId(clientRemark.getId())
                            .remark(clientRemark.getRemark())
                            .createdAt(clientRemark.getCreatedDate().toLocalDate())
                            .createdBy(Objects.requireNonNull(officeUserRepository.findById(clientRemark.getCreatedByUserId()).orElse(null)).getName())
                            .build()
            );
        }

        return ClientDetailResponse.builder()
                .clientId(client.getId())
                .clientPicUser(client.getPicUser().getName())
                .clientStatus(client.getStatus().getLabel())
                .clientName(client.getName())
                .clientPhoneNumber(client.getPhoneNumber())
                .clientGender(client.getGender().getLabel())
                .clientSource(client.getSource())
                .clientType(client.getType())
                .clientExpectedMoveInDate(client.getExpectedMoveInDate())
                .clientExpectedTransactionTypeList(clientExpectedTransactionTypeList)

                .scheduleList(scheduleDtoList)
                .showingPropertyList(showingPropertyDtoList)
                .clientRemarkList(clientRemarkList)
                .build();
    }
}
