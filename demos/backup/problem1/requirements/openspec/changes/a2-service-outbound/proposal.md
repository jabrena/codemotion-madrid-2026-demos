# Proposal: A2 service and outbound orchestration

## Why
The second parallel slice (`A2`) introduces resilient orchestration across pantheon sources and provides the outbound integration seam required by the contract. This enables partial aggregation behavior without blocking on failing sources.

## What Changes
- Add service-level tests for source parsing, first-character filtering, and deterministic aggregation.
- Implement `GodAnalysisService` with parallel source fan-out using virtual threads.
- Add outbound collaborator tests with WireMock fixtures and timeout/failure cases.
- Implement outbound adapters (`GodOutboundProperties`, `HttpClientConfig`, `GodDataClient`) with single-attempt behavior.

## Impact
- Introduces production orchestration path while preserving API boundary behavior from A1.
- Encodes resilience policy: failed sources contribute nothing, successful sources still return `200`.
