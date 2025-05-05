package prisma.editor.styles

import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.StyleScope

object ExpertiseItemStyles {
    val container: StyleScope.() -> Unit = {
        padding(Theme.Spacing.md)
        backgroundColor(Theme.Colors.lightTransparent)
        borderRadius(Theme.Spacing.borderRadius)
        height(Theme.Spacing.fullWidth)
    }

    val title: StyleScope.() -> Unit = {
        with(Theme.Typography) {
            fontSize(fontMd)
            fontFamily(defaultFontFamily)
        }
        marginBottom(Theme.Spacing.sm)
        color(Theme.Colors.white)
    }

    val description: StyleScope.() -> Unit = {
        margin(Theme.Spacing.none)
        with(Theme.Typography) {
            lineHeight(lineHeightNormal)
            fontFamily(defaultFontFamily)
        }
    }
}
