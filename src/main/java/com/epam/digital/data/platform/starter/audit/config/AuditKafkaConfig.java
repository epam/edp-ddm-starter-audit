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
public class AuditKafkaConfig {

  private final AuditKafkaProperties auditKafkaProperties;

  public AuditKafkaConfig(AuditKafkaProperties auditKafkaProperties) {
    this.auditKafkaProperties = auditKafkaProperties;
  }

  @Bean
  public Map<String, Object> auditProducerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, auditKafkaProperties.getBootstrap());
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
    props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, auditKafkaProperties.getSchemaRegistryUrl());
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
