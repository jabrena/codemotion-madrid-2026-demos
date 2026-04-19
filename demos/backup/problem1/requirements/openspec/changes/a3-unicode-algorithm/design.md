# Design: A3 Unicode aggregation completion

## Scope
Parallel group `A3` from the implementation plan (tasks 12-15).

## Design decisions
- Transform each name by iterating Unicode code points, concatenating decimal values as digits.
- Convert per-name concatenated digits to non-negative `BigInteger`, then sum across filtered names.
- Maintain explicit tests to prevent regression to stale "sum code points" behavior.
- Use real aggregator in service end-to-end acceptance path.

## Verification gate
- `UnicodeAggregatorTest` with ASCII and non-ASCII cases.
- Acceptance path uses real aggregator values (not placeholders).
- Include timeout integration scenario (`*IT`) and keep contract-compatible output.
