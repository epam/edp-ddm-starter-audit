package com.epam.digital.data.platform.starter.audit.service;

import com.epam.digital.data.platform.starter.audit.converter.AuditToAvroConverter;
import com.epam.digital.data.platform.starter.audit.model.AuditEvent;
import com.epam.digital.data.platform.starter.audit.model.generated.AvroAuditEvent;
import java.util.Map;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditServiceTest {

  private AuditService auditService;

  @Mock
  private ApplicationEventPublisher applicationEventPublisher;
  @Mock
  private AuditToAvroConverter auditToAvroConverter;

  @BeforeEach
  void beforeEach() {
    auditService = new AuditService(applicationEventPublisher, auditToAvroConverter);
  }

  @Test
  void expectAvroAuditIsPublished() {
    AuditEvent testAuditEvent = new AuditEvent();
    AvroAuditEvent avroAuditEvent = new AvroAuditEvent();
    when(auditToAvroConverter.convert(testAuditEvent))
            .thenReturn(avroAuditEvent);

    auditService.sendAudit(testAuditEvent);

    verify(auditToAvroConverter).convert(testAuditEvent);
    verify(applicationEventPublisher).publishEvent(avroAuditEvent);
  }

  @Test
  void shouldReturnEmptyMap() {
    var context = auditService.createContext(null, null, null, null, null, null);

    assertThat(context).isEmpty();
  }

  @Test
  void shouldReturnAllFields() {
    String action = "ACTION";
    String step = "STEP";
    String tableName = "TABLE_NAME";
    String rowId = "42";
    Set<String> fields = Set.of("first", "second");
    String result = "RESULT";

    var context = auditService.createContext(action, step, tableName, rowId, fields, result);

    assertThat(context).containsAllEntriesOf(Map.of(
        "action", action,
        "step", step,
        "tablename", tableName,
        "row_id", rowId,
        "fields", fields,
        "result", result
        ));
  }
}
