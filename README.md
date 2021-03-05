# ddm-starter-audit

This starter is to help with the preparation and sending of audit events.

### Usage
###### Configuration
* `audit.kafka` - connection properties (`AuditKafkaProperties` class)

###### Extension
* `AbstractAuditFacade` to be sub-classed as an entrypoint-class (aspect, RestController, etc.)
* `AuditService` to be called from a client Service, it might be the Facade from above

### License
ddm-starter-audit is Open Source software released under the Apache 2.0 license.
