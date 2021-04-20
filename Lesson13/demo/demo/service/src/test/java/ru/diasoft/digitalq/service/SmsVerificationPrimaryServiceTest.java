package ru.diasoft.digitalq.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.diasoft.digitalq.domain.*;
import ru.diasoft.digitalq.repository.SmsVerificationRepository;
import ru.diasoft.digitalq.smsverificationcreated.publish.SmsVerificationCreatedPublishGateway;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsVerificationPrimaryServiceTest {
    @Mock
    private SmsVerificationRepository repository;

    @Mock
    private SmsVerificationCreatedPublishGateway messagingGateway;

    @Autowired
    private SmsVerificationPrimaryService service;

    private final String GUID = UUID.randomUUID().toString();
    private final String PHONE_NUMBER = "89205555555";
    private final String VALID_CODE = "7689";
    private final String INVALID_CODE = "7681";
    private final String STATUS = "OK";

    @Before
    public void init(){
        service = new SmsVerificationPrimaryService(repository, messagingGateway);
        SmsVerification smsVerification =
                SmsVerification.builder()
                        .phoneNumber(PHONE_NUMBER)
                        .processGuid(GUID)
                        .secretCode(VALID_CODE)
                        .status(STATUS)
                        .build();
        when(repository.findBySecretCodeAndProcessGuidAndStatus(VALID_CODE, GUID, STATUS))
          .thenReturn(Optional.of(smsVerification));
        when(repository.findBySecretCodeAndProcessGuidAndStatus(INVALID_CODE, GUID, STATUS))
          .thenReturn(Optional.empty());
    }

    @Test
    public void dsSmsVerificationCheck() {
        SmsVerificationCheckResponse response = service.dsSmsVerificationCheck(new SmsVerificationCheckRequest(GUID, VALID_CODE));
        Assert.assertEquals(response.getCheckResult(), true);
    }

    @Test
    public void dsSmsVerificationCheckInvalidCode() {
        SmsVerificationCheckResponse response = service.dsSmsVerificationCheck(new SmsVerificationCheckRequest(GUID, INVALID_CODE));
        Assert.assertEquals(response.getCheckResult(), false);
    }

    @Test
    public void dsSmsVerificationCreate() {
        SmsVerificationPostResponse response = service.dsSmsVerificationCreate(new SmsVerificationPostRequest(PHONE_NUMBER));
        Assert.assertNotNull(response.getProcessGUID());
    }


}
