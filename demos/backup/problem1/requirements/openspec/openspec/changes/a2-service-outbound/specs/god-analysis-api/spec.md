## ADDED Requirements

### Requirement: Keep partial aggregation when a source fails
The service MUST continue aggregation with successful sources when one source fails or times out.

#### Scenario: One failing source
- **GIVEN** three sources are requested
- **AND** one source fails
- **WHEN** aggregation completes
- **THEN** only successful source names are aggregated

### Requirement: Reject invalid source tokens
The system MUST reject unsupported source values as bad requests.

#### Scenario: Unsupported source token
- **WHEN** request contains an unknown source token
- **THEN** a bad-request error is produced
- **AND** API returns `400`
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
