package prisma.editor.composables.component

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import prisma.editor.Footer as FooterData

@Composable
fun Footer(footer: FooterData? = null) {
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