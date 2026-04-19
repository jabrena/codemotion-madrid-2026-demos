# Tasks: A1 API boundary

- [ ] Run OpenAPI generation for `demos/demo1/implementation/pom.xml` and confirm generated API/model packages.
- [ ] Create `GodStatsControllerAT` with one happy-path scenario using Spring Boot random port + `RestClient`.
- [ ] Implement minimal controller endpoint returning generated `GodStatsSumResponse` via delegation.
- [ ] Add `BadRequestException` and `GlobalExceptionHandler` stubs needed for compilation.
- [ ] Add controller-slice tests for missing/invalid `filter` and `sources`.
- [ ] Complete boundary behavior with `application/problem+json` for `400`.
- [ ] Verify A1 gate by running acceptance + controller tests.
