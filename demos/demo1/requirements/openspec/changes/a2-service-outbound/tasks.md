# Tasks: A2 service and outbound orchestration

- [ ] Add `GodAnalysisServiceTest` for source parsing and invalid token handling.
- [ ] Add tests for case-insensitive first-code-point filtering.
- [ ] Add tests for partial aggregation when one source fails.
- [ ] Implement `GodAnalysisService` orchestration using `CompletableFuture` + virtual-thread executor.
- [ ] Add outbound collaborator tests with WireMock (`greek`, `roman`, `nordic` fixtures).
- [ ] Implement `GodOutboundProperties`, `HttpClientConfig`, and `GodDataClient`.
- [ ] Ensure outbound failures/timeouts return empty contribution per source.
- [ ] Verify A2 gate by running service and outbound test suites.
