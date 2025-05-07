package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.css.*
import prisma.editor.styles.Theme

/**
 * Represents the main editor scaffold component.
 * This component includes the app bar, drawer, and content area.
 */
class EditorScaffold {
    /**
     * Creates and returns the editor scaffold element.
     */
    fun create(): HTMLElement {
        val scaffold = document.create.div {
            id = "editor-scaffold"
            attributes["editor-scaffold"] = ""
        }

        // AppBar
        val appBar = createAppBar()
        scaffold.appendChild(appBar)

        // Content area
        val contentArea = document.create.div {
            id = "content-area"
            attributes["content-area"] = ""
        }

        // Main content
        val mainContent = document.create.div {
            id = "main-content"
            attributes["main-content"] = ""
        }

        contentArea.appendChild(mainContent)
        scaffold.appendChild(contentArea)

        return scaffold
    }

    /**
     * Creates the app bar element.
     */
    private fun createAppBar(): HTMLElement {
        val header = document.create.header {
            attributes["app-bar"] = ""

            // Menu button
            button {
                attributes["menu-button"] = ""
                id = "menu-button"
                
                // Menu icon
                span {
                    id = "menu-icon"
                    attributes["class"] = "material-symbols-outlined"
                    attributes["menu-icon"] = ""
                    +"menu"
                }
            }

            // Title
            h1 {
                attributes["app-bar-title"] = ""
                +"Prisma Editor"
            }
        }

        return header
    }

    /**
     * Creates and returns a stylesheet for the EditorScaffold component.
     * This method uses CSSOM API to create a stylesheet with rules for the component.
     */
    fun stylesheet(): CSSStyleSheet {
        // Create a new style element
        val styleElement = document.createElement("style")
        document.head?.appendChild(styleElement)

        // Get the stylesheet from the document's styleSheets collection
        val stylesheet = document.styleSheets[document.styleSheets.length - 1] as CSSStyleSheet

        // Add rules to the stylesheet

        // Editor scaffold rule
        val scaffoldRule = """
            [editor-scaffold] {
                display: flex;
                flex-direction: column;
                height: 100vh;
                width: 100%;
            }
        """.trimIndent()
        stylesheet.insertRule(scaffoldRule, stylesheet.cssRules.length)

        // Content area rule
        val contentAreaRule = """
            [content-area] {
                display: flex;
                flex-grow: 1;
                overflow: hidden;
            }
        """.trimIndent()
        stylesheet.insertRule(contentAreaRule, stylesheet.cssRules.length)

        // Main content rule
        val mainContentRule = """
            [main-content] {
                flex-grow: 1;
                padding: ${Theme.Spacing.md};
                overflow: auto;
            }
        """.trimIndent()
        stylesheet.insertRule(mainContentRule, stylesheet.cssRules.length)

        // App bar rule
        val appBarRule = """
            [app-bar] {
                display: flex;
                align-items: center;
                padding: ${Theme.Spacing.sm} ${Theme.Spacing.md};
                background-color: #6200EE;
                color: ${Theme.Colors.white};
                height: ${Theme.Spacing.navHeight};
                box-shadow: 0 2px 4px rgba(0,0,0,0.2);
            }
        """.trimIndent()
        stylesheet.insertRule(appBarRule, stylesheet.cssRules.length)

        // Menu button rule
        val menuButtonRule = """
            [menu-button] {
                background-color: transparent;
                border: 0;
                color: ${Theme.Colors.white};
                cursor: pointer;
                padding: ${Theme.Spacing.sm};
                margin-right: ${Theme.Spacing.md};
            }
        """.trimIndent()
        stylesheet.insertRule(menuButtonRule, stylesheet.cssRules.length)

        // Menu icon rule
        val menuIconRule = """
            [menu-icon] {
                font-size: 24px;
                line-height: 1;
            }
        """.trimIndent()
        stylesheet.insertRule(menuIconRule, stylesheet.cssRules.length)

        // App bar title rule
        val appBarTitleRule = """
            [app-bar-title] {
                margin: 0;
                font-size: ${Theme.Typography.fontMd};
                font-weight: 500;
            }
        """.trimIndent()
        stylesheet.insertRule(appBarTitleRule, stylesheet.cssRules.length)

        return stylesheet
    }
}