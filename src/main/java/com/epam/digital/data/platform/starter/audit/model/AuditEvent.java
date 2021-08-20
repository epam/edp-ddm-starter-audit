package com.epam.digital.data.platform.starter.audit.model;

import java.util.HashMap;
import java.util.Map;

public class AuditEvent {
  private String requestId;
  private String application;
  private String name;
  private AuditSourceInfo sourceInfo;
  private EventType eventType;
  private long currentTime;
  private AuditUserInfo userInfo;
  private Map<String, Object> context = new HashMap<>();

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public String getApplication() {
    return application;
  }

  public void setApplication(String application) {
    this.application = application;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public AuditSourceInfo getSourceInfo() {
    return sourceInfo;
  }

  public void setSourceInfo(AuditSourceInfo sourceInfo) {
    this.sourceInfo = sourceInfo;
  }

  public EventType getEventType() {
    return eventType;
  }

  public void setEventType(EventType eventType) {
    this.eventType = eventType;
  }

  public long getCurrentTime() {
    return currentTime;
  }

  public void setCurrentTime(long currentTime) {
    this.currentTime = currentTime;
  }

  public AuditUserInfo getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(AuditUserInfo userInfo) {
    this.userInfo = userInfo;
  }

  public Map<String, Object> getContext() {
    return context;
  }

  public void setContext(Map<String, Object> context) {
    this.context = context;
  }

  public static final class AuditEventBuilder {
    private String requestId;
    private String application;
    private String name;
    private AuditSourceInfo sourceInfo;
    private EventType eventType;
    private long currentTime;
    private AuditUserInfo userInfo;
    private Map<String, Object> context = new HashMap<>();

    private AuditEventBuilder() {
    }

    public static AuditEventBuilder anAuditEvent() {
      return new AuditEventBuilder();
    }

    public AuditEventBuilder requestId(String requestId) {
      this.requestId = requestId;
      return this;
    }

    public AuditEventBuilder application(String application) {
      this.application = application;
      return this;
    }

    public AuditEventBuilder name(String name) {
      this.name = name;
      return this;
    }

    public AuditEventBuilder sourceInfo(AuditSourceInfo sourceInfo) {
      this.sourceInfo = sourceInfo;
      return this;
    }

    public AuditEventBuilder eventType(EventType eventType) {
      this.eventType = eventType;
      return this;
    }

    public AuditEventBuilder currentTime(long currentTime) {
      this.currentTime = currentTime;
      return this;
    }

    public AuditEventBuilder userInfo(AuditUserInfo userInfo) {
      this.userInfo = userInfo;
      return this;
    }

    public AuditEventBuilder context(Map<String, Object> context) {
      this.context = context;
      return this;
    }

    public AuditEvent build() {
      var auditEvent = new AuditEvent();
      auditEvent.setRequestId(requestId);
      auditEvent.setApplication(application);
      auditEvent.setName(name);
      auditEvent.setSourceInfo(sourceInfo);
      auditEvent.setEventType(eventType);
      auditEvent.setCurrentTime(currentTime);
      auditEvent.setUserInfo(userInfo);
      auditEvent.setContext(context);
      return auditEvent;
    }
  }
}
