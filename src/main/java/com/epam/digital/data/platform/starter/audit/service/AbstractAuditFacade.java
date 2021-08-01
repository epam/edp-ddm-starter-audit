package com.epam.digital.data.platform.starter.audit.service;

import com.epam.digital.data.platform.starter.audit.model.AuditEvent;
import com.epam.digital.data.platform.starter.audit.model.EventType;
import java.time.Clock;
import java.util.Map;

public abstract class AbstractAuditFacade {

  protected final AuditService auditService;
  private final String appName;
  private final Clock clock;

  protected AbstractAuditFacade(String appName, AuditService auditService, Clock clock) {
    this.appName = appName;
    this.auditService = auditService;
    this.clock = clock;
  }

  protected GroupedAuditEventBuilder createBaseAuditEvent(
      EventType eventType, String eventName, String requestId) {
    return new GroupedAuditEventBuilder(eventType, eventName, requestId);
  }

  protected class GroupedAuditEventBuilder {

    private final AuditEvent.AuditEventBuilder builder;

    private GroupedAuditEventBuilder(
        EventType eventType, String eventName, String requestId) {
      builder = AuditEvent.AuditEventBuilder.anAuditEvent()
          .application(appName)
          .currentTime(clock.millis())
          .eventType(eventType)
          .name(eventName)
          .requestId(requestId);
    }

    public GroupedAuditEventBuilder setBusinessProcessInfo(
        String sourceSystem,
        String sourceBusinessId,
        String sourceBusinessProcess) {
      builder.sourceSystem(sourceSystem)
          .sourceBusinessId(sourceBusinessId)
          .sourceBusinessProcess(sourceBusinessProcess);
      return this;
    }

    public GroupedAuditEventBuilder setBusinessProcessInfo(
        String sourceSystem,
        String sourceBusinessProcess) {
      builder.sourceSystem(sourceSystem)
          .sourceBusinessProcess(sourceBusinessProcess);
      return this;
    }

    public GroupedAuditEventBuilder setUserInfo(
        String userId,
        String userName) {
      builder.userId(userId)
          .userName(userName);
      return this;
    }

    public GroupedAuditEventBuilder setContext(Map<String, Object> context) {
      builder.context(context);
      return this;
    }

    public AuditEvent build() {
      return builder.build();
    }
  }
}
