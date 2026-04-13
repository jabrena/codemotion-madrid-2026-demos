# Design: A1 API boundary

## Decisions
- Spring MVC controller is the HTTP boundary.
- Success response uses generated `GodStatsSumResponse`.
- Input errors map to Problem Details (`application/problem+json`).
- Controller delegates orchestration to service seam only.

## Plan mapping
Implements plan tasks 1-6 (parallel slice `A1`).
