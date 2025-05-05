package prisma.editor.styles

import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.StyleScope

object ButtonStyles {
    val primary: StyleScope.() -> Unit = {
        backgroundColor(Theme.Colors.secondary)
        color(Theme.Colors.primary)
        padding(Theme.Spacing.sm, Theme.Spacing.md)
        border(Theme.Spacing.none)
        borderRadius(Theme.Spacing.borderRadius)
        cursor("pointer")
        fontFamily(Theme.Typography.defaultFontFamily)
        fontWeight(Theme.Typography.fontWeightBold)
    }
}
