package prisma.editor

import androidx.compose.runtime.*
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import prisma.editor.composables.pages.Home

object Editor {
    fun openPage(name: String) {
        renderComposable(rootElementId = "root") {
            EditorScaffold {
                when (name.lowercase()) {
                    "home" -> HomePage()
                    else -> Text("Page not found")
                }
            }
        }
    }
}

@Composable
fun EditorScaffold(content: @Composable () -> Unit) {
    var isDrawerOpen by remember { mutableStateOf(false) }

    Div(attrs = {
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            height(100.vh)
            width(100.percent)
        }
    }) {
        // AppBar
        AppBar(
            isDrawerOpen = isDrawerOpen,
            onMenuClick = { isDrawerOpen = !isDrawerOpen }
        )

        // Content area with drawer
        Div(attrs = {
            style {
                display(DisplayStyle.Flex)
                flexGrow(1)
                overflow("hidden")
            }
        }) {
            // Drawer
            if (isDrawerOpen) {
                Drawer(
                    onNavigate = { route ->
                        isDrawerOpen = false
                        Editor.openPage(route)
                    }
                )
            }

            // Main content
            Div(attrs = {
                style {
                    flexGrow(1)
                    padding(16.px)
                    overflow("auto")
                }
            }) {
                content()
            }
        }
    }
}

@Composable
fun AppBar(isDrawerOpen: Boolean, onMenuClick: () -> Unit) {
    Header(attrs = {
        style {
            display(DisplayStyle.Flex)
            alignItems(AlignItems.Center)
            padding(8.px, 16.px)
            backgroundColor(Color("#6200EE"))
            color(Color.white)
            height(56.px)
            property("box-shadow", "0 2px 4px rgba(0,0,0,0.2)")
        }
    }) {
        // Menu button
        Button(attrs = {
            style {
                backgroundColor(Color.transparent)
                border(0.px)
                color(Color.white)
                cursor("pointer")
                padding(8.px)
                marginRight(16.px)
            }
            onClick { onMenuClick() }
        }) {
            if (isDrawerOpen) {
                // Close icon (simplified)
                Text("✕") // Unicode X symbol
            } else {
                // Menu icon (simplified)
                Div(attrs = {
                    style {
                        width(24.px)
                        height(3.px)
                        backgroundColor(Color.white)
                        marginBottom(5.px)
                    }
                })
                Div(attrs = {
                    style {
                        width(24.px)
                        height(3.px)
                        backgroundColor(Color.white)
                        marginBottom(5.px)
                    }
                })
                Div(attrs = {
                    style {
                        width(24.px)
                        height(3.px)
                        backgroundColor(Color.white)
                    }
                })
            }
        }

        // Title
        H1(attrs = {
            style {
                margin(0.px)
                fontSize(20.px)
                fontWeight("500")
            }
        }) {
            Text("Prisma Editor")
        }
    }
}

@Composable
fun Drawer(onNavigate: (String) -> Unit) {
    Nav(attrs = {
        style {
            width(240.px)
            height(100.percent)
            backgroundColor(Color.white)
            property("box-shadow", "2px 0 4px rgba(0,0,0,0.2)")
            overflow("auto")
        }
    }) {
        // Drawer header
        Header(attrs = {
            style {
                padding(16.px)
                backgroundColor(Color("#7D3DF3"))
                color(Color.white)
            }
        }) {
            H2(attrs = {
                style {
                    margin(0.px)
                    fontSize(18.px)
                }
            }) {
                Text("Navigation")
            }
        }

        // Drawer items
        Ul(attrs = {
            style {
                listStyleType("none")
                padding(0.px)
                margin(0.px)
            }
        }) {
            DrawerItem("Home", onClick = { onNavigate("home") })
        }
    }
}

@Composable
fun DrawerItem(text: String, onClick: () -> Unit) {
    Li(attrs = {
        style {
            padding(0.px)
            margin(0.px)
        }
    }) {
        A(attrs = {
            style {
                padding(16.px)
                cursor("pointer")
                backgroundColor(Color("#FFFFFF"))
                display(DisplayStyle.Block)
                textDecoration("none")
                color(Color.black)
                // Simple styling without hover effects
            }
            onClick { onClick() }
        }) {
            Text(text)
        }
    }
}

@Composable
fun HomePage() {
    Home()
}

fun main() {
    window.onload = {
        Editor.openPage("home")
    }
}
