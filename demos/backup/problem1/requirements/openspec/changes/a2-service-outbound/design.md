# Design: A2 service and outbound orchestration

## Scope
Parallel group `A2` from the implementation plan (tasks 7-11).

## Design decisions
- Parse and validate requested sources before outbound work; invalid tokens raise `BadRequestException`.
- Fetch source datasets concurrently via `CompletableFuture` and `Executors.newVirtualThreadPerTaskExecutor()`.
- Join successful source results, omit failed/timeout sources, then apply filter and aggregation.
- Keep outbound adapter behavior idempotent with one attempt per configured source URL and configured connect/read timeouts.

## Verification gate
- `GodAnalysisServiceTest` for parse/filter/partial-result behavior.
- Outbound tests with WireMock fixtures and timeout failure scenarios.
- A2 gate completes only when service and outbound test suites are green.
