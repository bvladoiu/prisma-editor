package prisma.editor

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.html.dom.create
import kotlinx.html.p
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import org.w3c.dom.NodeList
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

            // Toggle all components to edit mode
            toggleComponentsToEditMode()
        } else {
            fabIcon?.textContent = "edit"

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
     * Toggles all components to edit mode.
     * Finds all elements with kotlinInstance property and calls edit() on them.
     */
    private fun toggleComponentsToEditMode() {
        // Get all elements with section-container attribute (Section components)
        val sectionElements = document.querySelectorAll("[${Section.TAG}]")
        for (i in 0 until sectionElements.length) {
            val element = sectionElements.item(i) as? Element
            if (element != null) {
                val component = element.asDynamic().kotlinInstance as? Section
                if (component != null) {
                    val parent = element.asDynamic().parentElement
                    if (parent != null) {
                        val editableElement = component.edit()
                        parent.replaceChild(editableElement, element)
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
                        val editableElement = component.edit()
                        parent.replaceChild(editableElement, element)
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
                        val editableElement = component.edit()
                        parent.replaceChild(editableElement, element)
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
                        val editableElement = component.edit()
                        parent.replaceChild(editableElement, element)
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
                        val editableElement = component.edit()
                        parent.replaceChild(editableElement, element)
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
                        val editableElement = component.edit()
                        parent.replaceChild(editableElement, element)
                    }
                }
            }
        }

        // Add more component types as needed
    }

    /**
     * Toggles all components to preview mode.
     * Finds all elements with kotlinInstance property and calls preview() on them.
     */
    private fun toggleComponentsToPreviewMode() {
        // Get all elements with section-container attribute (Section components)
        val sectionElements = document.querySelectorAll("[${Section.TAG}]")
        for (i in 0 until sectionElements.length) {
            val element = sectionElements.item(i) as? Element
            if (element != null) {
                val component = element.asDynamic().kotlinInstance as? Section
                if (component != null) {
                    val parent = element.asDynamic().parentElement
                    if (parent != null) {
                        val previewElement = component.preview()
                        parent.replaceChild(previewElement, element)
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
                        val previewElement = component.preview()
                        parent.replaceChild(previewElement, element)
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
                        val previewElement = component.preview()
                        parent.replaceChild(previewElement, element)
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
                        val previewElement = component.preview()
                        parent.replaceChild(previewElement, element)
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
                        val previewElement = component.preview()
                        parent.replaceChild(previewElement, element)
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
                        val previewElement = component.preview()
                        parent.replaceChild(previewElement, element)
                    }
                }
            }
        }

        // Add more component types as needed
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
