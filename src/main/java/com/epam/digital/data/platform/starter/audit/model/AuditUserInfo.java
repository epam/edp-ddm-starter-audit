package com.epam.digital.data.platform.starter.audit.model;

public class AuditUserInfo {

  private String userName;
  private String userKeycloakId;
  private String userDrfo;

  public AuditUserInfo() {
  }

  public AuditUserInfo(String userName, String userKeycloakId, String userDrfo) {
    this.userName = userName;
    this.userKeycloakId = userKeycloakId;
    this.userDrfo = userDrfo;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserKeycloakId() {
    return userKeycloakId;
  }

  public void setUserKeycloakId(String userKeycloakId) {
    this.userKeycloakId = userKeycloakId;
  }

  public String getUserDrfo() {
    return userDrfo;
  }

  public void setUserDrfo(String userDrfo) {
    this.userDrfo = userDrfo;
  }

  public static final class AuditUserInfoBuilder {
    private String userName;
    private String userKeycloakId;
    private String userDrfo;

    private AuditUserInfoBuilder() {
    }

    public static AuditUserInfoBuilder anAuditUserInfo() {
      return new AuditUserInfoBuilder();
    }

    public AuditUserInfoBuilder userName(String userName) {
      this.userName = userName;
      return this;
    }

    public AuditUserInfoBuilder userKeycloakId(String userKeycloakId) {
      this.userKeycloakId = userKeycloakId;
      return this;
    }

    public AuditUserInfoBuilder userDrfo(String userDrfo) {
      this.userDrfo = userDrfo;
      return this;
    }

    public AuditUserInfo build() {
      return new AuditUserInfo(userName, userKeycloakId, userDrfo);
    }
  }
}
