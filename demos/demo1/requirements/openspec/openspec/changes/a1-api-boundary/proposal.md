# Proposal: A1 API boundary

## Why
Establish the API contract first in outside-in TDD so downstream service and algorithm work can evolve without breaking HTTP semantics.

## What Changes
- Acceptance-first API test for `GET /api/v1/gods/stats/sum`.
- Minimal controller delegation returning generated `GodStatsSumResponse`.
- Boundary validation + `application/problem+json` error mapping.

## Impact
Locks HTTP behavior early and prevents divergence from OpenAPI and Gherkin.
