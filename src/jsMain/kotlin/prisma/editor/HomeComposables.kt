package prisma.editor

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Composable
fun Home(homeData: HomeData? = null) {
    // Use a mutable state to hold the loaded data
    var data by remember { mutableStateOf(homeData) }
    var isLoading by remember { mutableStateOf(homeData == null) }
    var error by remember { mutableStateOf<String?>(null) }

    // Load data if not provided
    LaunchedEffect(Unit) {
        if (data == null) {
            try {
                data = loadHomeData()
                isLoading = false
            } catch (e: Exception) {
                error = e.message ?: "Failed to load home data"
                isLoading = false
            }
        }
    }

    Div(attrs = {
        style {
            backgroundColor(Color("#56b2f0"))
            color(Color.white)
            fontFamily("'Poppins', sans-serif")
            margin(0.px)
            padding(0.px)
        }
    }) {
        when {
            isLoading -> {
                // Show loading indicator
                Div(attrs = {
                    style {
                        display(DisplayStyle.Flex)
                        justifyContent(JustifyContent.Center)
                        alignItems(AlignItems.Center)
                        height(100.vh)
                    }
                }) {
                    Text("Loading...")
                }
            }
            error != null -> {
                // Show error message
                Div(attrs = {
                    style {
                        display(DisplayStyle.Flex)
                        justifyContent(JustifyContent.Center)
                        alignItems(AlignItems.Center)
                        height(100.vh)
                        color(Color.red)
                    }
                }) {
                    Text("Error: $error")
                }
            }
            data != null -> {
                // Navigation Menu
                NavigationMenu(data!!.navigation)

                // Main content container
                Div(attrs = {
                    style {
                        marginTop(5.cssRem)
                        marginBottom(2.cssRem)
                    }
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

@Composable
fun Section(
    title: String?,
    isDivider: Boolean = false,
    marginTop: CSSNumeric = 2.cssRem,
    content: @Composable () -> Unit
) {
    Section(attrs = {
        style {
            marginTop(marginTop)
            marginBottom(2.cssRem)
            padding(0.px, 16.px)
            maxWidth(1200.px)
            property("margin", "0 auto")
        }
    }) {
        Article(attrs = {
            style {
                padding(16.px)
                backgroundColor(rgba(255, 255, 255, 0.03))
                borderRadius(4.px)
            }
        }) {
            if (title != null) {
                if (isDivider) {
                    Header(attrs = {
                        style {
                            fontSize(24.px)
                            fontWeight("bold")
                            marginBottom(16.px)
                            paddingBottom(8.px)
                            property("border-bottom", "1px solid #666666")
                        }
                    }) {
                        H2 {
                            Text(title)
                        }
                    }
                } else {
                    Header(attrs = {
                        style {
                            marginBottom(16.px)
                        }
                    }) {
                        H2(attrs = {
                            style {
                                fontSize(24.px)
                                fontWeight("bold")
                                margin(0.px)
                            }
                        }) {
                            Text(title)
                        }
                    }
                }
            }

            content()
        }
    }
}

@Composable
fun NavigationMenu(navigation: Navigation? = null) {
    Nav(attrs = {
        style {
            position(Position.Fixed)
            top(0.px)
            left(0.px)
            right(0.px)
            backgroundColor(Color("#56b2f0"))
            property("z-index", "100")
        }
    }) {
        Div(attrs = {
            style {
                display(DisplayStyle.Flex)
                alignItems(AlignItems.Center)
                padding(0.px, 16.px)
                maxWidth(1200.px)
                property("margin", "0 auto")
                height(56.px)
            }
        }) {
            A(attrs = {
                style {
                    color(Color.white)
                    fontWeight("bold")
                    textDecoration("none")
                    marginRight(24.px)
                }
            }) {
                Text(navigation?.brand ?: "Prisma-Software")
            }

            NavItems(
                items = navigation?.items ?: listOf("Home", "News", "Blog", "Company")
            )
        }
    }
}

@Composable
fun NavItems(items: List<String>) {
    Ul(attrs = {
        style {
            display(DisplayStyle.Flex)
            listStyleType("none")
            margin(0.px)
            padding(0.px)
        }
    }) {
        items.forEach { item ->
            Li(attrs = {
                style {
                    margin(0.px, 4.px)
                }
            }) {
                A(attrs = {
                    style {
                        color(Color.white)
                        textDecoration("none")
                        padding(8.px, 16.px)
                        display(DisplayStyle.Block)
                        backgroundColor(Color("#7112a1"))
                        borderRadius(4.px)
                    }
                }) {
                    Text(item)
                }
            }
        }
    }
}

@Composable
fun HeroContent(
    title: String,
    description: String,
    buttonText: String
) {
    H1(attrs = {
        style {
            fontSize(36.px)
            fontWeight("400")
            marginTop(2.cssRem)
            marginBottom(16.px)
        }
    }) {
        Text(title)
    }

    P(attrs = {
        style {
            fontSize(18.px)
            lineHeight("1.6")
            marginBottom(24.px)
        }
    }) {
        Text(description)
    }

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
        Text(buttonText)
    }
}

@Composable
fun ExpertiseItem(
    title: String,
    description: String
) {
    Div(attrs = {
        style {
            padding(16.px)
            backgroundColor(rgba(255, 255, 255, 0.1))
            borderRadius(4.px)
            height(100.percent)
        }
    }) {
        H3(attrs = {
            style {
                fontSize(20.px)
                marginBottom(8.px)
                color(Color.white)
            }
        }) {
            Text(title)
        }

        P(attrs = {
            style {
                margin(0.px)
                lineHeight("1.5")
            }
        }) {
            Text(description)
        }
    }
}

@Composable
fun KeywordStrip(keywords: List<String>) {
    Div(attrs = {
        style {
            padding(16.px)
            backgroundColor(rgba(255, 255, 255, 0.1))
            borderRadius(4.px)
            textAlign("center")
        }
    }) {
        P(attrs = {
            style {
                marginTop(16.px)
                marginBottom(16.px)
                lineHeight("1.8")
            }
        }) {
            keywords.forEachIndexed { index, keyword ->
                Text(keyword)
                if (index < keywords.size - 1) {
                    Span(attrs = {
                        style {
                            margin(0.px, 8.px)
                        }
                    }) {
                        Text("•")
                    }
                }
            }
        }
    }
}

@Composable
fun ReasonItem(
    title: String,
    description: String
) {
    Div(attrs = {
        style {
            padding(16.px)
            backgroundColor(rgba(255, 255, 255, 0.1))
            borderRadius(4.px)
        }
    }) {
        Span(attrs = {
            style {
                fontWeight("bold")
                display(DisplayStyle.Block)
                marginBottom(8.px)
            }
        }) {
            Text(title)
        }

        Text(description)
    }
}

data class Update(
    val title: String,
    val author: String,
    val date: String
)

@Composable
fun UpdatesList(updates: List<Update>) {
    Ul(attrs = {
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            gap(16.px)
            listStyleType("none")
            padding(0.px)
            margin(0.px)
        }
    }) {
        updates.forEach { update ->
            Li(attrs = {
                style {
                    padding(8.px, 0.px)
                    property("border-bottom", "1px solid rgba(255, 255, 255, 0.1)")
                }
            }) {
                A(attrs = {
                    style {
                        color(Color.white)
                        textDecoration("none")
                        fontSize(18.px)
                        display(DisplayStyle.Block)
                        marginBottom(4.px)
                    }
                }) {
                    Text(update.title)
                }

                Div(attrs = {
                    style {
                        fontSize(14.px)
                        color(rgba(255, 255, 255, 0.7))
                    }
                }) {
                    Text("by ${update.author} • ${update.date}")
                }
            }
        }
    }
}

@Composable
fun Footer(footer: Footer? = null) {
    Footer(attrs = {
        style {
            backgroundColor(Color("#101010"))
            padding(32.px, 0.px)
            color(Color("#cccccc"))
            textAlign("center")
            marginTop(32.px)
        }
    }) {
        Div(attrs = {
            style {
                maxWidth(1200.px)
                property("margin", "0 auto")
                padding(0.px, 16.px)
            }
        }) {
            P {
                Text(footer?.copyright ?: "Prisma-Software © 2024, All rights reserved.")
            }

            P {
                val links = footer?.links ?: listOf("Privacy Policy", "Terms of Service")
                links.forEachIndexed { index, link ->
                    A(attrs = {
                        style {
                            color(Color.white)
                            textDecoration("underline")
                        }
                    }) {
                        Text(link)
                    }

                    if (index < links.size - 1) {
                        Text(" | ")
                    }
                }
            }
        }
    }
}
