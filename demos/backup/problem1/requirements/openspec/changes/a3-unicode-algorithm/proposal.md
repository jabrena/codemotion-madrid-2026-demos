# Proposal: A3 Unicode aggregation completion

## Why
The third parallel slice (`A3`) finalizes the core numeric rule so acceptance sums are derived from the real Unicode transformation instead of placeholders.

## What Changes
- Add focused algorithm tests for concatenation semantics and non-ASCII behavior.
- Implement `UnicodeAggregator` to concatenate decimal code points into a `BigInteger`.
- Integrate the real aggregator into service flow end-to-end.
- Verify timeout scenario and full acceptance path while preserving contract outputs.

## Impact
- Locks the most error-prone domain rule with clear regression values (including `Zeus -> 90101117115`).
- Makes acceptance-level totals traceable to deterministic algorithm behavior.
