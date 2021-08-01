package com.epam.digital.data.platform.starter.audit.listener;

import com.epam.digital.data.platform.starter.audit.model.generated.AvroAuditEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AuditEventListener {

  private final String auditTopic;

  private final KafkaTemplate<String, AvroAuditEvent> auditReplyTemplate;

  public AuditEventListener(@Value("${audit.kafka.topic}") String auditTopic,
          KafkaTemplate<String, AvroAuditEvent> auditReplyTemplate) {
    this.auditTopic = auditTopic;
    this.auditReplyTemplate = auditReplyTemplate;
  }

  @Async
  @EventListener
  public void auditListener(AvroAuditEvent event) {
    auditReplyTemplate.send(auditTopic, event);
  }
}
