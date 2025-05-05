package prisma.editor.composables.component

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import prisma.editor.styles.ExpertiseItemStyles

@Composable
fun ExpertiseItem(
    title: String,
    description: String
) {
    Div(attrs = {
        style(ExpertiseItemStyles.container)
    }) {
        H3(attrs = {
            style(ExpertiseItemStyles.title)
        }) {
            Text(title)
        }

        P(attrs = {
            style(ExpertiseItemStyles.description)
        }) {
            Text(description)
        }
    }
}
