# Proposal: A2 service and outbound

## Why
Implement resilient multi-source orchestration so the API can return partial sums when a source fails or times out.

## What Changes
- Service tests for parsing, filtering, and partial-result semantics.
- Parallel fan-out implementation in `GodAnalysisService`.
- Outbound adapters/tests with configured timeout behavior.

## Impact
Makes resilience behavior explicit and testable before final algorithm slice.
