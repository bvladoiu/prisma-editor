package prisma.editor

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.html.dom.create
import kotlinx.html.p
import org.w3c.dom.HTMLElement
import prisma.editor.component.*
import prisma.editor.pages.PagesInitialData
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
            "catalog" -> addCatalogPage(mainContent)
            else -> {
                val notFound = document.create.p { +"Page not found" }
                mainContent.appendChild(notFound)
            }
        }

        root.appendChild(scaffoldElement)
        root.appendChild(editFab.buildHtml())
    }

    fun toggle(toEditMode: Boolean) {
        isEditing = toEditMode

        BottomDrawer.toggleDrawer()

        if (isEditing) {
            editFab.iconName = "save"
        } else {
            editFab.iconName = "edit"
        }
        editFab.refresh()
        toggleComponents(isEditing)
        if (!isEditing) {
            bottomDrawer.updateValues()

            Config.currentSite = bottomDrawer.currentSite
            Config.currentLanguage = bottomDrawer.currentLanguage
            Config.currentPageTag = bottomDrawer.currentPageTag

            bottomDrawer.commit()

        }
    }

    /**
     * Toggles all components to edit or preview mode.
     * Finds all elements with kotlinInstance property and calls edit() or preview() on them.
     *
     * @param toEditMode If true, switches components to edit mode. If false, switches to preview mode.
     */
    fun toggleComponents(toEditMode: Boolean) {
        val allElements = document.querySelectorAll("*")

        for (i in 0 until allElements.length) {
            val element = allElements.item(i) as? HTMLElement
            val component = element?.asDynamic()?.kotlinInstance
            if (component != null) {
                if (toEditMode) {
                    component.edit()
                } else {
                    component.preview()
                }
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

@JsName("receivePageList")
fun receivePageList(pageList: dynamic) {
    EditorJs.bottomDrawer.setPageList(pageList)
}

private fun addHomePage(mainContent: HTMLElement) {
    window.setTimeout({
        GlobalScope.launch {
            val homeComponent = if (Config.currentLanguage == "de") {
                PagesInitialData.createDeHomePage()
            } else {
                PagesInitialData.createEnHomePage()
            }
            val homeElement = homeComponent.create()
            mainContent.appendChild(homeElement)
        }
    }, 0)
}

private fun addCatalogPage(mainContent: HTMLElement) {
    window.setTimeout({
        GlobalScope.launch {
            val catalogComponent = if (Config.currentLanguage == "de") {
                PagesInitialData.createDeCatalogPage()
            } else {
                PagesInitialData.createEnCatalogPage()
            }
            val catalogElement = catalogComponent.create()
            mainContent.appendChild(catalogElement)
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

    // Handle refreshPageList message
    if (tag == "refreshPageList") {
        EditorJs.bottomDrawer.load() // Request updated page list from JVM
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
        EditorJs.openPage("catalog")
    }
}
