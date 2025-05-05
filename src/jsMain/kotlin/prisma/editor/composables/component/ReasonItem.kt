package prisma.editor.composables.component

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

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