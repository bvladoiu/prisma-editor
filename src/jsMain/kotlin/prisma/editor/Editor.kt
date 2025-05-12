package prisma.editor

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.dom.create
import kotlinx.html.js.onClickFunction
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.HTMLElement
import prisma.editor.component.EditorScaffold
import prisma.editor.pages.Home
import kotlin.js.Json
import kotlin.js.json

/**
 * Main entry point for the Prisma Editor using kotlinx.html.
 */
object Editor {
    // Settings map for persisting data
    private var settings: MutableMap<String, Any> = mutableMapOf()

    /**
     * Imports settings from a JSON string.
     * This method is exposed to the JVM playwright logic.
     */
    @JsName("import")
    fun import(jsonString: String) {
        try {
            val jsonObj = JSON.parse<Json>(jsonString)
            val keys = js("Object.keys(jsonObj)")

            settings.clear()
            for (i in 0 until keys.length) {
                val key = keys[i] as String
                jsonObj[key]?.let { value ->
                    settings[key] = value
                }
            }

            console.log("Settings imported successfully")
        } catch (e: Exception) {
            console.error("Error importing settings: ${e.message}")
        }
    }

    /**
     * Exports settings as a JSON string.
     * This method is exposed to the JVM playwright logic.
     */
    @JsName("export")
    fun export(): String {
        return try {
            val jsonObj = json()
            settings.forEach { (key, value) ->
                jsonObj[key] = value
            }
            JSON.stringify(jsonObj)
        } catch (e: Exception) {
            console.error("Error exporting settings: ${e.message}")
            "{}"
        }
    }

    /**
     * Gets a setting value by key.
     */
    fun getSetting(key: String): Any? {
        return settings[key]
    }

    /**
     * Sets a setting value by key.
     */
    fun setSetting(key: String, value: Any) {
        settings[key] = value
    }

    /**
     * Opens a page with the specified name.
     */
    fun openPage(name: String) {
        val root = document.getElementById("root") as HTMLElement
        root.innerHTML = ""

        // Create the editor scaffold
        val editorScaffold = EditorScaffold()
        val scaffoldElement = editorScaffold.create()

        // Apply the stylesheet
        editorScaffold.stylesheet()

        // Add the drawer to the content area
        val contentArea = scaffoldElement.querySelector("[content-area]") as HTMLElement
        addDrawer(contentArea)

        // Get the main content area
        val mainContent = scaffoldElement.querySelector("#main-content") as HTMLElement

        // Add page content based on the route
        when (name.lowercase()) {
            "home" -> addHomePage(mainContent)
            else -> {
                val notFound = document.create.p { +"Page not found" }
                mainContent.appendChild(notFound)
            }
        }

        // Add the scaffold to the root
        root.appendChild(scaffoldElement)

        // Add JavaScript for drawer toggle
        val script = document.createElement("script") as HTMLElement
        script.innerHTML = """
            function toggleDrawer() {
                const drawer = document.getElementById('drawer');
                drawer.style.transform = drawer.style.transform === 'translateX(0px)' 
                    ? 'translateX(-240px)' 
                    : 'translateX(0px)';

                const menuIcon = document.getElementById('menu-icon');
                if (drawer.style.transform === 'translateX(0px)') {
                    menuIcon.innerHTML = 'close';
                } else {
                    menuIcon.innerHTML = 'menu';
                }
            }

            function navigateTo(route) {
                toggleDrawer();
                setTimeout(() => {
                    prisma.editor.Editor.openPage(route);
                }, 300);
            }
        """
        document.body?.appendChild(script)
    }
}

/**
 * Adds the navigation drawer to the content area.
 */
private fun addDrawer(contentArea: HTMLElement) {
    // Create NavLink for Home with selected state
    val homeLink = prisma.editor.component.NavLink("Home", "home", "home", true)

    // Create Drawer with NavLinks
    val drawer = prisma.editor.component.Drawer(listOf(homeLink))

    // Apply the stylesheet
    drawer.stylesheet()
    homeLink.stylesheet()

    // Append the drawer to the content area
    val drawerElement = drawer.preview()
    contentArea.appendChild(drawerElement)
}

/**
 * Adds the home page to the main content area.
 */
private fun addHomePage(mainContent: HTMLElement) {
    // Launch a coroutine to load and create the home page
    kotlinx.browser.window.setTimeout({
        kotlinx.coroutines.GlobalScope.launch {
            val homeComponent = Home()
            val homeElement = homeComponent.create()
            mainContent.appendChild(homeElement)
        }
    }, 0)
}

@JsName("receiveData")
fun receiveData(tag: String, jsonString: String) {
    val data = JSON.parse<dynamic>(jsonString)
    val element = document.querySelector("[$tag]") as? HTMLElement

    if (element != null) {
        val component = element.asDynamic().kotlinComponent
        if (component != null && jsTypeOf(component.set) == "function") {
            component.set(data)
        }
    }
}

/**
 * Main function that initializes the application.
 */
fun main() {
    window.onload = {
        console.log("window.onload")
        Editor.openPage("home")
    }
}
