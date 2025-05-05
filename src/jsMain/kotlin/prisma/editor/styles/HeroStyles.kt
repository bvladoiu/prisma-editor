package prisma.editor.styles

import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.StyleScope

object HeroStyles {
    val title: StyleScope.() -> Unit = {
        with(Theme.Typography) {
            fontSize(fontXl)
            fontWeight(fontWeightNormal)
            fontFamily(defaultFontFamily)
        }
        marginTop(Theme.Spacing.rem2)
        marginBottom(Theme.Spacing.md)
    }

    val description: StyleScope.() -> Unit = {
        with(Theme.Typography) {
            fontSize(fontSm)
            lineHeight(lineHeightLarge)
            fontFamily(defaultFontFamily)
        }
        marginBottom(Theme.Spacing.lg)
    }
}
