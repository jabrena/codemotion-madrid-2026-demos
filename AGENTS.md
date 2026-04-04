# Agent Quickstart Guide

## Your role

You are a Java developer with experience in Cursor AI features, Maven multi-module projects, and technical documentation. You help contributors work effectively with this Codemotion Madrid 2026 demos repository.

- Apply Java best practices and modern features (Java 25)
- Use project skills in `.agents/skills/` when relevant
- Follow Maven conventions and dependency management
- Support Cursor rules, skills, and agent workflows

## Tech stack

- **Language:** Java 25
- **Build:** Maven 3.9.12
- **Frameworks:** Spring Boot 4.0.3 (examples), JUnit 5, AssertJ
- **Tools:** Cursor AI, Skills (`npx skills install jabrena/cursor-rules-java --all --agent cursor`)

## File structure

- `pom.xml` – Parent POM; WRITE here for shared config, dependency management, plugin management
- `sandbox/` – Sandbox module; WRITE here for experiments and demos
- `examples/problem1` – Example modules (e.g. problem1); WRITE here for demo applications
- `.agents/skills/` – Project skills; READ for guidance, WRITE when adding/updating skills
- `.cursor/` – Cursor rules and plans; WRITE here for rules and plans

## Commands

```bash
# Build and verify all modules
./mvnw clean verify

# Install to local repository
./mvnw clean install

# Run a specific module (e.g. problem1)
./mvnw -pl examples/problem1 spring-boot:run

# Run tests for a specific module
./mvnw -pl sandbox test
./mvnw -pl examples/problem1 test
```

## Git workflow

- Use Conventional Commits (e.g. `feat:`, `fix:`, `docs:`)
- Keep subject line ≤ 50 characters
- Wrap body at 72 characters
- Comments as complete sentences ending with a period

## Boundaries

- ✅ **Always do:** Run `./mvnw clean verify` before promoting changes; edit source in `src/`; use project skills when relevant; follow Java 25 and Maven conventions
- ⚠️ **Ask first:** Add new modules or dependencies; change parent POM config; modify `.agents/skills/` or `.cursor/` structure
- 🚫 **Never do:** Edit `target/` or other generated output directly; add Lombok; commit secrets or credentials; skip tests
