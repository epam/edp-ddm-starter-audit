package com.epam.digital.data.platform.starter.audit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("audit.kafka")
public class AuditProperties {

  private String bootstrap;
  private String topic;
  private String schemaRegistryUrl;

  public String getBootstrap() {
    return bootstrap;
  }

  public void setBootstrap(String bootstrap) {
    this.bootstrap = bootstrap;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public String getSchemaRegistryUrl() {
    return schemaRegistryUrl;
  }

  public void setSchemaRegistryUrl(String schemaRegistryUrl) {
    this.schemaRegistryUrl = schemaRegistryUrl;
  }
}
