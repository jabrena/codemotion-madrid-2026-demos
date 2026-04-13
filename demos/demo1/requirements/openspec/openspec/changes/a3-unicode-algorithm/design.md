# Design: A3 Unicode algorithm

## Decisions
- For each name, concatenate decimal code-point values in sequence.
- Convert concatenated digits to non-negative `BigInteger`.
- Sum per-name values across filtered names.
- Keep dedicated tests to prevent accidental fallback to simple code-point summation.

## Plan mapping
Implements plan tasks 12-15 (parallel slice `A3`).
