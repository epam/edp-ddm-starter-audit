package com.epam.digital.data.platform.starter.audit.converter;

import com.epam.digital.data.platform.starter.audit.model.AuditEvent;
import com.epam.digital.data.platform.starter.audit.model.EventType;
import com.epam.digital.data.platform.starter.audit.model.generated.AvroAuditEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class AuditToAvroConverterTest {

  private static final String APP_NAME = "app";
  private static final String EVENT_NAME = "event";
  private static final String REQUEST_ID = "1";
  private static final String USER_NAME = "user";
  private static final String USER_ID = "user_id";
  private static final long TIME = 1618578000000L;

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final AuditToAvroConverter auditToAvroConverter = new AuditToAvroConverter(objectMapper);

  @Test
  void expectAuditEventIsConvertedToAvro() {
    AuditEvent testEvent = getTestEvent();

    AvroAuditEvent actual = auditToAvroConverter.convert(testEvent);

    assertThat(actual.getApplication()).isEqualTo(APP_NAME);
    assertThat(actual.getName()).isEqualTo(EVENT_NAME);
    assertThat(actual.getRequestId()).isEqualTo(REQUEST_ID);
    assertThat(actual.getUserName()).isEqualTo(USER_NAME);
    assertThat(actual.getUserId()).isEqualTo(USER_ID);
    assertThat(actual.getTimestamp()).isEqualTo(TIME);
    assertThat(actual.getType()).isEqualTo("USER_ACTION");
    assertThat(actual.getContext()).isEqualTo("{\"id\":\"2\"}");
  }

  private AuditEvent getTestEvent() {
    return AuditEvent.AuditEventBuilder.anAuditEvent()
            .application(APP_NAME)
            .name(EVENT_NAME)
            .requestId(REQUEST_ID)
            .userId(USER_ID)
            .userName(USER_NAME)
            .currentTime(TIME)
            .eventType(EventType.USER_ACTION)
            .context(Map.of("id", "2"))
            .build();
  }
}