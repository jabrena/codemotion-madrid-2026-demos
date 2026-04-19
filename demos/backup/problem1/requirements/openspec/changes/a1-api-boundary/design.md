# Design: A1 API boundary

## Scope
Parallel group `A1` from the implementation plan (tasks 1-6).

## Design decisions
- Use Spring MVC controller as the only HTTP boundary.
- Use generated OpenAPI model `GodStatsSumResponse` for the success body.
- Validate `filter` and `sources` at boundary and map invalid input to Problem Details.
- Keep orchestration behind service seam to preserve London-style outside-in layering.

## Verification gate
- `GodStatsControllerAT` happy path contract.
- Controller validation/error tests for `@error-handling` scenarios.
- A1 gate completes only when both acceptance and boundary tests are green.
