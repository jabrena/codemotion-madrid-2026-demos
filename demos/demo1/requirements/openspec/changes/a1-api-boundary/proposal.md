# Proposal: A1 API boundary

## Why
The first parallel slice (`A1`) establishes contract-correct API behavior for `GET /api/v1/gods/stats/sum` before internal orchestration is implemented. This keeps the outside-in TDD flow and prevents drift from OpenAPI and Gherkin error handling rules.

## What Changes
- Add acceptance-first contract coverage with `GodStatsControllerAT`.
- Implement minimal controller boundary that delegates and returns generated `GodStatsSumResponse`.
- Add request validation and problem-details mapping for invalid input (`application/problem+json`).
- Keep controller free of orchestration/business logic.

## Impact
- API boundary behavior becomes stable for subsequent A2/A3 slices.
- Reduces risk of rework by locking HTTP semantics early (status codes, payload shape, content type).
