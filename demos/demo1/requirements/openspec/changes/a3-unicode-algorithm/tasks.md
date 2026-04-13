# Tasks: A3 Unicode aggregation completion

- [ ] Add `UnicodeAggregatorTest` case asserting `Zeus -> 90101117115`.
- [ ] Add non-ASCII algorithm test coverage for code-point correctness.
- [ ] Add regression test guarding against stale "sum code points" implementation.
- [ ] Implement `UnicodeAggregator` with decimal code-point concatenation into `BigInteger`.
- [ ] Clarify and test null/empty input handling policy.
- [ ] Wire service flow to use real aggregator values end-to-end.
- [ ] Verify A3 gate with algorithm tests and acceptance path (including timeout `*IT`).
