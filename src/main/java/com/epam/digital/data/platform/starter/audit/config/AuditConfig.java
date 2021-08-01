package com.epam.digital.data.platform.starter.audit.config;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class AuditConfig {

  private final AuditProperties auditProperties;

  public AuditConfig(AuditProperties auditProperties) {
    this.auditProperties = auditProperties;
  }

  @Bean
  public Map<String, Object> auditProducerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, auditProperties.getBootstrap());
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
    props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, auditProperties.getSchemaRegistryUrl());
    return props;
  }

  @Bean
  public <I> ProducerFactory<String, I> auditRequestProducerFactory() {
    return new DefaultKafkaProducerFactory<>(auditProducerConfigs());
  }

  @Bean
  public <O> KafkaTemplate<String, O> auditReplyTemplate(
      @Qualifier("auditRequestProducerFactory") ProducerFactory<String, O> auditRequestProducerFactory) {
    return new KafkaTemplate<>(auditRequestProducerFactory);
  }
}
