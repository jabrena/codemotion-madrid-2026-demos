# Design: A2 service and outbound

## Decisions
- Validate source tokens before outbound execution.
- Use `CompletableFuture` with virtual-thread executor for source fan-out.
- Omit failed sources from aggregation (no retries).
- Keep timeout policy in outbound configuration (`connect` + `read`).

## Plan mapping
Implements plan tasks 7-11 (parallel slice `A2`).
