package prisma.editor.styles

import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.StyleScope

object FooterStyles {
    val container: StyleScope.() -> Unit = {
        backgroundColor(Theme.Colors.darkBackground)
        padding(Theme.Spacing.xl, Theme.Spacing.none)
        color(Theme.Colors.lightGray)
        textAlign("center")
        marginTop(Theme.Spacing.xl)
        fontFamily(Theme.Typography.defaultFontFamily)
    }

    val content: StyleScope.() -> Unit = {
        maxWidth(Theme.Spacing.maxContentWidth)
        property("margin", "0 auto")
        padding(Theme.Spacing.none, Theme.Spacing.md)
    }

    val link: StyleScope.() -> Unit = {
        color(Theme.Colors.white)
        textDecoration("underline")
        fontFamily(Theme.Typography.defaultFontFamily)
    }
}
