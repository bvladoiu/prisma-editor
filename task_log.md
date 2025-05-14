# Tasks

## Create client module with build task - 05/14/2025
Implemented Task2 from TODOS.md by creating a new 'client' module and setting up a task to copy its output to the main project:
1. Created a new 'client' module in the project with proper directory structure
2. Added the module to settings.gradle.kts with `include("client")`
3. Created build.gradle.kts for the client module with JS browser target configuration
4. Implemented a custom "copyJsToMainProject" task that copies the jsBrowserProductionWebpack output to resources/js/client.js
5. Made the build task depend on the copyJsToMainProject task to ensure it runs as part of the build process
6. Created a simple Client.kt file with a main function and a JsExport function for demonstration
7. Updated index.html to include the client.js script

This implementation allows for a separate client module that can be built and included in the main project. The client.js file is automatically copied to the resources/js directory during the build process, making it available for inclusion in the main project's HTML. This modular approach improves code organization and separation of concerns.

## Move FontsLoader from application level to Page component - 08/03/2025
Removed FontsLoader access/usage from the editor/application level and integrated it solely into the Page component:
1. Removed the call to FontsLoader.addFontLinksToHead() from the main() function in Editor.kt
2. Added the import statement for FontsLoader in Page.kt
3. Added FontsLoader initialization to Page.create() method
4. Updated the class documentation to reflect that font loading is now handled by FontsLoader within the Page component

This change improves the application's architecture by ensuring that font loading is handled at the page level rather than the application level. This means that fonts are only loaded when a page is created, which is more efficient and follows the principle of keeping component responsibilities focused. The Page component now fully manages font loading, which is a more appropriate location for this functionality than the application-level code.

## Integrate FontsLoader into Page component - 08/02/2025
Integrated the newly created FontsLoader into the Page component to ensure consistent font loading across the application:
1. Removed the `addGoogleFonts()` method from the Page component
2. Removed the call to `addGoogleFonts()` from the `create()` method
3. Updated the class documentation to reflect that font loading is now handled by FontsLoader at the application level
4. Removed unused imports (HTMLLinkElement)

This change improves the application's architecture by centralizing font loading in the FontsLoader object, which is initialized at the application level in the `main()` function. This avoids duplicate font loading and ensures that all pages use the same optimized font loading approach. The Page component now focuses solely on its core responsibilities without handling font loading.

## Implement FontsLoader for optimized font loading - 08/01/2025
Implemented Task1 from TODOS.md by creating a FontsLoader object to optimize font loading and manage icon names:
1. Created a new FontsLoader object in prisma.editor.css package that tracks icon names and provides methods for generating HTML link tags
2. Implemented methods for Material Symbols, Lexend, and Roboto Flex fonts following best practices for the Google Font service
3. Modified the Icon component to register icon names with the FontsLoader in the init block and when names change
4. Updated the main function in Editor.kt to initialize the FontsLoader before the window loads
5. Ensured alphabetical ordering of icon names for consistent font loading
6. Omitted the optical size axis for Material Symbols as it can cause bugs when not auto-managed by the browser
7. Added support for all variable fonts used in the project (Material Symbols, Lexend, Roboto Flex)

This implementation optimizes font loading by only loading the specific icons that are used in the application, reducing initial load time and bandwidth usage. It also centralizes font loading logic in a single object, making it easier to maintain and update font loading across the application.

## Add active icon variant to Typography - 07/31/2025
Added a new ICON variant to Typography.kt for selected/current semantic state:
1. Created a new "active" variant for the ICON class that represents the selected/current state
2. Implemented the variant with FILL 1, wght 400, GRAD 0 (like regular but with filled style)
3. Added appropriate comments to explain the purpose of this variant

This enhancement completes the icon styling system by adding a semantic variant for selected/current state, which is commonly needed in navigation menus and interactive elements. The implementation follows the project's styling guidelines by using a semantic and expressive selector name and maintaining consistency with the existing variants.

## Refactor Typography CSS and add icon and menu-label styles - 07/30/2025
Refactored Typography.kt to include new icon and menu-label styles as specified in the requirements:
1. Added iconFontFamily property for Material Symbols icons
2. Added ICON constant for icon class name
3. Implemented base icon style with font-family, font-size, font-variation-settings, and transition properties
4. Added deemphasized and emphasized variants for icons with appropriate font-variation-settings
5. Updated menu-label styles to match the requirements with proper font-variation-settings
6. Renamed existing menu-label variants from "slim" and "thick" to "deemphasized" and "emphasized" for consistency
7. Ensured all styles omit the 'opsz' parameter in font-variation-settings to let the browser manage it automatically

This change improves the typography system by providing consistent styling for icons and menu labels with proper emphasis variants. The implementation follows the project's styling guidelines by using CSS variables, avoiding hardcoded values, and maintaining a consistent pattern for class names and variants.

## Modify components to use Config for site and language information - 07/27/2025
Implemented Task1 from TODOS.md by modifying each component's saving and retrieving data API to use the Config object for site and language information. The implementation includes:
1. Updated all components (Hero, KeywordStrip, ArticleCard, BottomDrawer, Drawer, Section, etc.) to import and use the Config object
2. Modified commit() methods to construct save commands with the format "save:${Config.currentSite}_${Config.currentLanguage}_$TAG"
3. Modified load() methods to construct load commands with the format "load:${Config.currentSite}_${Config.currentLanguage}_$TAG"
4. Cleaned up the JVM side logic in JvmMain.kt to simply save/read the map as a file and send the data to the JS logic
5. Moved the full logic involving tag, language, and site name to construct the file name to the JS side

