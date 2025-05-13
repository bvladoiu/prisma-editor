# Prisma Editor Guidelines

## Workflow
- Scan project before starting tasks to understand structure/patterns
- Check OUTSTANDING in TODOS.md; promote items to tasks when needed
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
1. Check the OUTSTANDING section in TODOS.md and promote any items to tasks if needed
2. Select a task from the TODO section
3. Create a new branch from develop with a descriptive name (e.g., `task1-consistent-css-rules`)
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
1. Remove the completed task from TODO and make sure it's addressed in task_log.md
2. Commit the updated TODOS.md and task_log.md

Following this process ensures consistent code quality and maintains a clean git history.
