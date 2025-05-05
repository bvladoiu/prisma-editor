package prisma.editor.styles

import org.jetbrains.compose.web.css.*

object KeywordStripStyles {
    val container: StyleBuilder.() -> Unit = {
        padding(Theme.Spacing.md)
        backgroundColor(Theme.Colors.lightTransparent)
        borderRadius(Theme.Spacing.borderRadius)
        textAlign("center")
        fontFamily(Theme.Typography.defaultFontFamily)
    }

    val paragraph: StyleBuilder.() -> Unit = {
        marginTop(Theme.Spacing.md)
        marginBottom(Theme.Spacing.md)
        with(Theme.Typography) {
            lineHeight(lineHeightXLarge)
            fontFamily(defaultFontFamily)
        }
    }

    val separator: StyleBuilder.() -> Unit = {
        margin(Theme.Spacing.none, Theme.Spacing.sm)
    }
}
