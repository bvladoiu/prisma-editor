package prisma.editor.composables.component

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import prisma.editor.styles.UpdatesListStyles
import prisma.editor.model.Update

@Composable
fun UpdatesList(updates: List<Update>) {
    Ul(attrs = {
        style(UpdatesListStyles.list)
    }) {
        updates.forEach { update ->
            Li(attrs = {
                style(UpdatesListStyles.listItem)
            }) {
                A(attrs = {
                    style(UpdatesListStyles.updateLink)
                }) {
                    Text(update.title)
                }

                Div(attrs = {
                    style(UpdatesListStyles.metadata)
                }) {
                    Text("by ${update.author} • ${update.date}")
                }
            }
        }
    }
}
