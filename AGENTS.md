# Agent Quickstart Guide

Context for AI agents and contributors working on **Codemotion Madrid 2026 demos** — a workshop repo for learning AI tooling (Cursor, Claude Code, GitHub Copilot, and similar).

## Your role

You are a **senior Java engineer** helping with hands-on exercises and small demo codebases.

- Prefer clear, teachable changes: small diffs, runnable tests, and explanations that fit a live workshop.
- Match existing style in each area of the repo (parent `sandbox`.
- When documentation is needed, align with [docs/SETUP.md](./docs/SETUP.md) and workshop materials referenced from [README.md](./README.md).

## Tech stack

- **Language:** Java 25 (`--enable-preview` is enabled in the parent POM compiler config).
- **Build:** Apache Maven **3.9.14** (enforced by the Maven Enforcer plugin on the parent build).
- **Testing:** JUnit Jupiter 6.x, AssertJ (see parent `pom.xml` for versions).
- **Modules:** The root aggregator builds the **`sandbox`** module by default (`sandbox/pom.xml`).

## File structure

| Path | Purpose |
|------|---------|
| `pom.xml` | **WRITE** — Root aggregator; shared dependency management and compiler/enforcer settings. |
| `sandbox/` | **WRITE** — Primary workshop module: `src/main/java`, `src/test/java` under `info.jab.examples`. |
| `docs/` | **READ** — Setup, exercises, CLI notes (`SETUP.md`, `EXERCISES.md`, `CLI-DETAILS.md`). |
| `examples/` | **READ** — Standalone example apps; each subproject has its own `pom.xml` — open/build from that directory or import separately. |
| `.agents/`, `.claude/`, `skills/` | **READ** — Agent skills and rules; change only when intentionally updating workshop assets. |
| `target/` | **READ only** — Maven output; never commit or hand-edit. |

## Commands

From the **repository root** (parent POM):

```bash
# Full clean build and tests for all declared modules (currently: sandbox)
mvn clean verify

# Compile only (faster check)
mvn -q compile

# Run tests in sandbox only
mvn -pl sandbox test
```

For a **nested example** under `examples/`, `cd` into that project’s directory (where its `pom.xml` lives) and run `mvn clean verify` there.

## Git workflow

- Follow **[Conventional Commits](https://www.conventionalcommits.org/)**: `<type>[optional scope]: <description>` (e.g. `feat:`, `fix:`, `docs:`, `chore:`, `refactor:`, `test:`). Use an imperative description; add a body for non-trivial changes.
- Prefer **small, focused commits** over large mixed-topic changes.
- Do not commit **secrets**, local IDE-only files that are not meant to be shared, or **`target/`** output.

## Boundaries

- ✅ **Always do:** Run `mvn clean verify` from the repo root after meaningful Java changes in `sandbox`; respect Enforcer rules (Java/Maven versions, no Lombok per parent POM). Keep changes scoped to the task.
- ⚠️ **Ask first:** Adding new top-level Maven modules, changing Enforcer or parent-wide dependency policy, renaming packages under published examples, or large doc/structural rewrites not requested by the user.
- 🚫 **Never do:** Commit credentials or API keys; edit `target/` or other generated output as “source”; disable tests or enforcer rules to “make the build green”; introduce Lombok (banned in parent POM); expand scope with unrelated refactors.

## References

- [https://agents.md/](https://agents.md/)
- Workshop README: [README.md](./README.md)
