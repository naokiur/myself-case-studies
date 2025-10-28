# Project Guidelines

These guidelines help Junie work effectively in this repository.

## Project Overview
This repository contains small, self-contained case studies. The main one at the moment is "fictional-shop-checkout", which demonstrates a simple checkout domain with Java and Kotlin examples. Each example is an independent Gradle project with its own wrapper scripts and tests.

## Repository Structure (high level)
- LICENSE, README.md — top-level docs.
- .junie/guidelines.md — these guidelines.
- fictional-shop-checkout/
  - README.md — case study overview and diagram.
  - example-java/ — Java implementation (Gradle project with tests).
  - example-kotlin/ — Kotlin implementation (Gradle project template; minimal code).

Java example (example-java) key paths:
- src/main/java/org/example/... — production code (application, controller, domain, repository).
- src/test/java/org/example/... — unit tests.

Kotlin example (example-kotlin/example-kotlin) key paths:
- src/main/kotlin/... — minimal app entry point.

## How Junie should verify changes
- Prefer running tests for the affected subproject only (fast feedback).
- Build is not required if tests already compile and pass; tests will compile needed sources automatically.

## How to run tests
Run from the corresponding example directory (use the project-local Gradle wrapper):

- Java example (recommended when changing Java files):
  - Directory: fictional-shop-checkout/example-java
  - All tests: ./gradlew test
  - One test class: ./gradlew test --tests org.example.domain.value.MoneyTest

- Kotlin example (if you modify Kotlin files under example-kotlin/example-kotlin):
  - Directory: fictional-shop-checkout/example-kotlin/example-kotlin
  - All tests: ./gradlew test

Note: In the Junie environment, use the test tool when possible instead of raw shell:
- run_test with the full path to the test file/directory, e.g.:
  - run_test fictional-shop-checkout/example-java/src/test/java/org/example/domain/value/MoneyTest.java
  - run_test fictional-shop-checkout/example-java/src/test/java

## Build instructions
- For Java example: from fictional-shop-checkout/example-java run ./gradlew build
- For Kotlin example: from fictional-shop-checkout/example-kotlin/example-kotlin run ./gradlew build

## Coding and style
- Follow existing code patterns and package layout.
- Keep changes minimal and localized to satisfy the issue.
- Prefer small, focused commits (Junie will apply minimal edits to files).
- When renaming code elements, always use the provided rename tool to update references safely.

## Tooling notes for Junie
- Use specialized tools first: search_project, get_file_structure, open, run_test, build.
- Avoid running ls for the root (structure is provided). Use search_project for locating symbols or files.
- When editing, prefer search_replace with exact matches; do not partially match lines.
- Communicate progress using update_status; finalize with submit when done.

## Definition of done for simple documentation issues
- The requested file exists with clear, actionable content.
- References and commands match the actual repository layout and work with Gradle wrappers or the test tool.
