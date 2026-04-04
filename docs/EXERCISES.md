# Exercises

## Prompting Engineering Workflow

### Basics

#### Review CLI Products

**Goal: Understand all options from both products and compare**

- [CLI Details](./CLI-DETAILS.md)

#### Cursor AI Desktop

- AGENTS.md

**Goal:** Use Skill @200-agents-md to create AGENTS.md and explain why
**References:**

- https://agents.md/
- https://github.com/jabrena/cursor-rules-java/tree/main/skills/200-agents-md

- Autocomplete: Tab model

**Goal:** Follow TDD to implement Cache interface
**Notes:**

```bash
@sandbox/src/main/java/info/jab/examples/Cache.java Given this interface, develop a test following TDD and maintain the RED phase
@sandbox/src/test/java/info/jab/examples/CacheTest.java evolve to GREEN phase in TDD

```

**References:**

- https://en.wikipedia.org/wiki/Test-driven_development

- Modes (Ask, Agent & Plan)

**Goal:** Learn Ask mode
**Notes:**

```bash
Scenario 1: Euler Problem 1 how to implement

Ask about implement with java in a functional way.
https://projecteuler.net/problem=1
```

**References:**

- https://projecteuler.net/problem=1

---

**Goal:** Learn Agent mode
**Notes:**

```bash
Scenario 1: Euler Problem 1 how to implement

Implement with java in a functional way in @sandbox/main/java/info/jab/examples
https://projecteuler.net/problem=1
```

```bash
Scenario 2: FizzBuzz

Implement with java in a functional way in @sandbox/main/java/info/jab/examples
```

```gherkin
Feature: FizzBuzz

  Background:
    Given the goal is to implement a method that maps a positive integer i to a string by applying these rules in order (first match wins):
      """
      “FizzBuzz” if i is divisible by 3 and 5,
      else “Fizz” if i is divisible by 3,
      else “Buzz” if i is divisible by 5,
      else the decimal string of i.
      """

  Scenario: fizzbuzz results for i from 1 through the upper bound
    Given the upper bound is 5
    When we evaluate fizzbuzz for each i from 1 through the upper bound
    Then fizzbuzz(input) is:
      | input | expectation |
      | 1     | 1           |
      | 2     | 2           |
      | 3     | Fizz        |
      | 4     | 4           |
      | 5     | Buzz        |
```

**Goal:** Learn Plan mode
**Notes:**

```bash
Create a plan for the requirements located in @requirements-examples/problem1/requirements
to be implemented in @examples/problem1
```

**References:**

- https://cursor.com/blog/plan-mode
- https://code.claude.com/docs/en/common-workflows
- https://code.visualstudio.com/docs/copilot/agents/planning


- Web Browser NA

### Agentic Engineering Workflow

- Skills `npx skills install jabrena/cursor-rules-java --all --agent cursor`
- Agents
- Spec Driven

### DevOps pipelines features

- Enhancing pipelines with AI Tooling

