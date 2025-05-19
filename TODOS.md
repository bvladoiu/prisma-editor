# TODO

Task1: Implement a user interface in the JS frontend to display and manage the current configuration (sites and languages). This UI should allow users to select the active site and language and trigger saving and loading of page data using the existing mechanisms in `EditorJvm`.

Task2: Remove the hardcoded page content in `PagesInitialData.kt`. Modify the application flow to load page data dynamically from the JVM based on the selected site, language, and page tag. The loaded data should then be used to populate the components on the page.

Task3: Implement the export functionality. This should include:
    - Generating static HTML files for each page based on the component data.
    - Consolidating the CSS rules from all components into a single main CSS file.
    - Ensuring the client-side JavaScript bundle is correctly generated and linked in the exported HTML files.

Task4: Enhance the saving and loading mechanism in `EditorJvm` to handle different pages and their respective component data effectively. This might involve organizing the saved data by site, language, and page tag.

Task5: Consider adding a UI or command-line interface in the JVM application for managing sites and languages persistently (e.g., adding, removing, or modifying site/language configurations).

# DONE

- Created a Catalog page for component showcase.
- Implemented FontsLoader for optimized font loading.
- Created client module with build task.
- Integrated ScriptLoader for script loading.
- Updated CSS selectors to use parent-child attribute pattern.
- Added semantic color variable layer and removed hardcoded colors.
- Implemented a basic saving and loading mechanism for component data in `EditorJvm` communicating with `EditorJs` via console messages.

# OUTSTANDING: