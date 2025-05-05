package prisma.editor.styles

import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.StyleScope

object UpdatesListStyles {
    val list: StyleScope.() -> Unit = {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        gap(Theme.Spacing.md)
        listStyleType("none")
        padding(Theme.Spacing.none)
        margin(Theme.Spacing.none)
    }

    val listItem: StyleScope.() -> Unit = {
        padding(Theme.Spacing.sm, Theme.Spacing.none)
        property("border-bottom", "1px solid ${Theme.Colors.lightTransparent}")
        fontFamily(Theme.Typography.defaultFontFamily)
    }

    val updateLink: StyleScope.() -> Unit = {
        color(Theme.Colors.white)
        textDecoration("none")
        with(Theme.Typography) {
            fontSize(fontSm)
            fontFamily(defaultFontFamily)
        }
        display(DisplayStyle.Block)
        marginBottom(Theme.Spacing.xs)
    }

    val metadata: StyleScope.() -> Unit = {
        with(Theme.Typography) {
            fontSize(fontXs)
            fontFamily(defaultFontFamily)
        }
        color(Theme.Colors.mediumTransparent)
    }
}
