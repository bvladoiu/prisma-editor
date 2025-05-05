# Tasks

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

## Implement Compose Web scaffold - 05/05/2025
Implemented Editor.kt as a scaffold-like composition with AppBar/Toolbar and Drawer using compose web/html. Added a menu button to the AppBar and a Home entry to the Drawer in an idiomatic way. Used DSL-style programming with Compose for Web components instead of direct HTML/CSS.
