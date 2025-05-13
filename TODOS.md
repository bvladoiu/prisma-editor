# TODO
Task2: Add a secondary method to each component in the spirit of preview() called edit() that renders the component in an "editable" state. Making use of the content editable attribute. If the component you are working on is a list then make sure + button is added to add an element to the list. Make sure individual components display a - button to allow deletion. Add a fab button at the bottom right of the page with an edit icon that makes all elements toggle from preview() to edit().

Task3: Remove the hardcoded content from page and have it saved/loaded from map files in the jvm code and passed through to the js code using the commit/set/load cli api already implemented.

Task8: Implement UI for displaying current Config (sites and languages) and saving component data against site/language combinations. This should include a dropdown or similar UI element to select the current site and language, and ensure that all component data is saved and loaded with respect to the selected site/language.


# OUTSTANDING:


# DONE:
Task1: Add site and language properties to the Editor object. open them in a bottom drawer and allow user to edit them from there. When user presses the edit fab. Change the icon on the fab to save and save them. Look at Config object in common code to see sites and languages and look at commit/set/load apis in regular components and in the jvm code to see how things are saved/loaded.
