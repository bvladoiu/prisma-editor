package prisma.editor.composables.component

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import prisma.editor.styles.FooterStyles
import prisma.editor.model.Footer as FooterModel

@Composable
fun Footer(footer: FooterModel? = null) {
    Footer(attrs = {
        style(FooterStyles.container)
    }) {
        Div(attrs = {
            style(FooterStyles.content)
        }) {
            P {
                Text(footer?.copyright ?: "Prisma-Software © 2024, All rights reserved.")
            }

            P {
                val links = footer?.links ?: listOf("Privacy Policy", "Terms of Service")
                links.forEachIndexed { index, link ->
                    A(attrs = {
                        style(FooterStyles.link)
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
