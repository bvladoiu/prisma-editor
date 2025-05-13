package prisma.editor

import kotlinx.browser.*
import kotlinx.coroutines.*
import kotlinx.html.dom.create
import kotlinx.html.p
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLOptionElement
import org.w3c.dom.HTMLSelectElement
import prisma.editor.component.EditorScaffold
import prisma.editor.component.FloatingActionButton
import prisma.editor.component.Icon
import prisma.editor.component.Drawer
import prisma.editor.component.NavLink
import prisma.editor.pages.Home
import kotlinx.html.*
import kotlin.js.JSON

object Editor {
    // Site and language properties
    var currentSite: String = "prisma" // Default site
    var currentLanguage: String = "en" // Default language
    private var isEditing: Boolean = false

    // Tag for editor properties
    const val EDITOR_PROPERTIES_TAG = "editor-properties"

    init {
        // Load saved properties on initialization
        loadProperties()
    }

    fun openPage(name: String) {
        val root = document.getElementById("root") as HTMLElement
        root.innerHTML = ""

        val editorScaffold = EditorScaffold()
        val scaffoldElement = editorScaffold.preview()

        EditorScaffold.cssRules()
        FloatingActionButton.cssRules()
        Drawer.cssRules()
        NavLink.cssRules()

        val contentArea = scaffoldElement.querySelector("[content-area]") as HTMLElement
        addDrawer(contentArea)
        addBottomDrawer(contentArea)

        val mainContent = scaffoldElement.querySelector("#main-content") as HTMLElement

        when (name.lowercase()) {
            "home" -> addHomePage(mainContent)
            else -> {
                val notFound = document.create.p { +"Page not found" }
                mainContent.appendChild(notFound)
            }
        }

        root.appendChild(scaffoldElement)

        // Add floating action button with edit icon
        val editFab = FloatingActionButton("edit", "prisma.editor.Editor.toggleEditMode()")
        val fabElement = editFab.preview()
        root.appendChild(fabElement)

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

            function toggleBottomDrawer() {
                const drawer = document.getElementById('bottom-drawer');
                drawer.style.transform = drawer.style.transform === 'translateY(0px)' 
                    ? 'translateY(100%)' 
                    : 'translateY(0px)';
            }

            function navigateTo(route) {
                toggleDrawer();
                setTimeout(() => {
                    prisma.editor.Editor.openPage(route);
                }, 300);
            }

            function updateLanguageOptions() {
                prisma.editor.Editor.updateLanguageOptions();
            }
        """
        document.body?.appendChild(script)
    }

    @JsName("toggleEditMode")
    fun toggleEditMode() {
        isEditing = !isEditing

        // Toggle bottom drawer
        js("toggleBottomDrawer()")

        // Change FAB icon
        val fab = document.getElementById("floating-action-button") as? HTMLElement
        val fabIcon = fab?.querySelector("[material-icon]") as? HTMLElement

        if (isEditing) {
            fabIcon?.textContent = "save"

            // When in edit mode, show the current site and language in the inputs
            val siteSelect = document.getElementById("site-select") as? HTMLSelectElement
            val languageSelect = document.getElementById("language-select") as? HTMLSelectElement

            siteSelect?.value = currentSite
            languageSelect?.value = currentLanguage
        } else {
            fabIcon?.textContent = "edit"

            // When saving, update the site and language properties
            val siteSelect = document.getElementById("site-select") as? HTMLSelectElement
            val languageSelect = document.getElementById("language-select") as? HTMLSelectElement

            if (siteSelect != null && languageSelect != null) {
                val newSite = siteSelect.value
                val newLanguage = languageSelect.value

                // Update the properties
                currentSite = newSite
                currentLanguage = newLanguage

                // Save the properties
                saveProperties()
            }
        }
    }

    @JsName("updateLanguageOptions")
    fun updateLanguageOptions() {
        val siteSelect = document.getElementById("site-select") as? HTMLSelectElement
        val languageSelect = document.getElementById("language-select") as? HTMLSelectElement

        if (siteSelect != null && languageSelect != null) {
            val selectedSite = siteSelect.value

            // Clear existing options
            languageSelect.innerHTML = ""

            // Get languages for the selected site
            val languages = when (selectedSite) {
                "contadeal" -> arrayOf("en", "ro")
                "prisma" -> arrayOf("en", "de")
                else -> arrayOf("en")
            }

            // Add new options
            for (language in languages) {
                val option = document.createElement("option") as HTMLOptionElement
                option.value = language
                option.text = language.uppercase()
                languageSelect.add(option)
            }

            // Try to set the current language
            languageSelect.value = currentLanguage

            // If the value didn't change (language not available), select the first option
            if (languageSelect.value != currentLanguage && languageSelect.options.length > 0) {
                languageSelect.selectedIndex = 0
            }
        }
    }

    private fun saveProperties() {
        val data = mapOf(
            "site" to currentSite,
            "language" to currentLanguage
        )
        val jsonData = JSON.stringify(data)
        console.log("save:$EDITOR_PROPERTIES_TAG", jsonData)
    }

    private fun loadProperties() {
        console.log("load:$EDITOR_PROPERTIES_TAG")
        // The actual loading will be handled by the JVM side
        // which will call receiveData with the saved properties
    }

    fun set(data: dynamic) {
        if (data.site != null) {
            currentSite = data.site as String
        }
        if (data.language != null) {
            currentLanguage = data.language as String
        }
    }
}

private fun addDrawer(contentArea: HTMLElement) {
    val homeLink = prisma.editor.component.NavLink("Home", "home", "home", true)
    val drawer = prisma.editor.component.Drawer(listOf(homeLink))
    val drawerElement = drawer.preview()
    contentArea.appendChild(drawerElement)
}

private fun addBottomDrawer(contentArea: HTMLElement) {
    val bottomDrawer = document.create.div {
        id = "bottom-drawer"
        attributes["bottom-drawer"] = ""
        attributes[Editor.EDITOR_PROPERTIES_TAG] = ""
        style = "position: fixed; bottom: 0; left: 0; width: 100%; background-color: white; box-shadow: 0 -2px 4px rgba(0,0,0,0.2); padding: 16px; transform: translateY(100%); transition: transform 0.3s ease-in-out; z-index: 1000;"

        h3 {
            +"Site and Language Settings"
        }

        div {
            style = "display: flex; flex-direction: column; gap: 16px;"

            div {
                style = "display: flex; flex-direction: column; gap: 8px;"
                label {
                    htmlFor = "site-select"
                    +"Site:"
                }
                select {
                    id = "site-select"
                    style = "padding: 8px; border-radius: 4px; border: 1px solid #ccc;"
                    attributes["onchange"] = "updateLanguageOptions()"

                    // Add options for sites
                    option {
                        value = "contadeal"
                        +"ContaDeal"
                    }
                    option {
                        value = "prisma"
                        +"PRISMA-Software"
                    }
                }
            }

            div {
                style = "display: flex; flex-direction: column; gap: 8px;"
                label {
                    htmlFor = "language-select"
                    +"Language:"
                }
                select {
                    id = "language-select"
                    style = "padding: 8px; border-radius: 4px; border: 1px solid #ccc;"
                }
            }
        }
    }

    contentArea.appendChild(bottomDrawer)

    // Initialize language options based on current site
    window.setTimeout({
        js("updateLanguageOptions()")
    }, 100)
}

private fun addHomePage(mainContent: HTMLElement) {
    window.setTimeout({
        GlobalScope.launch {
            val homeComponent = Home()
            val homeElement = homeComponent.create()
            mainContent.appendChild(homeElement)
        }
    }, 0)
}

@JsName("receiveData")
fun receiveData(tag: String, jsonString: String) {
    val data = JSON.parse<dynamic>(jsonString)

    if (tag == Editor.EDITOR_PROPERTIES_TAG) {
        Editor.set(data)
        return
    }

    val element = document.querySelector("[$tag]") as? HTMLElement

    if (element != null) {
        val component = element.asDynamic().kotlinInstance
        if (component != null && jsTypeOf(component.set) == "function") {
            component.set(data)
        }
    }
}

fun main() {
    window.onload = {
        console.log("window.onload")
        Editor.openPage("home")
    }
}
