# Proposal: A3 Unicode algorithm

## Why
Finalize the domain rule that drives sum values so contract-level totals reflect real Unicode transformation logic.

## What Changes
- Add algorithm tests for ASCII and non-ASCII names.
- Implement concatenated-decimal code-point transformation into `BigInteger`.
- Wire real aggregator into service acceptance flow.

## Impact
Prevents regression to stale code-point-sum logic and locks expected numeric outputs.
