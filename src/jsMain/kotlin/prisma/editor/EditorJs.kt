package prisma.editor

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.html.dom.create
import kotlinx.html.p
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import prisma.editor.component.*
import prisma.editor.pages.Home
import kotlin.js.JSON

object EditorJs {

    const val TAG = "editor"

    private var isEditing: Boolean = false

    val editFab = FloatingActionButton("edit") {
        toggle(!isEditing)
    }

    val bottomDrawer = BottomDrawer()

    init {
        load()
    }

    fun openPage(name: String) {
        val root = document.getElementById("root") as HTMLElement
        root.innerHTML = ""

        val editorScaffold = EditorScaffold()
        val scaffoldElement = editorScaffold.preview()

        val contentArea = scaffoldElement.querySelector("[content-area]") as HTMLElement
        contentArea.appendChild(bottomDrawer.preview())

        val mainContent = scaffoldElement.querySelector("#main-content") as HTMLElement

        when (name.lowercase()) {
            "home" -> addHomePage(mainContent)
            else -> {
                val notFound = document.create.p { +"Page not found" }
                mainContent.appendChild(notFound)
            }
        }

        root.appendChild(scaffoldElement)
        root.appendChild(editFab.preview())
    }

    fun toggle(toEditMode: Boolean) {
        isEditing = toEditMode

        // Toggle bottom drawer
        BottomDrawer.toggleDrawer()

        // Change FAB icon
        if (isEditing) {
            editFab.iconName = "save"
        } else {
            editFab.iconName = "edit"
        }
        editFab.refresh()

        // Get the bottom drawer component
        val bottomDrawer = document.querySelector("[${BottomDrawer.TAG}]")?.asDynamic()?.kotlinInstance as? BottomDrawer

        if (isEditing) {
            // Toggle all components to edit mode
            toggleComponents(true)
        } else {
            // Toggle all components to preview mode
            toggleComponentsToPreviewMode()

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

    /**
     * Toggles all components to edit or preview mode.
     * Finds all elements with kotlinInstance property and calls edit() or preview() on them.
     *
     * @param toEditMode If true, switches components to edit mode. If false, switches to preview mode.
     */
    fun toggleComponents(toEditMode: Boolean) {
        // Get all elements with section-container attribute (Section components)
        val sectionElements = document.querySelectorAll("[${Section.TAG}]")
        for (i in 0 until sectionElements.length) {
            val element = sectionElements.item(i) as? Element
            if (element != null) {
                val component = element.asDynamic().kotlinInstance as? Section
                if (component != null) {
                    val parent = element.asDynamic().parentElement
                    if (parent != null) {
                        val newElement = if (toEditMode) component.edit() else component.preview()
                        parent.replaceChild(newElement, element)
                    }
                }
            }
        }

        // Text components have been replaced with regular HTML elements with typography classes

        // Get all elements with updates-list attribute (Latest components)
        val latestElements = document.querySelectorAll("[${Latest.TAG}]")
        for (i in 0 until latestElements.length) {
            val element = latestElements.item(i) as? Element
            if (element != null) {
                val component = element.asDynamic().kotlinInstance as? Latest
                if (component != null) {
                    val parent = element.asDynamic().parentElement
                    if (parent != null) {
                        val newElement = if (toEditMode) component.edit() else component.preview()
                        parent.replaceChild(newElement, element)
                    }
                }
            }
        }

        // Get all elements with update-item attribute (ArticleCard components)
        val articleElements = document.querySelectorAll("[${ArticleCard.TAG}]")
        for (i in 0 until articleElements.length) {
            val element = articleElements.item(i) as? Element
            if (element != null) {
                val component = element.asDynamic().kotlinInstance as? ArticleCard
                if (component != null) {
                    val parent = element.asDynamic().parentElement
                    if (parent != null) {
                        val newElement = if (toEditMode) component.edit() else component.preview()
                        parent.replaceChild(newElement, element)
                    }
                }
            }
        }

        // Get all elements with expertise attribute (Expertise components)
        val expertiseElements = document.querySelectorAll("[${Expertise.TAG}]")
        for (i in 0 until expertiseElements.length) {
            val element = expertiseElements.item(i) as? Element
            if (element != null) {
                val component = element.asDynamic().kotlinInstance as? Expertise
                if (component != null) {
                    val parent = element.asDynamic().parentElement
                    if (parent != null) {
                        val newElement = if (toEditMode) component.edit() else component.preview()
                        parent.replaceChild(newElement, element)
                    }
                }
            }
        }

        // Get all elements with section-content attribute (SectionContent components)
        val sectionContentElements = document.querySelectorAll("[${SectionContent.TAG}]")
        for (i in 0 until sectionContentElements.length) {
            val element = sectionContentElements.item(i) as? Element
            if (element != null) {
                val component = element.asDynamic().kotlinInstance as? SectionContent
                if (component != null) {
                    val parent = element.asDynamic().parentElement
                    if (parent != null) {
                        val newElement = if (toEditMode) component.edit() else component.preview()
                        parent.replaceChild(newElement, element)
                    }
                }
            }
        }

        // Get all elements with section-header attribute (SectionHeader components)
        val sectionHeaderElements = document.querySelectorAll("[${SectionHeader.TAG}]")
        for (i in 0 until sectionHeaderElements.length) {
            val element = sectionHeaderElements.item(i) as? Element
            if (element != null) {
                val component = element.asDynamic().kotlinInstance as? SectionHeader
                if (component != null) {
                    val parent = element.asDynamic().parentElement
                    if (parent != null) {
                        val newElement = if (toEditMode) component.edit() else component.preview()
                        parent.replaceChild(newElement, element)
                    }
                }
            }
        }

        // Add more component types as needed
    }

    /**
     * Toggles all components to edit mode.
     * Finds all elements with kotlinInstance property and calls edit() on them.
     */
    private fun toggleComponentsToEditMode() {
        toggleComponents(true)
    }

    /**
     * Toggles all components to preview mode.
     * Finds all elements with kotlinInstance property and calls preview() on them.
     */
    private fun toggleComponentsToPreviewMode() {
        toggleComponents(false)
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

    if (tag == EditorJs.TAG) {
        EditorJs.set(data)
        return
    }

    val element = document.querySelector("[$tag]") as? HTMLElement
    if (element != null) {
        val component = element.asDynamic().kotlinInstance
        component.set(data)
    }
}

fun main() {
    window.onload = {
        console.log("window.onload")
        EditorJs.openPage("home")
    }
}
