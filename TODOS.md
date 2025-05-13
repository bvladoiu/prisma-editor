# TODO:

Task0: (ongoing/checklist) - first check #OUTSTANDING section and promote/write-up anything there to a task and make it the first task to pick up in TODO section (update task numbers)

Task2: Add a secondary method to each component in the spirit of preview() called edit() that renders the component in an "editable" state. Making use of the content editable attribute. If the component you are working on is a list then make sure + button is added to add an element to the list. Make sure individual components display a - button to allow deletion. Add a fab button at the bottom right of the page with an edit icon that makes all elements toggle from preview() to edit().

Task3: Remove the hardcoded content from page and have it saved/loaded from map files in the jvm code and passed through to the js code using the commit/set/load cli api already implemented.


# OUTSTANDING:

 - add commonMain sourceset to the project and add in it a Config object. in sources not in resources!
 - create an Image component. make sure all other components render with Image, Icon and Text components and do not have duplicated wrappers.


# REOPEN OR DELETE:

Task1: Comb through components and make sure they have a consistent cssRules() method and then go to the Main css component in the css package and make sure it accesses/aggregates the rules of all components.

Task4: Extract a Page() component from Home() to serve as the common denominator for functionality for all pages. i.e. links to google fonts links to css/js required by all pages will be put in Page not in Home.

Task5: Add to the project an Icon component that takes parameter material-symbol icon name and renders it in a span. And a Text component that takes as parameter also a typography class such as display, headline, regular and action. Remove everything from the Typography css component and implement support for icon and text with their respective complexities. Headline and display are to be Poppins and the rest are to be roboto flex as variable font. Icons are also to be variable font. All must be get from google fonts service in Page().

Task6: Comb through components and make sure that semantically they are one single component with one TAG, one rendering logic, one set of css rules. Section component has been refactored to use SectionHeader and SectionContent components for semantic clarity, each with their own TAG and CSS rules. All components now follow the Hero pattern with standard methods (preview, commit, load, set, refresh) and CSS rules defined in companion objects.
