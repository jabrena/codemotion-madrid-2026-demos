## ADDED Requirements

### Requirement: Validate query at API boundary
The API MUST validate `filter` and `sources` query parameters at the controller boundary and reject invalid requests as client errors.

#### Scenario: Missing required filter
- **WHEN** a client calls `GET /api/v1/gods/stats/sum` without `filter`
- **THEN** the API responds with `400`
- **AND** the content type is `application/problem+json`

### Requirement: Return contract-compliant success response
The API MUST return HTTP 200 with `GodStatsSumResponse` and `sum` encoded as a decimal string.

#### Scenario: Valid request returns decimal sum string
- **GIVEN** a valid request with supported sources and one-character filter
- **WHEN** aggregation completes
- **THEN** the API responds with `200`
- **AND** the body includes `sum` matching `^[0-9]+$`
