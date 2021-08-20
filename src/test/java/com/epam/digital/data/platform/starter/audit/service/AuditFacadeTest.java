package com.epam.digital.data.platform.starter.audit.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.epam.digital.data.platform.starter.audit.model.AuditEvent;
import com.epam.digital.data.platform.starter.audit.model.AuditEvent.AuditEventBuilder;
import com.epam.digital.data.platform.starter.audit.model.AuditSourceInfo;
import com.epam.digital.data.platform.starter.audit.model.AuditSourceInfo.AuditSourceInfoBuilder;
import com.epam.digital.data.platform.starter.audit.model.AuditUserInfo;
import com.epam.digital.data.platform.starter.audit.model.EventType;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuditFacadeTest {

  private static final String APP_NAME = "APP_NAME";
  private static final String REQUEST_ID = "1";
  private static final String EVENT_NAME = "event";

  private AuditFacadeImpl auditFacade;

  @Mock
  private AuditService auditService;

  private final Clock clock =
      Clock.fixed(
          LocalDateTime.of(2021, 4, 16, 16, 0).atZone(ZoneId.systemDefault()).toInstant(),
          ZoneId.systemDefault());

  @BeforeEach
  void beforeEach() {
    auditFacade = new AuditFacadeImpl(auditService, APP_NAME, clock);
  }

  @Test
  void expectCorrectAuditEventIsCreated() {
    AuditEvent actual =
        auditFacade.createBaseAuditEvent(EventType.SECURITY_EVENT, EVENT_NAME, REQUEST_ID).build();

    assertThat(actual.getApplication()).isEqualTo(APP_NAME);
    assertThat(actual.getRequestId()).isEqualTo(REQUEST_ID);
    assertThat(actual.getName()).isEqualTo(EVENT_NAME);
    assertThat(actual.getEventType()).isEqualTo(EventType.SECURITY_EVENT);
    assertThat(actual.getCurrentTime()).isEqualTo(clock.millis());
    assertThat(actual.getUserInfo()).isNull();
    assertThat(actual.getContext()).isEmpty();
  }

  @Test
  void groupedBuilderShouldBuildItem() {
    var sourceInfo = AuditSourceInfoBuilder.anAuditSourceInfo()
            .system("system")
            .application("app")
            .businessProcess("bp")
            .businessProcessInstanceId("bp id")
            .businessProcessDefinitionId("bp def")
            .businessActivity("act")
            .businessActivityInstanceId("act id")
            .build();
    var userInfo = new AuditUserInfo("user", "keycloak_id", "drfo");
    var expected = AuditEventBuilder.anAuditEvent()
        .application(APP_NAME)
        .eventType(EventType.SECURITY_EVENT)
        .requestId(REQUEST_ID)
        .name(EVENT_NAME)
        .currentTime(clock.millis())
        .sourceInfo(sourceInfo)
        .userInfo(userInfo)
        .context(Map.of("key", "value"))
        .build();

    var actual =
        auditFacade
            .createBaseAuditEvent(EventType.SECURITY_EVENT, EVENT_NAME, REQUEST_ID)
            .setSourceInfo(expected.getSourceInfo())
            .setUserInfo(expected.getUserInfo())
            .setContext(expected.getContext())
            .build();

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  private static class AuditFacadeImpl extends AbstractAuditFacade {

    protected AuditFacadeImpl(
        AuditService auditService,
        String appName,
        Clock clock) {
      super(auditService, appName, clock);
    }
  }
}
