package prisma.editor.styles

import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.StyleScope

object ReasonItemStyles {
    val container: StyleScope.() -> Unit = {
        padding(Theme.Spacing.md)
        backgroundColor(Theme.Colors.lightTransparent)
        borderRadius(Theme.Spacing.borderRadius)
        fontFamily(Theme.Typography.defaultFontFamily)
    }

    val title: StyleScope.() -> Unit = {
        with(Theme.Typography) {
            fontWeight(fontWeightBold)
            fontFamily(defaultFontFamily)
        }
        display(DisplayStyle.Block)
        marginBottom(Theme.Spacing.sm)
    }
}
