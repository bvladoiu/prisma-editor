package prisma.editor.composables.component

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import prisma.editor.styles.ReasonItemStyles

@Composable
fun ReasonItem(
    title: String,
    description: String
) {
    Div(attrs = {
        style(ReasonItemStyles.container)
    }) {
        Span(attrs = {
            style(ReasonItemStyles.title)
        }) {
            Text(title)
        }

        Text(description)
    }
}
