package com.epam.digital.data.platform.starter.audit.converter;

import com.epam.digital.data.platform.starter.audit.model.AuditEvent;
import com.epam.digital.data.platform.starter.audit.model.generated.AvroAuditEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

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
    return AvroAuditEvent.newBuilder()
            .setRequestId(auditEvent.getRequestId())
            .setApplication(auditEvent.getApplication())
            .setSourceSystem(auditEvent.getSourceSystem())
            .setSourceBusinessId(auditEvent.getSourceBusinessId())
            .setSourceBusinessProcess(auditEvent.getSourceBusinessProcess())
            .setName(auditEvent.getName())
            .setType(auditEvent.getEventType().toString())
            .setTimestamp(auditEvent.getCurrentTime())
            .setUserId(auditEvent.getUserId())
            .setUserName(auditEvent.getUserName())
            .setContext(jsonContext)
            .build();
  }
}