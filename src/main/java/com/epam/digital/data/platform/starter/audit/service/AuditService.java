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
