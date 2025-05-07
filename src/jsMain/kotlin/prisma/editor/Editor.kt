package prisma.editor

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.onClickFunction
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.HTMLElement
import prisma.editor.ui.pages.Home

/**
 * Main entry point for the Prisma Editor using kotlinx.html.
 */
object Editor {
    /**
     * Opens a page with the specified name.
     */
    fun openPage(name: String) {
        val root = document.getElementById("root") as HTMLElement
        root.innerHTML = ""
        root.append {
            div {
                id = "editor-scaffold"
                attributes["style"] = """
                    display: flex;
                    flex-direction: column;
                    height: 100vh;
                    width: 100%;
                """

                // AppBar
                appBar()

                // Content area
                div {
                    id = "content-area"
                    attributes["style"] = """
                        display: flex;
                        flex-grow: 1;
                        overflow: hidden;
                    """

                    // Drawer (initially hidden with CSS)
                    drawer()

                    // Main content
                    div {
                        id = "main-content"
                        attributes["style"] = """
                            flex-grow: 1;
                            padding: 16px;
                            overflow: auto;
                        """

                        when (name.lowercase()) {
                            "home" -> homePage()
                            else -> p { +"Page not found" }
                        }
                    }
                }
            }
        }

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
                    menuIcon.innerHTML = '✕';
                } else {
                    menuIcon.innerHTML = '☰';
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
 * Creates the app bar at the top of the page.
 */
private fun FlowContent.appBar() {
    header {
        attributes["style"] = """
            display: flex;
            align-items: center;
            padding: 8px 16px;
            background-color: #6200EE;
            color: white;
            height: 56px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.2);
        """

        // Menu button
        button {
            attributes["style"] = """
                background-color: transparent;
                border: 0;
                color: white;
                cursor: pointer;
                padding: 8px;
                margin-right: 16px;
            """
            id = "menu-button"
            onClickFunction = { 
                js("toggleDrawer()")
            }

            // Menu icon
            span {
                id = "menu-icon"
                attributes["style"] = """
                    font-size: 24px;
                    line-height: 1;
                """
                +"☰"
            }
        }

        // Title
        h1 {
            attributes["style"] = """
                margin: 0;
                font-size: 20px;
                font-weight: 500;
            """
            +"Prisma Editor"
        }
    }
}

/**
 * Creates the navigation drawer.
 */
private fun FlowContent.drawer() {
    nav {
        id = "drawer"
        attributes["style"] = """
            width: 240px;
            height: 100%;
            background-color: white;
            box-shadow: 2px 0 4px rgba(0,0,0,0.2);
            overflow: auto;
            transform: translateX(-240px);
            transition: transform 0.3s ease-in-out;
        """

        // Drawer header
        header {
            attributes["style"] = """
                padding: 16px;
                background-color: #7D3DF3;
                color: white;
            """
            h2 {
                attributes["style"] = """
                    margin: 0;
                    font-size: 18px;
                """
                +"Navigation"
            }
        }

        // Drawer items
        ul {
            attributes["style"] = """
                list-style-type: none;
                padding: 0;
                margin: 0;
            """
            drawerItem("Home", "home")
        }
    }
}

/**
 * Creates a drawer item.
 */
private fun UL.drawerItem(text: String, route: String) {
    li {
        attributes["style"] = "padding: 0; margin: 0;"
        a {
            attributes["style"] = """
                padding: 16px;
                cursor: pointer;
                background-color: #FFFFFF;
                display: block;
                text-decoration: none;
                color: black;
            """
            href = "#"
            onClickFunction = { event ->
                event.preventDefault()
                val routeJs = route // Capture the route in a local variable
                js("navigateTo(arguments[0])")(routeJs)
            }
            +text
        }
    }
}

/**
 * Creates the home page.
 */
private fun FlowContent.homePage() {
    // Launch a coroutine to load and create the home page
    kotlinx.browser.window.setTimeout({
        kotlinx.coroutines.GlobalScope.launch {
            val homeComponent = Home()
            val homeElement = homeComponent.create()
            document.getElementById("main-content")?.appendChild(homeElement)
        }
    }, 0)
}

/**
 * Main function that initializes the application.
 */
fun main() {
    window.onload = {
        Editor.openPage("home")
    }
}
