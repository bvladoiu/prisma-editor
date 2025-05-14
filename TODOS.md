# TODO

Task1: modify each components saving and retrieving data api (commit/load/and or set if needed) to get from the Config object in common code the site and the language and to construct the save and load commands as save:$site_$languagecode_$TAG and load:$site_$languagecode_$TAG. Clean up the logic on the jvm side that does anything with language and tag and site name. because now it simply has to save the map as a file or read it and send the data over to the js logic. the full logic involving tag and language and site name to construct the file name will be fully in the js side.

Task3: Create a secondary page called Catalog for showcasing all the components. The example of the Home page is not to be folowed!!! This new catalog page must save and retrieve data into components using their commit/set/load apis that save/retrieve data against a site name and a language

Task4: Remove the hardcoded content from page and have it saved/loaded from map files in the jvm code and passed through to the js code using the commit/set/load cli api already implemented.

Task5: Implement UI for displaying current Config (sites and languages) and saving component data against site/language combinations. This should include a dropdown or similar UI element to select the current site and language, and ensure that all component data is saved and loaded with respect to the selected site/language.


# OUTSTANDING:
