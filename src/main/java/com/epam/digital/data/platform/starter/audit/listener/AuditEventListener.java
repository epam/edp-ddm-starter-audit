/*
 * Copyright 2021 EPAM Systems.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
