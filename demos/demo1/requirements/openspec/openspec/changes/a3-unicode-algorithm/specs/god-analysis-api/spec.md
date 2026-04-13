## ADDED Requirements

### Requirement: Use concatenated Unicode decimal rule
Each name MUST be transformed by concatenating decimal code points in order, then converting to a non-negative integer.

#### Scenario: Zeus transformation
- **WHEN** the input name is `Zeus`
- **THEN** the transformed numeric value is `90101117115`

### Requirement: Support non-ASCII correctly
The algorithm MUST compute Unicode code points correctly for non-ASCII characters.

#### Scenario: Non-ASCII input
- **GIVEN** names include non-ASCII characters
- **WHEN** aggregation executes
- **THEN** the sum is derived from real Unicode code points
- **AND** returned as decimal string
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
