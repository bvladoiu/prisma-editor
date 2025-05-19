# Prisma Editor - Developer Guide

A Kotlin Multiplatform project for editing static content with a component-based architecture.

## Project Structure

- `src/commonMain`: Shared code between JS and JVM platforms
- `src/jsMain`: JavaScript/Web frontend
- `src/jvmMain`: JVM/Desktop application
- `client`: Module for building the client-side JavaScript bundle.

## Development

### Prerequisites

- JDK 17 or higher
- Gradle

### Building

```bash
./gradlew build
```

You can also use the custom task to copy the JS output to both jsMain and jvmMain resources:

```bash
./gradlew copyJsToMainProject
```

### Running

#### Web Application

For development with hot-reload:
```bash
./gradlew jsBrowserDevelopmentRun
```

For production build:
```bash
./gradlew jsBrowserProductionRun
```

#### Desktop Application

```bash
./gradlew jvmRun
```

You can also use the custom task to copy resources and run the editor:

```bash
./gradlew runEditor
```

## Workflow

- Scan project before starting tasks to understand structure/patterns
- Check TODO in TODOS.md for pending tasks
- Create descriptive feature branches from develop (e.g., `task1-css-rules`)
- Add new files to git. Make sure there are no untracked/unaccounted for files.
- Update task_log.md with brief entry for completed work
- Maintain production-ready state after each task

## Kotlin/JS Development

- Use existing components instead of creating new HTML/CSS
- Modify existing files rather than creating duplicates
- Component pattern:
  - Classes with data properties
  - Standard methods: preview(), commit(), load(), set(), refresh()
  - Companion objects with TAG constants and cssRules()
  - Track with kotlinInstance for dynamic access

## Component Architecture

- One component = one semantic entity with one TAG
- Use attribute-based styling with TAG constants
- Centralize CSS in companion objects' cssRules()
- Follow Hero pattern with standard methods
- Use semantic HTML elements
- Extract reusable components to avoid duplication

## Styling

The editor is designed to allow the creation and management of specific pages like Home, Press, Dev-Blog, Company, and Contact solely through serialized data. This means these page types should utilize the generic `Page` component and its data properties, without requiring dedicated code components for each specific page type.

## Styling

- Use Theme for colors, spacing, typography
- Define CSS with attribute selectors in companion objects
- Avoid inline styles; use centralized stylesheets
- Return List<CssRuleDefinition> for all CSS rules
- Ensure Main.kt aggregates all component CSS

## Code Organization

- Follow single responsibility principle
- Use semantic HTML for accessibility/SEO
- Organize into logical packages
- Maintain consistent naming conventions

## Merge Process

This checklist outlines the standard process for implementing tasks and merging feature branches into the develop branch.

### Starting a Task
1. Check the TODO section in TODOS.md and select a task.
2. Create a new branch from develop with a descriptive name (e.g., `task1-ui-config`).

### Implementing the Task
1. Understand the requirements of the task
### Implementing the Task
1. Understand the requirements of the task
2. Make the necessary code changes
3. Add any new files to git
4. Update task_log.md with an entry for what you've done
5. Commit your changes with descriptive commit messages

### Code Review and Testing
1. Review your own code for quality and correctness
2. Build the project to check for compilation errors
3. Fix any issues found during review or testing
4. Commit fixes with a "Review fixes" message

### Merging into Develop
1. Switch to the develop branch
2. Merge your feature branch with squash option to combine all commits
3. Delete the feature branch after successful merge

### Updating TODOS.md
1. Update the TODO section in TODOS.md to reflect the completed task.
2. Add a detailed entry for the completed task in task_log.md.
3. Commit the updated TODOS.md and task_log.md.


## Project Features

### Component Catalog

The project includes a Catalog page that showcases all available components. This page is the default page when the editor opens and allows you to:
- View all components in one place
- Test component functionality
- Save and load component data using their commit/set/load APIs
- Save and load component data using their commit/set/load APIs (via console interaction with the JVM)

### Configuration System

The project uses a Config object in the commonMain sourceset to track:
- Sites being worked on by content editors (e.g., PRISMA-Software, ContaDeal)
- Languages available for each site (e.g., en/de for PRISMA-Software, en/ro for ContaDeal)
- Current site, language, and page tag

This configuration is accessible from both JVM and JS code, allowing for consistent settings across platforms.
This configuration is accessible from both JVM and JS code, allowing for consistent settings across platforms. A basic saving and loading mechanism for component data based on this configuration exists in the JVM, communicating with the JS frontend via console messages.

### Current Project Status

#### Completed Tasks
- Created a Catalog page for component showcase
- Implemented FontsLoader for optimized font loading
- Created client module with build task
- Integrated ScriptLoader for script loading
- Updated CSS selectors to use parent-child attribute pattern
- Added semantic color variable layer and removed hardcoded colors

#### Pending Tasks
- Implement a user interface in the JS frontend to display and manage the current configuration (sites and languages), allowing users to select the active site and language. This UI should also include functionality and UI elements for adding new language options.
- Implement the UI and underlying functionality for creating new pages. Users should be able to specify a page name and potentially a template or initial structure.
- Implement the UI and underlying functionality for editing existing pages. This should go beyond just editing component data and include managing the page structure and settings.
- Refine the saving and loading mechanism in `EditorJvm` to handle different pages and their respective component data effectively. This should involve organizing the saved data by site, language, and page tag.
- Implement the export functionality, including UI for triggering HTML/CSS export, generating static HTML and CSS, and linking the JS bundle.
- Ensure that the Home, Press, Dev-Blog, Company, and Contact pages can be created and managed solely through serialized data using the generic `Page` component.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## For Content Editors

If you're a content editor looking for documentation on how to use the Prisma Editor, please refer to the [USER_MANUAL.md](USER_MANUAL.md) file.
