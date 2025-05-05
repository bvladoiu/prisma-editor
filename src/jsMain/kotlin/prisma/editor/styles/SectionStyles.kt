package prisma.editor.styles

import org.jetbrains.compose.web.css.*

object SectionStyles {
    fun container(marginTop: CSSNumeric): StyleBuilder.() -> Unit = {
        marginTop(marginTop)
        marginBottom(Theme.Spacing.rem2)
        padding(Theme.Spacing.none, Theme.Spacing.md)
        maxWidth(Theme.Spacing.maxContentWidth)
        property("margin", "0 auto")
    }

    val article: StyleBuilder.() -> Unit = {
        padding(Theme.Spacing.md)
        backgroundColor(Theme.Colors.veryLightTransparent)
        borderRadius(Theme.Spacing.borderRadius)
    }

    val headerWithDivider: StyleBuilder.() -> Unit = {
        with(Theme.Typography) {
            fontSize(fontLg)
            fontWeight(fontWeightBold)
            fontFamily(defaultFontFamily)
        }
        marginBottom(Theme.Spacing.md)
        paddingBottom(Theme.Spacing.sm)
        property("border-bottom", "1px solid ${Theme.Colors.mediumGray}")
    }

    val headerWithoutDivider: StyleBuilder.() -> Unit = {
        marginBottom(Theme.Spacing.md)
    }

    val heading: StyleBuilder.() -> Unit = {
        with(Theme.Typography) {
            fontSize(fontLg)
            fontWeight(fontWeightBold)
            fontFamily(defaultFontFamily)
        }
        margin(Theme.Spacing.none)
    }
}
