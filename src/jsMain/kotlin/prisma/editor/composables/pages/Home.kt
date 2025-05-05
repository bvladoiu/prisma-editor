package prisma.editor.composables.pages

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import prisma.editor.model.Home
import prisma.editor.model.loadHome
import prisma.editor.model.Update
import prisma.editor.composables.component.*
import prisma.editor.styles.PageStyles

@Composable
fun Home(homeData: Home? = null) {
    // Use a mutable state to hold the loaded data
    var data by remember { mutableStateOf(homeData) }
    var isLoading by remember { mutableStateOf(homeData == null) }
    var error by remember { mutableStateOf<String?>(null) }

    // Load data if not provided
    LaunchedEffect(Unit) {
        if (data == null) {
            try {
                data = loadHome()
                isLoading = false
            } catch (e: Exception) {
                error = e.message ?: "Failed to load home data"
                isLoading = false
            }
        }
    }

    Div(attrs = {
        style(PageStyles.container)
    }) {
        when {
            isLoading -> {
                // Show loading indicator
                Div(attrs = {
                    style(PageStyles.loadingContainer)
                }) {
                    Text("Loading...")
                }
            }
            error != null -> {
                // Show error message
                Div(attrs = {
                    style(PageStyles.errorContainer)
                }) {
                    Text("Error: $error")
                }
            }
            data != null -> {
                // Navigation Menu
                NavigationMenu(data!!.navigation)

                // Main content container
                Div(attrs = {
                    style(PageStyles.mainContent)
                }) {
                    // Hero Section
                    Section(
                        title = null,
                        isDivider = false,
                        marginTop = 5.cssRem
                    ) {
                        HeroContent(
                            title = data!!.hero.title,
                            description = data!!.hero.description,
                            buttonText = data!!.hero.buttonText
                        )
                    }

                    // Render all sections
                    data!!.sections.forEach { section ->
                        Section(
                            title = section.title,
                            isDivider = section.isDivider
                        ) {
                            // Render section content based on its type
                            when {
                                // Section with items (Core Expertise or Why Work With Us)
                                section.items != null -> {
                                    val minWidth = if (section.title == "Why Work With Us?") "200px" else "300px"

                                    Div(attrs = {
                                        style {
                                            display(DisplayStyle.Grid)
                                            property("grid-template-columns", "repeat(auto-fit, minmax($minWidth, 1fr))")
                                            gap(16.px)
                                        }
                                    }) {
                                        section.items.forEach { item ->
                                            if (section.title == "Our Core Expertise") {
                                                ExpertiseItem(
                                                    title = item.title,
                                                    description = item.description
                                                )
                                            } else {
                                                ReasonItem(
                                                    title = item.title,
                                                    description = item.description
                                                )
                                            }
                                        }
                                    }

                                    // Add button if present (for Why Work With Us section)
                                    if (section.buttonText != null) {
                                        Div(attrs = {
                                            style {
                                                marginTop(2.cssRem)
                                            }
                                        }) {
                                            Button(attrs = {
                                                style {
                                                    backgroundColor(Color("#7112a1"))
                                                    color(Color("#56b2f0"))
                                                    padding(8.px, 16.px)
                                                    border(0.px)
                                                    borderRadius(4.px)
                                                    cursor("pointer")
                                                }
                                            }) {
                                                Text(section.buttonText)
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
                                    UpdatesList(
                                        updates = section.updates.map { 
                                            Update(it.title, it.author, it.date) 
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                // Footer
                Footer(data!!.footer)
            }
        }
    }
}
