package prisma.editor.composables.component

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

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