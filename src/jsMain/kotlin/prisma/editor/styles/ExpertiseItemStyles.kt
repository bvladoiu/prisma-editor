package prisma.editor.styles

import org.jetbrains.compose.web.css.*

object ExpertiseItemStyles {
    val container: StyleBuilder.() -> Unit = {
        padding(Theme.Spacing.md)
        backgroundColor(Theme.Colors.lightTransparent)
        borderRadius(Theme.Spacing.borderRadius)
        height(Theme.Spacing.fullWidth)
    }

    val title: StyleBuilder.() -> Unit = {
        with(Theme.Typography) {
            fontSize(fontMd)
            fontFamily(defaultFontFamily)
        }
        marginBottom(Theme.Spacing.sm)
        color(Theme.Colors.white)
    }

    val description: StyleBuilder.() -> Unit = {
        margin(Theme.Spacing.none)
        with(Theme.Typography) {
            lineHeight(lineHeightNormal)
            fontFamily(defaultFontFamily)
        }
    }
}
