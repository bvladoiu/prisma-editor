package prisma.editor

import kotlinx.browser.*
import kotlinx.coroutines.*
import kotlinx.html.dom.create
import kotlinx.html.p
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
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
    
    private fun saveProperties() {
        val data = mapOf(
            "site" to currentSite,
            "language" to currentLanguage
        )
        val jsonData = JSON.stringify(data)
        console.log("save:editor-properties", jsonData)
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
                    
                    // Add options for languages
                    option {
                        value = "en"
                        +"EN"
                    }
                    option {
                        value = "de"
                        +"DE"
                    }
                    option {
                        value = "ro"
                        +"RO"
                    }
                }
            }
        }
    }
    
    contentArea.appendChild(bottomDrawer)
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