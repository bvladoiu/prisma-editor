package prisma.editor.ui.pages

import kotlinx.html.*
import kotlinx.browser.document
import kotlinx.coroutines.*
import kotlinx.html.dom.append
import org.w3c.dom.HTMLElement
import prisma.editor.model.Home
import prisma.editor.model.loadHome
import prisma.editor.ui.component.*

/**
 * Creates the home page using kotlinx.html.
 */
fun FlowContent.Home(homeData: Home? = null) {
    // We can't use LaunchedEffect or remember in kotlinx.html
    // Instead, we'll load the data and then render the page
    // This is a simplified approach for demonstration purposes

    if (homeData != null) {
        renderHome(homeData)
    } else {
        // Show loading indicator
        div {
            attributes["style"] = """
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            """
            +"Loading..."
        }

        // Load data asynchronously
        MainScope().launch {
            try {
                val data = loadHome()
                // Clear the loading indicator
                val container = document.getElementById("main-content") as HTMLElement
                container.innerHTML = ""
                container.append {
                    Home(data)
                }
            } catch (e: Exception) {
                // Show error message
                val container = document.getElementById("main-content") as HTMLElement
                container.innerHTML = ""
                container.append {
                    div {
                        attributes["style"] = """
                            color: red;
                            padding: 16px;
                            text-align: center;
                        """
                        +"Error: ${e.message ?: "Failed to load home data"}"
                    }
                }
            }
        }
    }
}

/**
 * Renders the home page with the provided data.
 */
private fun FlowContent.renderHome(data: Home) {
    Page {
        // Hero Section
        Section(
            title = null,
            isDivider = false,
            marginTop = "5rem"
        ) {
            Hero(
                title = data.hero.title,
                description = data.hero.description,
                buttonText = data.hero.buttonText
            )
        }

        // Render all sections
        data.sections.forEach { section ->
            Section(
                title = section.title,
                isDivider = section.isDivider
            ) {
                // Render section content based on its type
                when {
                    // Section with items (Core Expertise or Why Work With Us)
                    section.items != null -> {
                        val minWidth = if (section.title == "Why Work With Us?") "200px" else "300px"

                        div {
                            attributes["style"] = """
                                display: grid;
                                grid-template-columns: repeat(auto-fit, minmax($minWidth, 1fr));
                                gap: 16px;
                            """

                            section.items.forEach { item ->
                                if (section.title == "Our Core Expertise") {
                                    Expertise(
                                        title = item.title,
                                        description = item.description
                                    )
                                } else {
                                    Pitch(
                                        title = item.title,
                                        description = item.description
                                    )
                                }
                            }
                        }

                        // Add button if present (for Why Work With Us section)
                        if (section.buttonText != null) {
                            div {
                                attributes["style"] = "margin-top: 2rem;"
                                button {
                                    attributes["style"] = """
                                        background-color: #7112a1;
                                        color: #56b2f0;
                                        padding: 8px 16px;
                                        border: 0;
                                        border-radius: 4px;
                                        cursor: pointer;
                                    """
                                    +section.buttonText
                                }
                            }
                        }
                    }

                    // Keyword Strip section
                    section.keywordStrip != null -> {
                        KeywordStrip(keywords = section.keywordStrip)
                    }

                    // Latest Updates section
                    section.updates != null -> {
                        Updates(updates = section.updates)
                    }
                }
            }
        }

        // Footer
        Footer(data.footer.copyright, data.footer.links)
    }
}

