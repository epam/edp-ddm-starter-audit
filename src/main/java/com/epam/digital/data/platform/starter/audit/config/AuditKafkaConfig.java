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

package com.epam.digital.data.platform.starter.audit.config;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class AuditKafkaConfig {

  private static final String CERTIFICATES_TYPE = "PEM";
  private static final String SECURITY_PROTOCOL = "SSL";

  private final AuditKafkaProperties auditKafkaProperties;

  public AuditKafkaConfig(AuditKafkaProperties auditKafkaProperties) {
    this.auditKafkaProperties = auditKafkaProperties;
  }

  @Bean
  public <O> KafkaTemplate<String, O> auditReplyTemplate() {
    return new KafkaTemplate<>(auditRequestProducerFactory());
  }

  private <I> ProducerFactory<String, I> auditRequestProducerFactory() {
    return new DefaultKafkaProducerFactory<>(auditProducerConfigs());
  }

  private Map<String, Object> auditProducerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, auditKafkaProperties.getBootstrap());
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
    props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, auditKafkaProperties.getSchemaRegistryUrl());
    if (auditKafkaProperties.getSsl().isEnabled()) {
      props.putAll(createSslProperties());
    }
    return props;
  }

  private Map<String, Object> createSslProperties() {
    return Map.of(
            CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SECURITY_PROTOCOL,
            SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, CERTIFICATES_TYPE,
            SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, CERTIFICATES_TYPE,
            SslConfigs.SSL_TRUSTSTORE_CERTIFICATES_CONFIG, auditKafkaProperties.getSsl().getTruststoreCertificate(),
            SslConfigs.SSL_KEYSTORE_CERTIFICATE_CHAIN_CONFIG, auditKafkaProperties.getSsl().getKeystoreCertificate(),
            SslConfigs.SSL_KEYSTORE_KEY_CONFIG, auditKafkaProperties.getSsl().getKeystoreKey()
    );
  }
}
