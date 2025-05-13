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
import kotlin.js.JSON

object Editor {
    private var isEditing: Boolean = false

    const val TAG = "editor"

    init {
        // Load saved properties on initialization
        load()
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
        BottomDrawer.toggleDrawer()

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
                Config.currentSite = bottomDrawer.currentSite
                Config.currentLanguage = bottomDrawer.currentLanguage
                Config.currentPageTag = bottomDrawer.currentPageTag

                // Save the properties
                commit()
            }
        }
    }


    private fun commit() {
        val data = mapOf(
            "site" to Config.currentSite,
            "language" to Config.currentLanguage,
            "pageTag" to Config.currentPageTag
        )
        val jsonData = JSON.stringify(data)
        console.log("save:$TAG", jsonData)
    }

    private fun load() {
        console.log("load:$TAG")
        // The actual loading will be handled by the JVM side
        // which will call receiveData with the saved properties
    }

    fun set(data: dynamic) {
        if (data.site != null) {
            Config.currentSite = data.site as String
        }
        if (data.language != null) {
            Config.currentLanguage = data.language as String
        }
        if (data.pageTag != null) {
            Config.currentPageTag = data.pageTag as String
        }
    }
}


private fun addBottomDrawer(contentArea: HTMLElement) {
    val bottomDrawer = BottomDrawer()
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

    if (tag == Editor.TAG) {
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