This change improves the architecture by centralizing the site and language information in the Config object and ensuring that all components use the same format for save/load commands. It also simplifies the JVM side logic by removing the responsibility of constructing file names based on site, language, and tag.

## Implement edit mode for components - 07/26/2025
Implemented Task 2 from TODOS.md by adding an edit() method to components that turns them into an "editable" state. The implementation includes:
1. Added edit() method to Text component that makes it contenteditable and adds a delete button
2. Added edit() method to Section component that makes the title editable and adds delete and add buttons
3. Added static methods to Section companion object for adding new content and grid items
4. Added CSS rules for delete and add buttons with proper styling
5. Modified Editor.toggleEditMode() to toggle all components between preview and edit modes
6. Implemented toggleComponentsToEditMode() and toggleComponentsToPreviewMode() functions to handle the DOM manipulation
7. Added edit() method to ArticleCard component that makes title, author, and date editable
8. Added edit() method to Latest component with ability to add new articles
9. Added edit() method to Expertise component that makes name and description editable
10. Added edit() method to SectionContent component with ability to add new content
11. Added edit() method to SectionHeader component with ability to toggle divider
12. Added static methods to Latest, SectionContent companion objects for adding new items
13. Updated Editor.toggleComponentsToEditMode() and toggleComponentsToPreviewMode() to handle all component types

This enhancement allows users to edit content directly in the browser by clicking the FAB button with the edit icon. When in edit mode, text elements become editable, container components show "+" buttons to add new elements, and all components display "-" buttons for deletion. The FAB button changes to a save icon when in edit mode, and clicking it again saves the changes and returns to preview mode.

## Fix compilation errors with Config object accessibility - 07/25/2025
Fixed compilation/build errors related to the Config object in commonMain not being accessible to jsMain and jvmMain code. The implementation includes:
1. Updated build.gradle.kts to explicitly include kotlin("stdlib-common") in both jsMain and jvmMain dependencies
2. Renamed the Config.kt file in jvmMain to ConfigJvm.kt to avoid conflicts with the Config object in commonMain
3. Updated JvmMain.kt to import and use the Config object from commonMain
4. Updated BottomDrawer.kt to use Config directly instead of trying to access properties through Editor

These changes ensure that the Config object defined in commonMain is properly accessible from both jsMain and jvmMain code, allowing for consistent configuration across platforms.

## Replace pagetag language code and site with Config properties - 07/24/2025
Analyzed the codebase to identify where pagetag language code and site properties should be replaced with Config object properties. Found that the Config object in commonMain already has the necessary properties (currentSite, currentLanguage, and currentPageTag) and methods (updateSite, updateLanguage, and updatePageTag) to handle this functionality.

The analysis revealed that:
1. The Editor.kt file has properties for currentSite, currentLanguage, and currentPageTag that should be replaced with Config properties
2. The BottomDrawer.kt component has properties that delegate to the Editor properties
3. The JvmMain.kt file has private variables for currentSite, currentLanguage, and currentPageTag that should be replaced with Config properties

However, there appears to be an issue with accessing the Config object from JS code, as indicated by a TODO comment in Editor.kt: "These properties should be moved to the Config object in commonMain once the multiplatform setup is fixed to allow proper access from JS code".

Due to this limitation, the recommended approach is to:
1. Keep the current properties in Editor.kt until the multiplatform setup is fixed
2. Update JvmMain.kt to use Config properties instead of local variables
3. Ensure that the properties are properly synchronized between Editor.kt and Config

This approach maintains the current functionality while preparing for a future migration to use Config properties directly from JS code once the multiplatform setup is fixed.

## Improve site and language handling with Config object and proper JSON parsing - 07/23/2025
Implemented improvements to the site, language, and page tag handling as requested. The implementation includes:
1. Updated the Config object in commonMain to include currentSite, currentLanguage, and currentPageTag properties
2. Added methods to Config for updating these properties and converting to/from a map
3. Modified JvmMain.kt to parse JSON properly as a map instead of using regex
4. Updated Editor.kt with a TODO comment indicating that its properties should be moved to Config once multiplatform access is fixed
5. Refactored BottomDrawer to use Editor's properties instead of maintaining its own copies
6. Simplified the addBottomDrawer function in Editor.kt to use the new constructor

These changes improve the code by centralizing configuration in the Config object and using proper JSON parsing instead of regex. The site, language, and page properties are now properly managed and accessed from a single source, making the code more maintainable and less prone to errors.

## Update JVM code to save/retrieve items against language and site names - 07/22/2025
Implemented Task1 from TODOS.md by updating the JVM code to save and retrieve items against language and site names. The implementation includes:
1. Modified the `setupCli` function in JvmMain.kt to extract site and language from JSON data using regular expressions
2. Updated the "save" command handler to create filenames with the format "${site}_${language}_$tag.json"
3. Updated the "load" command handler to handle two scenarios:
   - For the editor tag, load with default values "prisma" and "en"
   - For other components, first load the editor JSON to get the current site and language, then load the component data
4. Added fallback to default values ("prisma" for site and "en" for language) if the values are not found in the JSON data
5. Ensured backward compatibility by using default values when needed

