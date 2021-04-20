package ru.diasoft.digitalq.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.diasoft.digitalq.domain.SmsVerification;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsVerificationRepositoryTest {

  @Autowired
  SmsVerificationRepository repository;

  private final String PHONE_NUMBER = "89205555555";
  private final String SECRET_CODE = "7689";
  private final String INV_SECRET_CODE = "7681";
  private final String STATUS_WAIT = "WAITING";
  private final String STATUS_OK = "OK";

    @Test
    public void smsVerificationCreateTest() {
        final String processGuid = UUID.randomUUID().toString();

        SmsVerification smsVerification =
                SmsVerification.builder()
                        .processGuid(processGuid)
                        .phoneNumber(PHONE_NUMBER)
                        .secretCode(SECRET_CODE)
                        .status(STATUS_WAIT)
                        .build();

        SmsVerification createdEntity = repository.save(smsVerification);
        smsVerification.setVerificationId(createdEntity.getVerificationId());

        Assert.assertEquals(smsVerification, createdEntity);
        Assert.assertNotNull(createdEntity);
    }

    @Test
    public void findBySecretCodeAndProcessGuidAndStatus() {
        final String processGuid = UUID.randomUUID().toString();

        SmsVerification smsVerification =
                SmsVerification.builder()
                        .processGuid(processGuid)
                        .phoneNumber(PHONE_NUMBER)
                        .secretCode(SECRET_CODE)
                        .status(STATUS_OK)
                        .build();

        SmsVerification createdEntity = repository.save(smsVerification);

        Optional<SmsVerification> validCode = repository.findBySecretCodeAndProcessGuidAndStatus(SECRET_CODE, processGuid, STATUS_OK);
        assertTrue(validCode.isPresent());

        Optional<SmsVerification> invalidCode = repository.findBySecretCodeAndProcessGuidAndStatus(INV_SECRET_CODE, processGuid, STATUS_OK);
        assertFalse(invalidCode.isPresent());

    }

    @Test
    public void updateStatusByProcessGuidTest() {
        String processGuid = UUID.randomUUID().toString();
        SmsVerification smsVerification = SmsVerification.builder()
                .processGuid(processGuid)
                .phoneNumber(PHONE_NUMBER)
                .secretCode(SECRET_CODE)
                .status(STATUS_WAIT)
                .build();
        SmsVerification createdEntity = repository.save(smsVerification);
        repository.updateStatusByProcessGuid(STATUS_OK, processGuid);
        assertThat(repository.findById(createdEntity.getVerificationId()).orElse(smsVerification.builder().build()).getStatus()).isEqualTo(STATUS_OK);

    }

}
