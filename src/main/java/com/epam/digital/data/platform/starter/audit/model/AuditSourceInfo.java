package com.epam.digital.data.platform.starter.audit.model;

public class AuditSourceInfo {

  private String system;
  private String application;
  private String businessProcess;
  private String businessProcessDefinitionId;
  private String businessProcessInstanceId;
  private String businessActivity;
  private String businessActivityInstanceId;

  public String getSystem() {
    return system;
  }

  public void setSystem(String system) {
    this.system = system;
  }

  public String getApplication() {
    return application;
  }

  public void setApplication(String application) {
    this.application = application;
  }

  public String getBusinessProcess() {
    return businessProcess;
  }

  public void setBusinessProcess(String businessProcess) {
    this.businessProcess = businessProcess;
  }

  public String getBusinessProcessDefinitionId() {
    return businessProcessDefinitionId;
  }

  public void setBusinessProcessDefinitionId(String businessProcessDefinitionId) {
    this.businessProcessDefinitionId = businessProcessDefinitionId;
  }

  public String getBusinessProcessInstanceId() {
    return businessProcessInstanceId;
  }

  public void setBusinessProcessInstanceId(String businessProcessInstanceId) {
    this.businessProcessInstanceId = businessProcessInstanceId;
  }

  public String getBusinessActivity() {
    return businessActivity;
  }

  public void setBusinessActivity(String businessActivity) {
    this.businessActivity = businessActivity;
  }

  public String getBusinessActivityInstanceId() {
    return businessActivityInstanceId;
  }

  public void setBusinessActivityInstanceId(String businessActivityInstanceId) {
    this.businessActivityInstanceId = businessActivityInstanceId;
  }


  public static final class AuditSourceInfoBuilder {
    private String system;
    private String application;
    private String businessProcess;
    private String businessProcessDefinitionId;
    private String businessProcessInstanceId;
    private String businessActivity;
    private String businessActivityInstanceId;

    private AuditSourceInfoBuilder() {
    }

    public static AuditSourceInfoBuilder anAuditSourceInfo() {
      return new AuditSourceInfoBuilder();
    }

    public AuditSourceInfoBuilder system(String system) {
      this.system = system;
      return this;
    }

    public AuditSourceInfoBuilder application(String application) {
      this.application = application;
      return this;
    }

    public AuditSourceInfoBuilder businessProcess(String businessProcess) {
      this.businessProcess = businessProcess;
      return this;
    }

    public AuditSourceInfoBuilder businessProcessDefinitionId(String businessProcessDefinitionId) {
      this.businessProcessDefinitionId = businessProcessDefinitionId;
      return this;
    }

    public AuditSourceInfoBuilder businessProcessInstanceId(String businessProcessInstanceId) {
      this.businessProcessInstanceId = businessProcessInstanceId;
      return this;
    }

    public AuditSourceInfoBuilder businessActivity(String businessActivity) {
      this.businessActivity = businessActivity;
      return this;
    }

    public AuditSourceInfoBuilder businessActivityInstanceId(String businessActivityInstanceId) {
      this.businessActivityInstanceId = businessActivityInstanceId;
      return this;
    }

    public AuditSourceInfo build() {
      AuditSourceInfo auditSourceInfo = new AuditSourceInfo();
      auditSourceInfo.setSystem(system);
      auditSourceInfo.setApplication(application);
      auditSourceInfo.setBusinessProcess(businessProcess);
      auditSourceInfo.setBusinessProcessDefinitionId(businessProcessDefinitionId);
      auditSourceInfo.setBusinessProcessInstanceId(businessProcessInstanceId);
      auditSourceInfo.setBusinessActivity(businessActivity);
      auditSourceInfo.setBusinessActivityInstanceId(businessActivityInstanceId);
      return auditSourceInfo;
    }
  }
}
