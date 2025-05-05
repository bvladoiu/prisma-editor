package prisma.editor.composables.component

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

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