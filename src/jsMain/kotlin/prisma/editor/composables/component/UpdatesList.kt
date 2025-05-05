package prisma.editor.composables.component

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

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