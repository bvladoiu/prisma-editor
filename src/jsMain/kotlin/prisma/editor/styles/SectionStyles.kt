package prisma.editor.styles

import org.w3c.dom.HTMLElement

object SectionStyles {
    fun applyContainerStyle(element: HTMLElement, marginTop: String) {
        element.style.apply {
            this.marginTop = marginTop
            marginBottom = Theme.Spacing.rem2
            padding = "${Theme.Spacing.none} ${Theme.Spacing.md}"
            maxWidth = Theme.Spacing.maxContentWidth
            margin = "0 auto"
        }
    }

    fun applyArticleStyle(element: HTMLElement) {
        element.style.apply {
            padding = Theme.Spacing.md
            backgroundColor = Theme.Colors.veryLightTransparent
            borderRadius = Theme.Spacing.borderRadius
        }
    }

    fun applyHeaderWithDividerStyle(element: HTMLElement) {
        element.style.apply {
            fontSize = Theme.Typography.fontLg
            fontWeight = Theme.Typography.fontWeightBold
            fontFamily = Theme.Typography.defaultFontFamily
            marginBottom = Theme.Spacing.md
            paddingBottom = Theme.Spacing.sm
            borderBottom = "1px solid ${Theme.Colors.mediumGray}"
        }
    }

    fun applyHeaderWithoutDividerStyle(element: HTMLElement) {
        element.style.apply {
            marginBottom = Theme.Spacing.md
        }
    }

    fun applyHeadingStyle(element: HTMLElement) {
        element.style.apply {
            fontSize = Theme.Typography.fontLg
            fontWeight = Theme.Typography.fontWeightBold
            fontFamily = Theme.Typography.defaultFontFamily
            margin = Theme.Spacing.none
        }
    }
}