This change enhances the editor's functionality by saving and retrieving component data against specific site and language combinations. Components are now saved with filenames that include the site name and language (e.g., "prisma_en_footer.json"), making it easier to manage content for different sites and languages.

## Add page TAG tracking to Editor - 07/21/2025
Added logic to the Editor to track the page TAG in addition to site and language. The implementation includes:
1. Modified the Page base class to include TAG support with a companion object TAG constant and tag property
2. Added standard methods (commit, load, set, refresh) to the Page class following the component pattern
3. Added a TAG to the Home page class with a companion object and overridden tag property
4. Added currentPageTag property to the Editor object to track the current page tag
5. Updated commit/load/set APIs in Editor to persist/retrieve the page TAG
6. Updated the BottomDrawer component to include the pageTag in its form and data handling
7. Updated the toggleEditMode() method to update the currentPageTag when saving

This change enhances the Editor's functionality by allowing it to track which page is being edited, in addition to the site and language. The page TAG follows the same component TAG pattern used throughout the project, ensuring consistency and maintainability. This prepares the application for future pages and ensures that all content can be properly persisted and retrieved based on site, language, and page.

## Remove deprecated size parameter from Icon component - 07/20/2025
Removed the deprecated size parameter from the Icon component as part of the ongoing effort to standardize icon sizing across the application. The implementation includes:
1. Removed the size parameter from the Icon class constructor
2. Removed references to the size parameter in the commit() method
3. Removed references to the size parameter in the set() method

This change completes the migration to using Theme.iconSize for all icon sizing in the application, which was started in the previous task. By removing the deprecated parameter, we ensure that all components use the centralized Theme.iconSize property, maintaining consistency and responsive behavior across different viewport sizes.

## Add iconSize property to Theme and update components - 07/19/2025
Added a new iconSize property to the Theme object that follows the same pattern as the existing spacing property. The iconSize property uses a CSS variable (--icon-size) that clamps from 24px to 42px between viewport widths of 600w and 1440w. Updated all components in the project to use this new property instead of hardcoded icon sizes:
1. Updated Icon.kt to use Theme.iconSize in preview(), render(), and cssRules() methods
2. Marked the size parameter in Icon constructor as deprecated with a message to use Theme.iconSize
3. Updated NavLink.kt to use Theme.iconSize for the icon size
4. Updated EditorScaffold.kt to use Theme.iconSize for the menu icon size

This change improves consistency across the application by centralizing icon sizing in the Theme object, similar to how spacing is handled. Icons will now scale responsively based on viewport width, providing better visual hierarchy and readability across different screen sizes.

## Remove hardcoded drawer logic and move to components - 07/18/2025
Removed all hardcoded logic for opening/closing the bottom and regular drawers in the Editor object and moved it to their respective components. The implementation includes:
1. Added toggle() method to the Drawer component to handle opening/closing the drawer and updating the menu icon
2. Added static toggleDrawer() method to the Drawer companion object that can be called from JavaScript
3. Added navigateTo() method to the Drawer companion object to handle navigation after drawer toggle
4. Updated EditorScaffold to use the Drawer component's API for the menu button click handler
5. Updated NavLink to use the Drawer component's API for navigation
6. Moved the regular Drawer creation from Editor.kt to Page.kt to ensure proper separation of concerns
7. Removed hardcoded JavaScript functions from Editor.kt
8. Updated Editor.kt to use the BottomDrawer component's API directly instead of through JavaScript

This refactoring improves the component architecture by ensuring that each component is responsible for its own behavior. The Editor object now only includes/uses the BottomDrawer component, and the Page component includes/uses the regular/side Drawer. All drawer-related functionality is now implemented with existing APIs rather than hardcoded JavaScript code.

## Extract bottom drawer logic into BottomDrawer component - 07/17/2025
Extracted all drawer logic from the Editor object into a new BottomDrawer component following the project's component pattern. The implementation includes:
1. Created a new BottomDrawer component in the component package with standard methods (preview, commit, load, set, refresh)
2. Moved all drawer-related HTML, CSS, and JavaScript from Editor.kt to the new component
3. Implemented proper attribute-based styling with TAG constants and cssRules() in the companion object
4. Added methods for toggling the drawer and updating values from form inputs
5. Refactored Editor.kt to use the new BottomDrawer component
6. Removed inline styles and replaced them with proper CSS rules
7. Updated the JavaScript code to use the new component's methods

This refactoring improves the component architecture by extracting a reusable UI element from the Editor object, making the code more modular and maintainable. The BottomDrawer component now follows the same pattern as other components in the project, with proper separation of concerns and consistent styling approach.

## Merge site and language properties implementations - 07/16/2025
Merged two implementations of the site and language properties feature (task1-editor-site-language and task1-site-language-properties branches). Selected the more comprehensive implementation from task1-editor-site-language as it provides dynamic language options based on site selection, proper integration with the JVM side through loadProperties and set functions, and a more structured approach with a constant for the editor properties tag. The merged implementation includes all the features from the better implementation:
1. Site and language properties in the Editor object
2. Bottom drawer UI for editing these properties
3. Dynamic language options that update based on the selected site
4. Toggle edit mode functionality with FAB icon changes
5. Save and load properties functions using the commit/load pattern
6. Integration with the JVM side through the receiveData function

This merge resolves the duplication of implementations and provides a single, comprehensive solution for site and language properties in the editor.

