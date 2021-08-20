package com.epam.digital.data.platform.starter.audit.converter;

import com.epam.digital.data.platform.starter.audit.model.AuditEvent;
import com.epam.digital.data.platform.starter.audit.model.AuditSourceInfo;
import com.epam.digital.data.platform.starter.audit.model.AuditUserInfo;
import com.epam.digital.data.platform.starter.audit.model.generated.AvroAuditEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditToAvroConverter {

  private final ObjectMapper objectMapper;

  public AuditToAvroConverter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public AvroAuditEvent convert(AuditEvent auditEvent) {
    String jsonContext;
    try {
      jsonContext = objectMapper.writeValueAsString(auditEvent.getContext());
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Couldn't serialize object", e);
    }
    var sourceInfo = Optional.ofNullable(auditEvent.getSourceInfo())
            .orElse(new AuditSourceInfo());
    var userInfo = Optional.ofNullable(auditEvent.getUserInfo())
            .orElse(new AuditUserInfo());
    return AvroAuditEvent.newBuilder()
        .setRequestId(auditEvent.getRequestId())
        .setApplicationName(auditEvent.getApplication())
        .setName(auditEvent.getName())
        .setSourceSystem(sourceInfo.getSystem())
        .setSourceApplication(sourceInfo.getApplication())
        .setSourceBusinessProcess(sourceInfo.getBusinessProcess())
        .setSourceBusinessProcessDefinitionId(sourceInfo.getBusinessProcessDefinitionId())
        .setSourceBusinessProcessInstanceId(sourceInfo.getBusinessProcessInstanceId())
        .setSourceBusinessActivity(sourceInfo.getBusinessActivity())
        .setSourceBusinessActivityId(sourceInfo.getBusinessActivityInstanceId())
        .setType(auditEvent.getEventType().toString())
        .setTimestamp(auditEvent.getCurrentTime())
        .setUserName(userInfo.getUserName())
        .setUserKeycloakId(userInfo.getUserKeycloakId())
        .setUserDrfo(userInfo.getUserDrfo())
        .setContext(jsonContext)
        .build();
  }
}