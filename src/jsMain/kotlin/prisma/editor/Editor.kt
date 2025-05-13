package prisma.editor

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.html.dom.create
import kotlinx.html.p
import org.w3c.dom.HTMLElement
import prisma.editor.component.*
import prisma.editor.pages.Home

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
        BottomDrawer.cssRules()

        val contentArea = scaffoldElement.querySelector("[content-area]") as HTMLElement
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

        // No need for script element anymore as drawer functionality is handled by components
    }

    @JsName("toggleEditMode")
    fun toggleEditMode() {
        isEditing = !isEditing

        // Toggle bottom drawer
        prisma.editor.component.BottomDrawer.toggleDrawer()

        // Change FAB icon
        val fab = document.getElementById("floating-action-button") as? HTMLElement
        val fabIcon = fab?.querySelector("[material-icon]") as? HTMLElement

        // Get the bottom drawer component
        val bottomDrawer = document.querySelector("[${BottomDrawer.TAG}]")?.asDynamic()?.kotlinInstance as? BottomDrawer

        if (isEditing) {
            fabIcon?.textContent = "save"
        } else {
            fabIcon?.textContent = "edit"

            if (bottomDrawer != null) {
                // Update the drawer's values from the form inputs
                bottomDrawer.updateValues()

                // Update the properties
                currentSite = bottomDrawer.currentSite
                currentLanguage = bottomDrawer.currentLanguage

                // Save the properties
                saveProperties()
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


private fun addBottomDrawer(contentArea: HTMLElement) {
    val bottomDrawer = BottomDrawer(
        currentSite = Editor.currentSite,
        currentLanguage = Editor.currentLanguage
    )
    val bottomDrawerElement = bottomDrawer.preview()
    contentArea.appendChild(bottomDrawerElement)
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