## Implement site and language properties in Editor - 07/15/2025
Implemented Task1 from TODOS.md by adding site and language properties to the Editor object. Created a bottom drawer UI that allows users to edit these properties when they press the edit FAB. The implementation includes:
1. Added currentSite and currentLanguage properties to the Editor object
2. Created a bottom drawer with site and language select dropdowns
3. Implemented dynamic language options that update based on the selected site
4. Added toggleEditMode function that changes the FAB icon from edit to save
5. Implemented saveProperties and loadProperties functions using the commit/load pattern
6. Added a set function to handle received data from the JVM side

The drawer is toggled when the user presses the edit FAB, and the icon changes from edit to save. When the user saves, the properties are updated and logged with a "save:editor-properties" prefix. This enhances the editor's functionality by allowing content editors to specify which site and language they are working on, preparing for future tasks that will save component data against site/language combinations.

## Add floating action button with edit icon to Editor - 07/11/2025
Created a new FloatingActionButton component that renders a floating action button with a customizable icon. Implemented the component following the standard pattern with preview(), commit(), load(), set(), and refresh() methods, and a companion object with TAG constant and cssRules(). Added an instance of the FloatingActionButton with an edit icon to the Editor object, making it always visible as specified in Task1. The button is positioned at the bottom right corner of the screen with appropriate styling and hover effects. This enhances the editor's functionality by providing quick access to editing features.

## Remove Text component and implement typography classes - 07/10/2025
Removed the Text component and its usage as per Task1 requirements. Moved typography styling from the Text component to the Typography object, creating CSS classes for display, headline, regular, and action text styles. Updated the SectionHeader component to use the typography classes directly instead of the Text component. Removed the reference to Text.cssRules() from Main.kt. This change simplifies the component architecture by eliminating a dedicated Text component and instead providing reusable typography classes that can be applied to any text element.

## Add FlowContent rendering methods to Text, Icon, and Image components - 07/09/2025
Implemented new render() methods for Text, Icon, and Image components that return FlowContent instead of HTMLElement. This allows these components to be embedded in other components' preview() methods without creating duplicate wrappers. For the Text component, added parameters for display, headline, and regular to override the default text type. Updated SectionHeader to use the new render() method of the Text component. Added addContent() methods to SectionContent and Section components that accept FlowContent parameters, allowing them to work with the new render() methods. These changes improve component composition by eliminating the need to create and append intermediate HTMLElements, making the code cleaner and more efficient.

## Promote component rendering task from OUTSTANDING to TODO - 07/08/2025
Promoted the item "Make sure all components render with Image, Icon and Text components and do not have duplicated wrappers" from the OUTSTANDING section to the TODO section in TODOS.md. Added it as Task1 to maintain the sequential order of tasks. This task is important for ensuring consistent component architecture across the application, as it will help eliminate duplicated wrapper code and standardize the use of basic building blocks (Image, Icon, and Text components) for rendering all other components.

## Create Image component - 07/07/2025
Created a new Image component in src/jsMain/kotlin/prisma/editor/component/Image.kt that renders images with optional alt text and captions. The component follows the established component pattern with standard methods (preview, commit, load, set, refresh) and CSS rules defined in a companion object. Used semantic HTML with figure and figcaption elements for proper accessibility. Updated Main.kt to include the Image component's CSS rules. This component will be used alongside Icon and Text components to ensure all other components render with these basic building blocks and do not have duplicated wrappers. Promoted the task from OUTSTANDING to TODO in TODOS.md as Task9.

## Update Config object to track sites and languages - 07/06/2025
Updated the Config object in src/commonMain/kotlin/prisma/editor/Config.kt to track sites being worked on by content editors and their languages. Removed all previous configuration settings and replaced them with a structure that includes a Site data class with codename and display name properties, a SITES map containing ContaDeal and PRISMA-Software sites, and a SITE_LANGUAGES map tracking language availability for each site (en/ro for ContaDeal, en/de for PRISMA-Software). Added a new task (Task8) to TODOS.md for implementing UI to display the current config and save component data against site/language combinations.

## Add commonMain sourceset with Config object - 05/13/2025
Created a commonMain sourceset directory structure in the project and added a Config object in src/commonMain/kotlin/prisma/editor/Config.kt. The Config object provides centralized configuration settings for the editor, including language preferences, theme options, and font size settings. This configuration is accessible from both JVM and JS code, allowing for consistent settings across platforms. The implementation includes default values, constraints (min/max values), and a defaultSettings map for easy initialization.

## Polish guidelines and consolidate project practices - 07/05/2025
Updated .junie/guidelines.md to be more concise while maintaining quality. Extracted relevant guidelines from TODOS.md, tasks.md, and project structure. Organized into clear sections covering workflow, component architecture, styling, and merge process. Reduced verbosity while preserving essential information and development practices.

## Centralize typography styling in Typography.kt - 07/28/2025
Implemented the task to ensure Typography.kt is the only source of truth for typography in the project:
1. Removed all typography-related CSS (font-size, font-family, etc.) from components' cssRules() methods
2. Applied appropriate Typography classes to HTML text elements based on their semantic meaning
3. Updated components to use Typography classes (HEADLINE, TAGLINE, BODY, CAPTION, SMALL_TEXT, etc.)
4. Verified changes across multiple components to ensure consistency

This change improves the maintainability of the codebase by centralizing all typography styling in one place, making it easier to update and ensuring consistency across the application. Typography classes are now applied directly to HTML elements, providing a clear semantic structure and improving accessibility.

