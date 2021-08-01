package com.epam.digital.data.platform.starter.audit.model;

import java.util.HashMap;
import java.util.Map;

public class AuditEvent {
  private String requestId;
  private String application;
  private String sourceSystem;
  private String sourceBusinessId;
  private String sourceBusinessProcess;
  private String name;
  private EventType eventType;
  private long currentTime;
  private String userId;
  private String userName;
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

  public String getSourceSystem() {
    return sourceSystem;
  }

  public void setSourceSystem(String sourceSystem) {
    this.sourceSystem = sourceSystem;
  }

  public String getSourceBusinessId() {
    return sourceBusinessId;
  }

  public void setSourceBusinessId(String sourceBusinessId) {
    this.sourceBusinessId = sourceBusinessId;
  }

  public String getSourceBusinessProcess() {
    return sourceBusinessProcess;
  }

  public void setSourceBusinessProcess(String sourceBusinessProcess) {
    this.sourceBusinessProcess = sourceBusinessProcess;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
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
    private String sourceSystem;
    private String sourceBusinessId;
    private String sourceBusinessProcess;
    private String name;
    private EventType eventType;
    private long currentTime;
    private String userId;
    private String userName;
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

    public AuditEventBuilder sourceSystem(String sourceSystem) {
      this.sourceSystem = sourceSystem;
      return this;
    }

    public AuditEventBuilder sourceBusinessId(String sourceBusinessId) {
      this.sourceBusinessId = sourceBusinessId;
      return this;
    }

    public AuditEventBuilder sourceBusinessProcess(String sourceBusinessProcess) {
      this.sourceBusinessProcess = sourceBusinessProcess;
      return this;
    }

    public AuditEventBuilder name(String name) {
      this.name = name;
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

    public AuditEventBuilder userId(String userId) {
      this.userId = userId;
      return this;
    }

    public AuditEventBuilder userName(String userName) {
      this.userName = userName;
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
      auditEvent.setSourceSystem(sourceSystem);
      auditEvent.setSourceBusinessId(sourceBusinessId);
      auditEvent.setSourceBusinessProcess(sourceBusinessProcess);
      auditEvent.setName(name);
      auditEvent.setEventType(eventType);
      auditEvent.setCurrentTime(currentTime);
      auditEvent.setUserId(userId);
      auditEvent.setUserName(userName);
      auditEvent.setContext(context);
      return auditEvent;
    }
  }
}
