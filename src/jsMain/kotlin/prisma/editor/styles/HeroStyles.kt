package prisma.editor.styles

import org.jetbrains.compose.web.css.*

object HeroStyles {
    val title: StyleBuilder.() -> Unit = {
        with(Theme.Typography) {
            fontSize(fontXl)
            fontWeight(fontWeightNormal)
            fontFamily(defaultFontFamily)
        }
        marginTop(Theme.Spacing.rem2)
        marginBottom(Theme.Spacing.md)
    }

    val description: StyleBuilder.() -> Unit = {
        with(Theme.Typography) {
            fontSize(fontSm)
            lineHeight(lineHeightLarge)
            fontFamily(defaultFontFamily)
        }
        marginBottom(Theme.Spacing.lg)
    }
}
