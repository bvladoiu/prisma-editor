package prisma.editor.styles

import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.StyleScope

object NavigationMenuStyles {
    val container: StyleScope.() -> Unit = {
        position(Position.Fixed)
        top(Theme.Spacing.none)
        left(Theme.Spacing.none)
        right(Theme.Spacing.none)
        backgroundColor(Theme.Colors.primary)
        property("z-index", "100")
    }

    val content: StyleScope.() -> Unit = {
        display(DisplayStyle.Flex)
        alignItems(AlignItems.Center)
        padding(Theme.Spacing.none, Theme.Spacing.md)
        maxWidth(Theme.Spacing.maxContentWidth)
        property("margin", "0 auto")
        height(Theme.Spacing.navHeight)
    }

    val brand: StyleScope.() -> Unit = {
        color(Theme.Colors.white)
        with(Theme.Typography) {
            fontWeight(fontWeightBold)
            fontFamily(defaultFontFamily)
        }
        textDecoration("none")
        marginRight(Theme.Spacing.lg)
    }

    val navList: StyleScope.() -> Unit = {
        display(DisplayStyle.Flex)
        listStyleType("none")
        margin(Theme.Spacing.none)
        padding(Theme.Spacing.none)
    }

    val navItem: StyleScope.() -> Unit = {
        margin(Theme.Spacing.none, Theme.Spacing.xs)
    }

    val navLink: StyleScope.() -> Unit = {
        color(Theme.Colors.white)
        textDecoration("none")
        padding(Theme.Spacing.sm, Theme.Spacing.md)
        display(DisplayStyle.Block)
        backgroundColor(Theme.Colors.secondary)
        borderRadius(Theme.Spacing.borderRadius)
        fontFamily(Theme.Typography.defaultFontFamily)
    }
}
