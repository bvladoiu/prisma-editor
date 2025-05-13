package prisma.editor

import kotlinx.browser.*
import kotlinx.coroutines.*
import kotlinx.html.dom.create
import kotlinx.html.p
import org.w3c.dom.HTMLElement
import prisma.editor.component.EditorScaffold
import prisma.editor.component.FloatingActionButton
import prisma.editor.component.Icon
import prisma.editor.pages.Home

object Editor {

    fun openPage(name: String) {
        val root = document.getElementById("root") as HTMLElement
        root.innerHTML = ""

        val editorScaffold = EditorScaffold()
        val scaffoldElement = editorScaffold.preview()

        EditorScaffold.cssRules()
        FloatingActionButton.cssRules()

        val contentArea = scaffoldElement.querySelector("[content-area]") as HTMLElement
        addDrawer(contentArea)

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
        val editFab = FloatingActionButton("edit", "console.log('Edit button clicked')")
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

private fun addDrawer(contentArea: HTMLElement) {
    val homeLink = prisma.editor.component.NavLink("Home", "home", "home", true)
    val drawer = prisma.editor.component.Drawer(listOf(homeLink))
    val drawerElement = drawer.preview()
    contentArea.appendChild(drawerElement)
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
