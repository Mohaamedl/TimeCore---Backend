# Development Workflow

This repository follows a streamlined development workflow with two main branches: `main` and `stage`. The goal is to ensure stability in production while allowing flexibility in staging for testing and team integration.

## Branch Overview

- **`main`**: The production-ready branch containing the stable version of the application.
- **`stage`**: A pre-production branch used for testing new features and bug fixes before they are merged into `main`.

## Workflow

1. **Development in Local Feature Branches**:
    - Developers create a new feature branch from `stage` for every new feature or bug fix.
      ```bash
      git checkout stage
      git pull origin stage
      git checkout -b feature/your-feature-name
      ```
    - Code, test, and commit changes in the local branch.
      ```bash
      git add .
      git commit -m "Description of the changes"
      ```

2. **Push and Create Pull Request**:
    - Push the feature branch to the remote repository.
      ```bash
      git push origin feature/your-feature-name
      ```
    - Open a Pull Request (PR) on GitHub targeting the `stage` branch.
        - Provide a clear description of the changes.
        - Link any related issues or tickets.

3. **Code Review**:
    - The team reviews the PR for code quality, functionality, and adherence to project standards.
    - Address feedback by making additional commits to the same branch.

4. **Merge to `stage`**:
    - Once approved, merge the feature branch into `stage`.
        - Preferably use the "Squash and Merge" option to keep the history clean.
    - Delete the feature branch after merging.

5. **Testing in `stage`**:
    - The `stage` branch is deployed to the staging environment.
    - Run tests and validate functionality.
    - Fix any issues by repeating steps 1–4.

6. **Deploy to `main`**:
    - Once all features are verified on `stage`, create a PR from `stage` to `main`.
    - Review and merge changes into `main`.

## IntelliJ IDEA Guidelines

- Clone the repository using IntelliJ's Git integration:
    1. Open IntelliJ IDEA.
    2. Go to **File > New > Project from Version Control**.
    3. Paste the repository URL and select the desired folder.

- Use IntelliJ's Git tool window for branch management, committing, and pushing changes.
- Run tests directly from IntelliJ to verify changes before committing.

## Notes

- Ensure you always pull the latest changes from `stage` before starting a new feature.
- Follow the project's coding [Coding Standards](coding-standards.md)  and naming conventions.
- Communicate with the team regarding any major changes or blockers.

## Example Commands

- **Switch to `stage`**:
  ```bash
  git checkout stage
  git pull origin stage
  ```

- **Create a new feature branch**:
  ```bash
  git checkout -b feature/your-feature-name
  ```

- **Push changes**:
  ```bash
  git push origin feature/your-feature-name
  ```

- **Create a PR**: Open a Pull Request on GitHub targeting the `stage` branch.