## Implement consistent cssRules() and update Main aggregator - 07/04/2025
Examined all components in the project to ensure they have a consistent cssRules() method in their companion objects. Verified that all components follow the same pattern of returning a List<CssRuleDefinition> with CSS rules for the component. Updated the Main css component to aggregate CSS rules from all components in the project, adding rules for EditorScaffold, NavLink, ArticleCard, Drawer, Expertise, Hero, KeywordStrip, Latest, NavigationMenu, and Pitch components that were previously missing. This ensures that all component styles are properly included in the main stylesheet, improving consistency and maintainability.

## Add missing component files and clean up branches - 07/03/2025
Added unversioned SectionHeader.kt and SectionContent.kt files to git, which were part of the Section component refactoring mentioned in Task6. These components follow the standard pattern with preview(), commit(), load(), set(), and refresh() methods, and have companion objects with TAG constants and cssRules() methods. Verified that Main.kt properly includes CSS rules from these components. Also confirmed that branches task1, task2, and task3 were properly integrated into develop and deleted them. Made a final commit with "Merge fixes" message to ensure the project is in a clean state.

## Integrate branches and update TODOS.md - 07/02/2025
Integrated branches task3 into task2, task2 into task1, and task1 into develop. Found that all branches were already at the same commit, so no actual merging was needed. Reviewed the project to ensure there were no duplicated components or CSS logic and no unnecessary wrapper components. Updated TODOS.md to use Task1, Task2, etc. notation instead of bullet points. Identified that the "Refactor components to match Hero pattern" task was completed but not marked as done in TODOS.md, so added it as Task6 in the DONE section. Confirmed that Section component has been properly refactored with SectionHeader and SectionContent components, each with their own TAG and CSS rules.

## Add Icon and Text components - 05/13/2025
Created new Icon and Text components to standardize typography and iconography across the application. The Icon component renders Material Symbols icons with customizable weight, fill, grade, and size parameters. The Text component provides consistent typography with support for display, headline, regular, and action text types. Removed most styling from the Typography CSS component, keeping only basic constants. Updated Main.kt to include CSS rules from both components. This improves consistency and maintainability by centralizing typography and icon styling in dedicated components.

## Extract Page component - 05/13/2025
Created a new Page component as a base class for all pages in the application. Extracted common functionality from Home to Page, including Google Fonts integration (Poppins, Roboto Flex, and Material Symbols) and main stylesheet initialization. Updated Home to inherit from Page and call super.create() to set up common page elements. Added cssRules() to Page and updated Main.kt to include these rules. This improves code organization by centralizing common page functionality and resources in one place.

## Refactor components to match Hero pattern - 07/01/2025
Refactored all components in the prisma.editor.component package to follow the same pattern as the Hero component. Added TAG constants in companion objects and used them consistently throughout the code. Replaced hardcoded attribute strings with constants. Added standard methods (preview, commit, load, set, refresh) to all components. Added initialization code with load() calls. Set kotlinInstance for dynamic access. Ensured CSS rules are defined in companion objects using the TAG constants. This standardizes the component architecture across the application, making it more consistent and maintainable.

## Fix CSS compilation errors in components - 05/12/2025
Refactored all components in the component package to fix CSS-related compilation errors. Replaced stylesheet() methods with cssRules() methods that return a List<CssRuleDefinition> following the pattern in Hero.kt. Updated all components to use the refactored CSS package APIs (Theme.spacing, Typography.fontMd, etc.) instead of the old Theme.Spacing, Theme.Colors, and Theme.Typography. Used setProperty() for CSS properties not directly available in the CSSStyleDeclaration. Added TAG constants to all components for consistent CSS targeting. This ensures all components use a consistent approach to CSS styling and fixes all CSS-related compilation errors.

## Wrap component stylesheet methods in companion objects - 06/28/2025
Refactored several component classes to move their stylesheet() methods into companion objects. Modified Footer, Hero, NavigationMenu, Pitch, Section, Expertise, and EditorScaffold components to ensure their stylesheet methods are accessible as static methods through the companion object. This change ensures that the Main.stylesheet() method can properly call each component's stylesheet method without requiring an instance of the component, maintaining a consistent pattern across all components.

## Fix NavLink compilation errors - 06/27/2025
Fixed compilation errors in NavLink.kt by refactoring the stylesheet() function to use string templates for CSS rules instead of the object-oriented approach with web.cssom classes. Replaced imports from web.cssom.* with org.w3c.dom.css.* and updated the code to match the pattern used in other components like Theme and ArticleCard. This ensures consistent use of Kotlin JS APIs across the project without introducing string hardcoding.

## Improve NavLink component - 06/26/2025
Refactored the NavLink component to use proper Kotlin APIs instead of hardcoded JavaScript. Replaced the raw JS navigation call with Kotlin's window.setTimeout and direct calls to Editor.openPage. Implemented the toggleDrawer function in Kotlin instead of relying on the JS function. Improved the CSS implementation by restructuring the CSS rules into a map for better organization and maintainability. These changes maintain the same functionality while using more idiomatic Kotlin code and better leveraging the available Kotlin wrappers for browser APIs.

## Remove hardcoded styles - 06/24/2025
Created a new EditorScaffold component to replace hardcoded styles in the Editor.kt file. Moved all inline styles from the editor scaffold, app bar, content area, and main content to the EditorScaffold's stylesheet method. Updated the Editor.kt file to use the new component and its stylesheet. Added the EditorScaffold to the Main stylesheet aggregator to ensure all styles are included in the main stylesheet. This improves caching by centralizing all styles in stylesheets rather than using inline styles.

