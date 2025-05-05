package prisma.editor.styles

import org.jetbrains.compose.web.css.*

object UpdatesListStyles {
    val list: StyleBuilder.() -> Unit = {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        gap(Theme.Spacing.md)
        listStyleType("none")
        padding(Theme.Spacing.none)
        margin(Theme.Spacing.none)
    }

    val listItem: StyleBuilder.() -> Unit = {
        padding(Theme.Spacing.sm, Theme.Spacing.none)
        property("border-bottom", "1px solid ${Theme.Colors.lightTransparent}")
        fontFamily(Theme.Typography.defaultFontFamily)
    }

    val updateLink: StyleBuilder.() -> Unit = {
        color(Theme.Colors.white)
        textDecoration("none")
        with(Theme.Typography) {
            fontSize(fontSm)
            fontFamily(defaultFontFamily)
        }
        display(DisplayStyle.Block)
        marginBottom(Theme.Spacing.xs)
    }

    val metadata: StyleBuilder.() -> Unit = {
        with(Theme.Typography) {
            fontSize(fontXs)
            fontFamily(defaultFontFamily)
        }
        color(Theme.Colors.mediumTransparent)
    }
}
