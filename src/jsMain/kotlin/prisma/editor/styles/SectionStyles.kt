package prisma.editor.styles

import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.StyleScope

object SectionStyles {
    fun container(marginTop: CSSNumeric): StyleScope.() -> Unit = {
        marginTop(marginTop)
        marginBottom(Theme.Spacing.rem2)
        padding(Theme.Spacing.none, Theme.Spacing.md)
        maxWidth(Theme.Spacing.maxContentWidth)
        property("margin", "0 auto")
    }

    val article: StyleScope.() -> Unit = {
        padding(Theme.Spacing.md)
        backgroundColor(Theme.Colors.veryLightTransparent)
        borderRadius(Theme.Spacing.borderRadius)
    }

    val headerWithDivider: StyleScope.() -> Unit = {
        with(Theme.Typography) {
            fontSize(fontLg)
            fontWeight(fontWeightBold)
            fontFamily(defaultFontFamily)
        }
        marginBottom(Theme.Spacing.md)
        paddingBottom(Theme.Spacing.sm)
        property("border-bottom", "1px solid ${Theme.Colors.mediumGray}")
    }

    val headerWithoutDivider: StyleScope.() -> Unit = {
        marginBottom(Theme.Spacing.md)
    }

    val heading: StyleScope.() -> Unit = {
        with(Theme.Typography) {
            fontSize(fontLg)
            fontWeight(fontWeightBold)
            fontFamily(defaultFontFamily)
        }
        margin(Theme.Spacing.none)
    }
}