## Extract Drawer Component - 06/23/2025
Extracted the drawer code from Editor.kt into a separate Drawer component with preview and stylesheet methods. Created a NavLink component for drawer items, following the component pattern used in the project. Updated the drawer toggle to use Material Symbols icons instead of text icons. Added Google Fonts links to index.html for Roboto Flex, Poppins, and Material Symbols fonts. This improves the component architecture by making the drawer more modular and reusable, and enhances the UI with proper Material Design iconography.

## Implement Import/Export and Reimplement Home - 06/22/2025
Reimplemented the Home page using existing components and styles, following the composite pattern. Added import/export methods to the Editor object for persisting data, exposed to the JVM playwright logic. Updated the JVM code to use a settings map and connect to the JS import/export methods, with functions to load settings from a file on startup and save settings to a file on shutdown. The new Home page includes sections for welcome, features, latest updates, and settings, with buttons for importing and exporting settings.

## Refactor Latest component with ArticleCard - 06/21/2025
Deleted the Update class from the Latest component and created a new ArticleCard component. Refactored the Latest component to use ArticleCard instead of Update, implementing the composite pattern where Latest.preview() calls ArticleCard.preview() for each article. This improves the component architecture by separating the article card rendering logic from the list rendering logic, making the components more modular and maintainable.

## Simplify UI components to match Expertise pattern - 06/20/2025
Refactored components in the ui package to more closely match the Expertise component pattern. Updated KeywordStrip, Pitch, and Section components to store data as properties of the class instead of taking them as parameters to the create() method. Renamed create() methods to preview() for consistency with the Expertise component. Created an Update class within the Updates.kt file to replace the model.Update class, following the principle that components themselves are model classes. This simplifies the architecture by reducing the number of classes and making the components more self-contained.

## Merge model properties into UI components - 06/19/2025
Merged the properties of data classes in the prisma.editor.model package into their counterparts in the prisma.editor.ui package, following the pattern established in the Expertise component. Updated NavigationMenu, Hero, and Footer components to include the properties from their respective model classes. For the Home component, implemented the state logic directly in the Home UI component instead of using a separate model class. This simplifies the architecture by reducing the number of classes and making the UI components more self-contained.

## Implement Home as component with composite pattern - 06/18/2025
Refactored Home.kt in the prisma.editor.ui.pages package to follow the component pattern used by other UI components. Implemented Home as a class that extends HTMLElement with a create() method that aggregates other components. Used the composite pattern where the Home component's create() method calls create() on child components (Hero, Section, Footer, etc.). This standardizes the architecture across the application and improves maintainability.

## Refactor UI components to use kotlinx.html - 06/17/2025
Refactored all components in the prisma.editor.component package to match the Expertise component pattern using kotlinx.html APIs. Converted Footer, Hero, KeywordStrip, NavigationMenu, Pitch, Section, Page, and Updates components from FlowContent extension functions to classes extending HTMLElement with create() methods. This standardizes the component architecture across the application, making it more consistent and maintainable.

## Create Main stylesheet aggregator - 05/07/2025
Created a Main.kt file in the styles package that aggregates all styles from the styles package into a single stylesheet. The Main object provides two main functions: initialize() to create a stylesheet and add all styles to it, and exportStylesheet() to export the stylesheet as a CSS string that can be downloaded. Updated HeroStyles.kt to include container and button styles that were previously hardcoded in the Hero component. This prepares the application for a future export button functionality that will allow users to download the aggregated stylesheet.

## Migrate styles to direct DOM API - 05/22/2025
Removed compose styles API from all files in the prisma.editor.css package, following the pattern established in ButtonStyles.kt. Migrated FooterStyles, HeroStyles, KeywordStripStyles, NavigationMenuStyles, PageStyles, ReasonItemStyles, SectionStyles, and UpdatesListStyles to use direct style properties on HTMLElement instead of StyleScope lambda functions. Used dynamic approach for non-standard CSS properties like gap and gridTemplateColumns. This completes the migration of all style files to use the direct DOM API.

## Complete UI component migration - 05/21/2025
Migrated all remaining UI components from Compose to kotlinx.html+kotlin browser wrapper. Converted ExpertiseItem, ReasonItem, Footer, HeroContent, KeywordStrip, NavigationMenu, and UpdatesList components to use kotlinx.html DSL instead of Compose. Updated Home.kt to use the new components and removed redundant private functions. This completes the migration of all UI components to kotlinx.html while maintaining the compose-style naming convention (using Page() and Section() instead of buildPage() and buildSection()).

## Complete migration to kotlinx.html - 05/20/2025
Completed the migration from Compose Web to kotlinx.html by removing all *Kotlinx.kt files and updating the original files to use kotlinx.html and inline CSS. Updated Editor.kt, Home.kt, and Section.kt to use kotlinx.html APIs instead of Compose Web, maintaining the same functionality and appearance. This eliminates the duplication of code and completes the migration to kotlinx.html.

## Remove kotlin.css dependency - 05/19/2025
Removed kotlin.css dependency from build.gradle.kts and updated all files that were using it to use inline CSS strings instead. This includes EditorKotlinx.kt and SectionKotlinx.kt. Also updated EditorKotlinx.kt to use the HomeKotlinx implementation instead of a placeholder. This completes the migration from compose-web to kotlinx.html and ensures that all styling is done using inline CSS strings.

