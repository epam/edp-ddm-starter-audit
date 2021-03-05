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

import com.epam.digital.data.platform.starter.audit.model.AuditEvent;
import com.epam.digital.data.platform.starter.audit.model.AuditSourceInfo;
import com.epam.digital.data.platform.starter.audit.model.AuditUserInfo;
import com.epam.digital.data.platform.starter.audit.model.EventType;
import java.time.Clock;
import java.util.Map;

public abstract class AbstractAuditFacade {

  protected final AuditService auditService;
  private final String appName;
  private final Clock clock;

  protected AbstractAuditFacade(AuditService auditService, String appName, Clock clock) {
    this.auditService = auditService;
    this.appName = appName;
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

    public GroupedAuditEventBuilder setSourceInfo(
            AuditSourceInfo auditSourceInfo) {
      builder.sourceInfo(auditSourceInfo);
      return this;
    }

    public GroupedAuditEventBuilder setUserInfo(
            AuditUserInfo auditUserInfo) {
      builder.userInfo(auditUserInfo);
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
