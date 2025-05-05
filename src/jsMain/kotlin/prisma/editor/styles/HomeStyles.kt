package prisma.editor.styles

import org.jetbrains.compose.web.css.*

object HomeStyles {
    val container: StyleBuilder.() -> Unit = {
        backgroundColor(Theme.Colors.primary)
        color(Theme.Colors.white)
        fontFamily(Theme.Typography.defaultFontFamily)
        margin(Theme.Spacing.none)
        padding(Theme.Spacing.none)
    }

    val loadingContainer: StyleBuilder.() -> Unit = {
        display(DisplayStyle.Flex)
        justifyContent(JustifyContent.Center)
        alignItems(AlignItems.Center)
        height(Theme.Spacing.fullHeight)
    }

    val errorContainer: StyleBuilder.() -> Unit = {
        display(DisplayStyle.Flex)
        justifyContent(JustifyContent.Center)
        alignItems(AlignItems.Center)
        height(Theme.Spacing.fullHeight)
        color(Theme.Colors.red)
        fontFamily(Theme.Typography.defaultFontFamily)
    }

    val mainContent: StyleBuilder.() -> Unit = {
        marginTop(Theme.Spacing.rem5)
        marginBottom(Theme.Spacing.rem2)
    }

    fun sectionItemsGrid(minWidth: String): StyleBuilder.() -> Unit = {
        display(DisplayStyle.Grid)
        property("grid-template-columns", "repeat(auto-fit, minmax($minWidth, 1fr))")
        gap(Theme.Spacing.md)
    }

    val buttonContainer: StyleBuilder.() -> Unit = {
        marginTop(Theme.Spacing.rem2)
    }

    val button: StyleBuilder.() -> Unit = {
        backgroundColor(Theme.Colors.secondary)
        color(Theme.Colors.primary)
        padding(Theme.Spacing.sm, Theme.Spacing.md)
        border(Theme.Spacing.none)
        borderRadius(Theme.Spacing.borderRadius)
        cursor("pointer")
        fontFamily(Theme.Typography.defaultFontFamily)
    }
}