## Replace compose with kotlinx.html - 05/18/2025
Updated build.gradle.kts to remove compose.html.core dependency and uncomment kotlin.wrappers.browser and kotlin.css dependencies. Created new implementation of the UI using kotlinx.html instead of compose-web, maintaining a compose-like style with functions like Page() and Section(). The new implementation includes EditorKotlinx.kt, SectionKotlinx.kt, and HomeKotlinx.kt files that provide the same functionality as the original compose-based implementation.

## Extract typography styles to Typography - 05/17/2025
Created a Typography object in the Theme.kt file to centralize all typography-related styles. Moved font sizes from Spacing and font properties from Styles to the Typography object, and added common text styles (h1, h2, body1, body2, caption). Refactored all 10 style files to use the Typography object, ensuring consistent typography across the application and making it easier to maintain and update text styles.

## Extract colors and spacings to Theme - 05/16/2025
Created a Theme object in its own file with Colors, Spacing, and Styles sub-objects to centralize all design tokens. Extracted all hardcoded colors and spacing values from the 10 style files in the styles package and replaced them with references to the Theme object. This improves maintainability by making it easier to update design values across the application and ensures consistency in the UI.

## Refactor model classes into separate files - 05/15/2025
Created a model package and moved each data model class from HomeData.kt into its own file. Renamed HomeData to Home, removing the "Data" suffix as requested. Updated all references in the composables to use the new model classes. This improves code organization by following the single responsibility principle and makes the model classes more maintainable.

## Refactor HomeComposables into separate files - 05/14/2025
Extracted each composable from HomeComposables.kt into its own file within a new package structure. Moved Home() composable to composables/pages/Home.kt and all other component composables to composables/component/ directory. Updated imports in Editor.kt to reference the new location. This improves code organization and maintainability by following the single responsibility principle.

## Refactor composables to semantic HTML - 05/13/2025
Refactored all composables to use appropriate semantic HTML tags instead of generic Div elements. Replaced Div with Header in AppBar, Nav in NavigationMenu, Section in Section composable, and Footer in Footer composable. Also improved list structures by using Ul and Li elements in NavItems, UpdatesList, and DrawerItem. Added Article elements for content containers and proper Header elements for section titles. These changes improve accessibility and SEO while maintaining the same visual appearance.

## Implement JSON-driven home content - 05/12/2025
Created a data model and loading mechanism for the home page content from JSON files. Modified the Home composable to dynamically load and display content from the resources/data directory, supporting both English and German languages. Updated NavigationMenu and Footer composables to accept data parameters, making the entire page content configurable through JSON.

## Add multilingual JSON data - 05/11/2025
Created a data directory structure in resources with en and de subdirectories for English and German content. Extracted content from home6.html in resources/samples and created structured home.json files for both languages. The JSON structure includes navigation, hero section, content sections, and footer with all text properly localized.

## Fix AppBar isDrawerOpen parameter - 05/10/2025
Fixed a compilation error by implementing the isDrawerOpen parameter in the AppBar composable. Added the parameter to the function signature and updated the menu button to show different icons based on the drawer state (X when open, hamburger menu when closed).

## Implement Home composable from mock - 05/09/2025
Created a Home() composable based on the home6.html mock in resources/samples. Implemented Section and various Item composables with appropriate parameters to make all content configurable. Used Compose Web DSL-style programming with modifiers instead of direct HTML/CSS, following idiomatic Compose patterns.

## Purged project of MUI. Now only using Compose for Web/Html 05/05/2025

## Create todo list for HTML/CSS editor - 05/05/2025
Created a todo.md file with immediate tasks and icebox (future) tasks for implementing an in-browser static HTML/CSS editor for landing, press, blog, company, and contact pages. The todo list prioritizes DSL-style programming and outlines a clear path forward for the project.

## Setup browser module as single project - 05/05/2025
Renamed all packages from "spock.lair" to "prisma.editor", updated all references, and set up the project structure. Added necessary configuration files, cleaned up build artifacts, and created a 'develop' branch for ongoing development work.

## Fix JVM browser navigation - 05/05/2025
Updated the JVM run task to open the index.html page from the JS production build instead of a blank page. Modified JvmMain.kt to navigate to the file URL of the index.html instead of just injecting the script.

