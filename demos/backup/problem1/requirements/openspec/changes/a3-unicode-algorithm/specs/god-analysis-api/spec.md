## ADDED Requirements

### Requirement: Apply concatenated Unicode decimal transformation
The algorithm MUST transform each name by concatenating decimal code points into a non-negative integer and summing transformed names.

#### Scenario: ASCII name transformation
- **WHEN** the name `Zeus` is transformed
- **THEN** the per-name numeric value is `90101117115`

### Requirement: Preserve non-ASCII correctness
The algorithm MUST compute Unicode code points correctly for non-ASCII characters.

#### Scenario: Name with non-ASCII characters
- **GIVEN** input names include non-ASCII code points
- **WHEN** aggregation is executed
- **THEN** resulting sums are computed from real Unicode code points
- **AND** the final `sum` is returned as a decimal string
