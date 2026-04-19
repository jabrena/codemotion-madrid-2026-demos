## ADDED Requirements

### Requirement: Orchestrate multi-source retrieval with partial tolerance
The service MUST fan out to the requested pantheon sources and continue aggregation when one or more sources fail or time out.

#### Scenario: One source fails and others succeed
- **GIVEN** multiple sources are requested
- **AND** one outbound source fails
- **WHEN** service orchestration completes
- **THEN** names from failed sources are omitted
- **AND** aggregation continues with successful sources

### Requirement: Enforce source token validation
The service MUST reject invalid source identifiers before outbound execution.

#### Scenario: Invalid source token
- **WHEN** a request includes an unsupported source token
- **THEN** the service raises a bad-request domain error
- **AND** the API boundary maps it to `400`
