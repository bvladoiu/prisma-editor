package prisma.editor.composables.component

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import prisma.editor.styles.SectionStyles

@Composable
fun Section(
    title: String?,
    isDivider: Boolean = false,
    marginTop: CSSNumeric = 2.cssRem,
    content: @Composable () -> Unit
) {
    Section(attrs = {
        style(SectionStyles.container(marginTop))
    }) {
        Article(attrs = {
            style(SectionStyles.article)
        }) {
            if (title != null) {
                if (isDivider) {
                    Header(attrs = {
                        style(SectionStyles.headerWithDivider)
                    }) {
                        H2 {
                            Text(title)
                        }
                    }
                } else {
                    Header(attrs = {
                        style(SectionStyles.headerWithoutDivider)
                    }) {
                        H2(attrs = {
                            style(SectionStyles.heading)
                        }) {
                            Text(title)
                        }
                    }
                }
            }

            content()
        }
    }
}
