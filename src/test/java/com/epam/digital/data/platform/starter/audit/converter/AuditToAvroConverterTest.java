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

package com.epam.digital.data.platform.starter.audit.converter;

import com.epam.digital.data.platform.starter.audit.model.AuditEvent;
import com.epam.digital.data.platform.starter.audit.model.AuditSourceInfo;
import com.epam.digital.data.platform.starter.audit.model.AuditUserInfo;
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
  private static final String SOURCE_SYSTEM = "system";
  private static final String SOURCE_APPLICATION = "source_app";
  private static final String BUSINESS_PROCESS = "bp";
  private static final String BUSINESS_PROCESS_DEFINITION_ID = "bp_def_id";
  private static final String BUSINESS_PROCESS_INSTANCE_ID = "bp_id";
  private static final String BUSINESS_ACTIVITY = "bp_act";
  private static final String BUSINESS_ACTIVITY_INSTANCE_ID = "bp_act_id";
  private static final String USER_NAME = "user";
  private static final String USER_KEYCLOAK_ID = "user_id";
  private static final String USER_DRFO = "drfo";
  private static final long TIME = 1618578000000L;

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final AuditToAvroConverter auditToAvroConverter = new AuditToAvroConverter(objectMapper);

  @Test
  void expectAuditEventIsConvertedToAvro() {
    AuditEvent testEvent = getTestEvent(createSourceInfo(), createUserInfo());

    AvroAuditEvent actual = auditToAvroConverter.convert(testEvent);

    assertThat(actual.getApplicationName()).isEqualTo(APP_NAME);
    assertThat(actual.getName()).isEqualTo(EVENT_NAME);
    assertThat(actual.getRequestId()).isEqualTo(REQUEST_ID);
    assertThat(actual.getSourceSystem()).isEqualTo(SOURCE_SYSTEM);
    assertThat(actual.getSourceApplication()).isEqualTo(SOURCE_APPLICATION);
    assertThat(actual.getSourceBusinessProcess()).isEqualTo(BUSINESS_PROCESS);
    assertThat(actual.getSourceBusinessProcessDefinitionId()).isEqualTo(BUSINESS_PROCESS_DEFINITION_ID);
    assertThat(actual.getSourceBusinessProcessInstanceId()).isEqualTo(BUSINESS_PROCESS_INSTANCE_ID);
    assertThat(actual.getSourceBusinessActivity()).isEqualTo(BUSINESS_ACTIVITY);
    assertThat(actual.getSourceBusinessActivityId()).isEqualTo(BUSINESS_ACTIVITY_INSTANCE_ID);
    assertThat(actual.getUserName()).isEqualTo(USER_NAME);
    assertThat(actual.getUserKeycloakId()).isEqualTo(USER_KEYCLOAK_ID);
    assertThat(actual.getUserDrfo()).isEqualTo(USER_DRFO);
    assertThat(actual.getTimestamp()).isEqualTo(TIME);
    assertThat(actual.getType()).isEqualTo("USER_ACTION");
    assertThat(actual.getContext()).isEqualTo("{\"id\":\"2\"}");
  }

  @Test
  void expectAuditEventIsConvertedToAvroWithoutSourceAndUserInfo() {
    AuditEvent testEvent = getTestEvent(null, null);

    AvroAuditEvent actual = auditToAvroConverter.convert(testEvent);

    assertThat(actual.getApplicationName()).isEqualTo(APP_NAME);
    assertThat(actual.getName()).isEqualTo(EVENT_NAME);
    assertThat(actual.getRequestId()).isEqualTo(REQUEST_ID);
    assertThat(actual.getSourceSystem()).isNull();
    assertThat(actual.getSourceApplication()).isNull();
    assertThat(actual.getSourceBusinessProcess()).isNull();
    assertThat(actual.getSourceBusinessProcessDefinitionId()).isNull();
    assertThat(actual.getSourceBusinessProcessInstanceId()).isNull();
    assertThat(actual.getSourceBusinessActivity()).isNull();
    assertThat(actual.getSourceBusinessActivityId()).isNull();
    assertThat(actual.getUserName()).isNull();
    assertThat(actual.getUserKeycloakId()).isNull();
    assertThat(actual.getUserDrfo()).isNull();
    assertThat(actual.getTimestamp()).isEqualTo(TIME);
    assertThat(actual.getType()).isEqualTo("USER_ACTION");
    assertThat(actual.getContext()).isEqualTo("{\"id\":\"2\"}");
  }

  private AuditEvent getTestEvent(AuditSourceInfo sourceInfo, AuditUserInfo userInfo) {
    return AuditEvent.AuditEventBuilder.anAuditEvent()
            .application(APP_NAME)
            .name(EVENT_NAME)
            .requestId(REQUEST_ID)
            .sourceInfo(sourceInfo)
            .userInfo(userInfo)
            .currentTime(TIME)
            .eventType(EventType.USER_ACTION)
            .context(Map.of("id", "2"))
            .build();
  }

  private AuditSourceInfo createSourceInfo() {
    return AuditSourceInfo.AuditSourceInfoBuilder.anAuditSourceInfo()
        .system(SOURCE_SYSTEM)
        .application(SOURCE_APPLICATION)
        .businessProcess(BUSINESS_PROCESS)
        .businessProcessDefinitionId(BUSINESS_PROCESS_DEFINITION_ID)
        .businessProcessInstanceId(BUSINESS_PROCESS_INSTANCE_ID)
        .businessActivity(BUSINESS_ACTIVITY)
        .businessActivityInstanceId(BUSINESS_ACTIVITY_INSTANCE_ID)
        .build();
  }

  private AuditUserInfo createUserInfo() {
    return new AuditUserInfo(USER_NAME, USER_KEYCLOAK_ID, USER_DRFO);
  }
}