## Update JVM to use dev server - 05/05/2025
Modified JvmMain.kt to navigate to the development server URL (http://localhost:8080/) instead of the static production build file. This allows for real-time development and hot-reloading when using the jsBrowserDevelopmentRun task.

## Add editor with side-drawer - 05/06/2025
Added Material UI wrapper dependency, created editor.html with a draggable side-drawer layout, implemented EditorMain.kt with DSL-style components, and updated JvmMain.kt to open the editor page instead of index. The side-drawer provides navigation tools while maintaining a clean editing area.

## Consolidate editor functionality - 05/06/2025
Removed EditorMain.kt and integrated its functionality into Main.kt. The main class now detects whether it's running in the editor context and renders the appropriate UI. This simplifies the codebase by having a single entry point for both the default page and the editor page.

## Update side drawer navigation - 05/06/2025
Replaced the content of the side drawer with navigation entries for Home, Press, Dev-Blog, Company, and Contact pages. Changed the title from "Editor Tools" to "Navigation" to better reflect its purpose. Each navigation entry has a placeholder onClick handler that shows an alert for navigation.

## Improve drawer toggle button - 05/06/2025
Changed the drawer toggle button from text ("Hide Drawer"/"Show Drawer") to use directional arrow icons (◀/▶) for a cleaner, more intuitive interface. Styled the button to be more compact and visually appealing while maintaining the same functionality.

## Add material header/top bar - 05/06/2025
Added a material-styled header/top bar to the editor interface that contains the drawer toggle button. Moved the toggle button from the drawer to the top bar, making it accessible even when the drawer is closed. Adjusted the layout to accommodate the new top bar, including proper spacing and positioning of all elements.

## Update drawer toggle to menu icon - 05/07/2025
Changed the drawer toggle button to use a menu/hamburger icon (☰) instead of directional arrows. Added Material Icons wrapper dependency to the project for future icon usage. The icon remains consistent regardless of drawer state, following modern UI conventions for menu toggles.

## Simplify topbar and improve toggle icon - 05/07/2025
Removed all content from the topbar except the menu button, creating a cleaner and more focused interface. Enhanced the toggle button to dynamically change between a hamburger icon (☰) when drawer is closed and a close icon (✕) when drawer is open, providing better visual feedback to users about the current state.

## Migrate UI to Material Design - 05/07/2025
Migrated all UI components from regular HTML/CSS to Material UI styled elements. Updated TopBarComponent to use AppBar styling, SideDrawerComponent to use Drawer styling, MainContentComponent to use Container styling, and ButtonComponent to use Button styling. Applied Material UI typography, colors, spacing, and elevation throughout the application for a consistent, modern look and feel.

## Clean up header - 05/07/2025
Removed all content from the header except the drawer toggle button, creating a minimalist and focused top bar. Eliminated the title text and any other elements to achieve a cleaner interface that emphasizes content and navigation rather than header elements.

## Replace TopBar with MUI AppBar - 05/08/2025
Replaced the custom TopBarComponent with a new MuiAppBarComponent that uses Material UI AppBar styling. The new component provides a cleaner implementation that only contains the drawer menu button as required. Updated all references to TopBarComponent in the EditorComponent class and added a DSL-style function for creating MUI AppBar components.

## Implement Home page from home6.html - 06/25/2025
Implemented a new Home page based on the content from resources/samples/home6.html using existing components. Enhanced the NavLink component to support a selected state for navigation items. Updated the Drawer implementation to mark the Home entry as selected. Created a clean implementation with proper sections for Hero/Intro, Core Expertise, Keyword Strip, Why Work With Us, and Latest Updates. Used attribute-based styling and Theme tokens for consistent styling without hardcoding values.

## Refactor Expertise component with attribute-based styling - 06/15/2025
Refactored the Expertise component in the prisma.editor.component package to use attribute-based styling. Updated the component to add an 'expertise' attribute to the root element for CSS targeting. Enhanced ExpertiseStyles.kt to use CSSOM APIs with selectors targeting the 'expertise' attribute, while maintaining backward compatibility with direct style manipulation methods. Added a method to create and add a stylesheet to the document, improving the component's styling architecture.

## Extend HTMLElement with HtmlExpertiseElement - 06/16/2025
Created a new HtmlExpertiseElement class that extends HTMLElement directly, following the example of HtmlDivElement. Implemented the necessary methods and properties for the class to work correctly. Updated the Expertise function to use the new HtmlExpertiseElement class while maintaining the 'expertise' attribute for CSS targeting. This improves the component architecture by using proper HTML element extension instead of wrapping elements.

## Implement Compose Web scaffold - 05/05/2025
Implemented Editor.kt as a scaffold-like composition with AppBar/Toolbar and Drawer using compose web/html. Added a menu button to the AppBar and a Home entry to the Drawer in an idiomatic way. Used DSL-style programming with Compose for Web components instead of direct HTML/CSS.

## Add semantic color variable layer and remove hardcoded colors - 07/29/2025
Implemented a new layer of variable naming for all colors in the Theme.kt file and removed all hardcoded colors from the project. The implementation includes:
1. Created semantic variable names for the actual color values (brandBlue, brandPurple, pureWhite, etc.)
2. Updated the original variable names (primary, secondary, white, etc.) to use CSS variable references
3. Updated setProperty calls in Theme.kt to use the new semantic variable names
4. Added new variables for hardcoded colors found in the codebase (brightPurple, vibrantPurple, blackShadowLight, etc.)
5. Replaced all hardcoded colors in component files with references to Theme variables:
   - Drawer.kt: Replaced "#7D3DF3" with Theme.drawerPurple and rgba(0,0,0,0.2) with Theme.shadowLight
   - EditorScaffold.kt: Replaced "#6200EE" with Theme.scaffoldPurple and rgba(0,0,0,0.2) with Theme.shadowLight
   - BottomDrawer.kt: Replaced rgba(0,0,0,0.2) with Theme.shadowLight
   - FloatingActionButton.kt: Replaced rgba(0,0,0,0.3) with Theme.shadowMedium
   - NavigationMenu.kt: Replaced rgba(0,0,0,0.8) with Theme.shadowHeavy

This change improves the maintainability of the codebase by centralizing all color definitions in the Theme.kt file and ensuring that no hardcoded colors are used anywhere in the project. The new layer of variable naming makes it easier to understand the purpose of each color and provides a clear separation between the actual color values and their semantic usage in the application.
