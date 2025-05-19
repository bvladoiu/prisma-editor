# TODO

Task1: Implement a user interface in the JS frontend to display and manage the current configuration (sites and languages). This UI should allow users to select the active site and language and trigger saving and loading of page data using the existing mechanisms in `EditorJvm`.

Task2: Implement the UI and underlying functionality for creating new pages. Users should be able to specify a page name and potentially a template or initial structure.

Task3: Implement the UI and underlying functionality for editing existing pages. This should go beyond just editing component data and include managing the page structure and settings.

Task4: Refine the saving and loading mechanism in `EditorJvm` to handle different pages and their respective component data effectively. This should involve organizing the saved data by site, language, and page tag.

Task5: Implement the export functionality. This should include:
    - Implementing the UI for triggering HTML and CSS export.
    - Generating static HTML files for each page based on the component data.
    - Consolidating the CSS rules from all components used on a page into a single main CSS file.
    - Ensuring the client-side JavaScript bundle is correctly generated and linked in the exported HTML files.

Task6: Ensure that the Home, Press, Dev-Blog, Company, and Contact pages can be created and managed solely through serialized data using the generic `Page` component. This means these page types should not require dedicated code components.

# DONE

- Created a Catalog page for component showcase.
- Implemented FontsLoader for optimized font loading.
- Created client module with build task.
- Integrated ScriptLoader for script loading.
- Updated CSS selectors to use parent-child attribute pattern.
- Added semantic color variable layer and removed hardcoded colors.
- Implemented a basic saving and loading mechanism for component data in `EditorJvm` communicating with `EditorJs` via console messages.

# OUTSTANDING: