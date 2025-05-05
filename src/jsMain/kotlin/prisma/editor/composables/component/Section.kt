package prisma.editor.composables.component

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Composable
fun Section(
    title: String?,
    isDivider: Boolean = false,
    marginTop: CSSNumeric = 2.cssRem,
    content: @Composable () -> Unit
) {
    Section(attrs = {
        style {
            marginTop(marginTop)
            marginBottom(2.cssRem)
            padding(0.px, 16.px)
            maxWidth(1200.px)
            property("margin", "0 auto")
        }
    }) {
        Article(attrs = {
            style {
                padding(16.px)
                backgroundColor(rgba(255, 255, 255, 0.03))
                borderRadius(4.px)
            }
        }) {
            if (title != null) {
                if (isDivider) {
                    Header(attrs = {
                        style {
                            fontSize(24.px)
                            fontWeight("bold")
                            marginBottom(16.px)
                            paddingBottom(8.px)
                            property("border-bottom", "1px solid #666666")
                        }
                    }) {
                        H2 {
                            Text(title)
                        }
                    }
                } else {
                    Header(attrs = {
                        style {
                            marginBottom(16.px)
                        }
                    }) {
                        H2(attrs = {
                            style {
                                fontSize(24.px)
                                fontWeight("bold")
                                margin(0.px)
                            }
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