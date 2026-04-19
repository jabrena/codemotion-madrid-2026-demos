# Steps

## Analysis, Design & Planning

- Create a Plan using a Github Issue:

```bash
Given a github issue: https://github.com/jabrena/codemotion-madrid-2026-demos/issues/8
And the Open API: @demos/demo1/requirements/US-001-god-analysis-api.openapi.yaml
And the ADRs:  @demos/demo1/requirements/adr
Then Develop an implementation plan for the following development: @demos/demo1/implementation
```

- Create a Plan in local

```bash
Develop a plan to implement the User Story
taking in account the
@demos/demo1/requirements/adr and @demos/demo1/requirements/agile
to implement in @demos/demo1/implementation
```

- Refine a Plan

```bash
#Step 1
Write the plan in @demos/demo1/requirements/plans

#Step 2
@demos/demo1/requirements/plans/US-001-god-analysis-api-implementation-plan.md improve the plan @.agents/skills/041-planning-plan-mode
```

- Convert plan into OpenSpec changes

## Development

```bash
Implement the first milestone @demos/demo1/requirements/plans/US-001-god-analysis-api-implementation-plan.md

implement the second milestone @demos/demo1/requirements/plans/US-001-god-analysis-api-implementation-plan.md

Implement the third milestone  @demos/demo1/requirements/plans/US-001-god-analysis-api-implementation-plan.md

implement forth milestone @demos/demo1/requirements/plans/US-001-god-analysis-api-implementation-plan.md
```

## Verify


```bash
curl -sS 'http://localhost:8080/api/v1/gods/stats/sum?filter=n&sources=greek,roman,nordic' | jq .
```
