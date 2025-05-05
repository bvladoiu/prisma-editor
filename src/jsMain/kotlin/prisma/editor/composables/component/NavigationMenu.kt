package prisma.editor.composables.component

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import prisma.editor.Navigation

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