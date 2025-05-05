package prisma.editor.styles

import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.StyleScope

object KeywordStripStyles {
    val container: StyleScope.() -> Unit = {
        padding(Theme.Spacing.md)
        backgroundColor(Theme.Colors.lightTransparent)
        borderRadius(Theme.Spacing.borderRadius)
        textAlign("center")
        fontFamily(Theme.Typography.defaultFontFamily)
    }

    val paragraph: StyleScope.() -> Unit = {
        marginTop(Theme.Spacing.md)
        marginBottom(Theme.Spacing.md)
        with(Theme.Typography) {
            lineHeight(lineHeightXLarge)
            fontFamily(defaultFontFamily)
        }
    }

    val separator: StyleScope.() -> Unit = {
        margin(Theme.Spacing.none, Theme.Spacing.sm)
    }
}
