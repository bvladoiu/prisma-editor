# TODO:

Task0: (ongoing/checklist) - first check #OUTSTANDING section and promote/write-up anything there to a task and make it the first task to pick up in TODO section (update task numbers)

Task1: Create a merge process guideline/checklist in TODOS.md that outlines the steps for merging feature branches into develop. Include steps for creating a branch, implementing changes, reviewing code, testing, squashing commits, merging, and cleaning up.

Task2: Add a secondary method to each component in the spirit of preview() called edit() that renders the component in an "editable" state. Making use of the content editable attribute. If the component you are working on is a list then make sure + button is added to add an element to the list. Make sure individual components display a - button to allow deletion. Add a fab button at the bottom right of the page with an edit icon that makes all elements toggle from preview() to edit().

Task3: Remove the hardcoded content from page and have it saved/loaded from map files in the jvm code and passed through to the js code using the commit/set/load cli api already implemented.

# MERGE PROCESS GUIDELINE

This checklist outlines the standard process for implementing tasks and merging feature branches into the develop branch.

## Starting a Task
1. [ ] Check the OUTSTANDING section in TODOS.md and promote any items to tasks if needed
2. [ ] Select a task from the TODO section
3. [ ] Create a new branch from develop with a descriptive name (e.g., `task1-consistent-css-rules`)
   ```
   git checkout develop
   git checkout -b task-name-description
   ```

## Implementing the Task
1. [ ] Understand the requirements of the task
2. [ ] Make the necessary code changes
3. [ ] Add any new files to git
   ```
   git add <new-files>
   ```
4. [ ] Update tasks.md with an entry for what you've done
5. [ ] Commit your changes with descriptive commit messages
   ```
   git commit -m "Descriptive message about the changes"
   ```

## Code Review and Testing
1. [ ] Review your own code for quality and correctness
2. [ ] Build the project to check for compilation errors
   ```
   build
   ```
3. [ ] Fix any issues found during review or testing
4. [ ] Commit fixes with a "Review fixes" message
   ```
   git commit -m "Review fixes: Description of the fixes"
   ```

## Merging into Develop
1. [ ] Switch to the develop branch
   ```
   git checkout develop
   ```
2. [ ] Merge your feature branch with squash option to combine all commits
   ```
   git merge --squash task-name-description
   git commit -m "Implement Task: Description of the task"
   ```
3. [ ] Delete the feature branch after successful merge
   ```
   git branch -d task-name-description
   ```

## Updating TODOS.md
1. [ ] Move the completed task from TODO to DONE section in TODOS.md
2. [ ] Commit the updated TODOS.md
   ```
   git commit -m "Update TODOS.md: Move Task to DONE section"
   ```

Following this process ensures consistent code quality and maintains a clean git history.



# OUTSTANDING:

 - add commonMain sourceset to the project and add in it a Config object. in sources not in resources!
 - create an Image component. make sure all other components render with Image, Icon and Text components and do not have duplicated wrappers.


# DONE:

Task1: Comb through components and make sure they have a consistent cssRules() method and then go to the Main css component in the css package and make sure it accesses/aggregates the rules of all components.

Task4: Extract a Page() component from Home() to serve as the common denominator for functionality for all pages. i.e. links to google fonts links to css/js required by all pages will be put in Page not in Home.

Task5: Add to the project an Icon component that takes parameter material-symbol icon name and renders it in a span. And a Text component that takes as parameter also a typography class such as display, headline, regular and action. Remove everything from the Typography css component and implement support for icon and text with their respective complexities. Headline and display are to be Poppins and the rest are to be roboto flex as variable font. Icons are also to be variable font. All must be get from google fonts service in Page().

Task6: Comb through components and make sure that semantically they are one single component with one TAG, one rendering logic, one set of css rules. Section component has been refactored to use SectionHeader and SectionContent components for semantic clarity, each with their own TAG and CSS rules. All components now follow the Hero pattern with standard methods (preview, commit, load, set, refresh) and CSS rules defined in companion objects.
