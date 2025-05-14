# TODO

Task2: Make prisma.js from index not be linked in index.html but have it installed as intrinsic script in the jvm code of the project. Remove all google font links from index.html and ensure they are working from the Page component in js logic of the project that should now be installed as intrinsic script. Remove client.js link from index.html and create a ScriptLoader object then integrate it in Page. in the spirit of FontsLoader.

Task3: Create a secondary page called Catalog for showcasing all our components so far. The example of the Home page is not to be followed!!! This new catalog page must save and retrieve data into components using their commit/set/load apis that save/retrieve data against a site name and a language. Add another site name in the Config object in common code called 'common'. Make the catalog page be the default page the editor opens on.  

Task4: Remove the hardcoded content from page and have it saved/loaded from map files in the jvm code and passed through to the js code using the commit/set/load cli api already implemented.

Task5: Implement UI for displaying current Config (sites and languages) and saving component data against site/language combinations. This should include a dropdown or similar UI element to select the current site and language, and ensure that all component data is saved and loaded with respect to the selected site/language.


# DONE

Task1: Implement an object IconLoader to be used as an hook/aggregator of icon names. modify the Icon component to achieve this (the render method). have the IconLoader provide a method to be employed in head elements that adds query parameters with icon names to avoid downloading all thousands of icons long icon font. ensure it follows the best practices of the font service. icons must be in alphabetical name (check if also the axis must be in alphabetical names). Omit the optical size axis because it causes bugs when its not automanaged by the browser. Better yet call this object FontsLoader and look at Typography css object what other variable fonts our project uses in adition to material-symbols (as variable font) and make a respective method for each one so that they can be employed in head tags to get our typography related fonts/variable fonts by linking to the google font service and ensuring best practices for accessing it.


# OUTSTANDING:
