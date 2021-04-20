package ru.diasoft.digitalq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.diasoft.digitalq.domain.*;
import ru.diasoft.digitalq.repository.SmsVerificationRepository;
import ru.diasoft.digitalq.domain.*;
import ru.diasoft.digitalq.model.SmsVerificationMessage;
import ru.diasoft.digitalq.repository.SmsVerificationRepository;
import ru.diasoft.digitalq.smsverificationcreated.publish.SmsVerificationCreatedPublishGateway;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Primary
public class SmsVerificationPrimaryService implements SmsVerificationService {

    private final SmsVerificationRepository smsverificationrepository;
    private final SmsVerificationCreatedPublishGateway messagingGateway;

    @Override
    public SmsVerificationCheckResponse dsSmsVerificationCheck(SmsVerificationCheckRequest smsVerificationCheckRequest) {
        Optional<SmsVerification> smsVerification = smsverificationrepository.findBySecretCodeAndProcessGuidAndStatus(smsVerificationCheckRequest.getCode(), smsVerificationCheckRequest.getProcessGUID(), "OK");
        return new SmsVerificationCheckResponse(smsVerification.isPresent());
    }

    @Override
    public SmsVerificationPostResponse dsSmsVerificationCreate(SmsVerificationPostRequest smsVerificationPostRequest) {
        String guid = UUID.randomUUID().toString();
        String secretCode = String.format("%04d", new Random().nextInt(10000));

        SmsVerification smsVerification =
                SmsVerification.builder()
                        .phoneNumber(smsVerificationPostRequest.getPhoneNumber())
                        .processGuid(guid)
                        .secretCode(secretCode)
                        .status("WAITING")
                        .build();

        smsverificationrepository.save(smsVerification);
        messagingGateway.smsVerificationCreated(SmsVerificationMessage.builder().guid(guid).code(secretCode).phoneNumber(smsVerificationPostRequest.getPhoneNumber()).build());
        return new SmsVerificationPostResponse(guid);
    }


}
