package com.epam.digital.data.platform.starter.audit.service;

import com.epam.digital.data.platform.starter.audit.converter.AuditToAvroConverter;
import com.epam.digital.data.platform.starter.audit.model.AuditEvent;
import com.epam.digital.data.platform.starter.audit.model.generated.AvroAuditEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class AuditService {

  private static final String ACTION = "action";
  private static final String STEP = "step";
  private static final String TABLE_NAME = "tablename";
  private static final String ROW_ID = "row_id";
  private static final String FIELDS = "fields";
  private static final String RESULT = "result";

  private final ApplicationEventPublisher publisher;
  private final AuditToAvroConverter auditToAvroConverter;

  public AuditService(ApplicationEventPublisher publisher,
      AuditToAvroConverter auditToAvroConverter) {
    this.publisher = publisher;
    this.auditToAvroConverter = auditToAvroConverter;
  }

  public void sendAudit(AuditEvent auditEvent) {
    AvroAuditEvent avroEvent = auditToAvroConverter.convert(auditEvent);
    publisher.publishEvent(avroEvent);
  }

  public Map<String, Object> createContext(String action, String step, String tableName,
      String rowId, Set<String> fields, String result) {
    Map<String, Object> context = new HashMap<>();
    putIfNotNull(context, ACTION, action);
    putIfNotNull(context, STEP, step);
    putIfNotNull(context, TABLE_NAME, tableName);
    putIfNotNull(context, ROW_ID, rowId);
    putIfNotNull(context, FIELDS, fields);
    putIfNotNull(context, RESULT, result);
    return context;
  }

  private void putIfNotNull(Map<String, Object> context, String key, Object value) {
    if (value != null) {
      context.put(key, value);
    }
  }
}
