# Tasks

## Update Config object to track sites and languages - 07/06/2025
Updated the Config object in src/commonMain/kotlin/prisma/editor/Config.kt to track sites being worked on by content editors and their languages. Removed all previous configuration settings and replaced them with a structure that includes a Site data class with codename and display name properties, a SITES map containing ContaDeal and PRISMA-Software sites, and a SITE_LANGUAGES map tracking language availability for each site (en/ro for ContaDeal, en/de for PRISMA-Software). Added a new task (Task8) to TODOS.md for implementing UI to display the current config and save component data against site/language combinations.

## Add commonMain sourceset with Config object - 05/13/2025
Created a commonMain sourceset directory structure in the project and added a Config object in src/commonMain/kotlin/prisma/editor/Config.kt. The Config object provides centralized configuration settings for the editor, including language preferences, theme options, and font size settings. This configuration is accessible from both JVM and JS code, allowing for consistent settings across platforms. The implementation includes default values, constraints (min/max values), and a defaultSettings map for easy initialization.

## Polish guidelines and consolidate project practices - 07/05/2025
Updated .junie/guidelines.md to be more concise while maintaining quality. Extracted relevant guidelines from TODOS.md, tasks.md, and project structure. Organized into clear sections covering workflow, component architecture, styling, and merge process. Reduced verbosity while preserving essential information and development practices.

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
