package prisma.editor.styles

import org.jetbrains.compose.web.css.*

object ReasonItemStyles {
    val container: StyleBuilder.() -> Unit = {
        padding(Theme.Spacing.md)
        backgroundColor(Theme.Colors.lightTransparent)
        borderRadius(Theme.Spacing.borderRadius)
        fontFamily(Theme.Typography.defaultFontFamily)
    }

    val title: StyleBuilder.() -> Unit = {
        with(Theme.Typography) {
            fontWeight(fontWeightBold)
            fontFamily(defaultFontFamily)
        }
        display(DisplayStyle.Block)
        marginBottom(Theme.Spacing.sm)
    }
}
