package prisma.editor.composables.component

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import prisma.editor.styles.KeywordStripStyles

@Composable
fun KeywordStrip(keywords: List<String>) {
    Div(attrs = {
        style(KeywordStripStyles.container)
    }) {
        P(attrs = {
            style(KeywordStripStyles.paragraph)
        }) {
            keywords.forEachIndexed { index, keyword ->
                Text(keyword)
                if (index < keywords.size - 1) {
                    Span(attrs = {
                        style(KeywordStripStyles.separator)
                    }) {
                        Text("•")
                    }
                }
            }
        }
    }
}
