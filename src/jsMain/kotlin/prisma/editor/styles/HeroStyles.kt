package prisma.editor.styles

import org.w3c.dom.HTMLElement

object HeroStyles {
    fun applyContainerStyle(element: HTMLElement) {
        element.style.apply {
            textAlign = "center"
            padding = "${Theme.Spacing.xl} ${Theme.Spacing.md}"
        }
    }

    fun applyTitleStyle(element: HTMLElement) {
        element.style.apply {
            fontSize = Theme.Typography.fontXl
            fontWeight = Theme.Typography.fontWeightNormal
            fontFamily = Theme.Typography.defaultFontFamily
            marginTop = Theme.Spacing.rem2
            marginBottom = Theme.Spacing.md
            color = Theme.Colors.primary
        }
    }

    fun applyDescriptionStyle(element: HTMLElement) {
        element.style.apply {
            fontSize = Theme.Typography.fontSm
            lineHeight = Theme.Typography.lineHeightLarge
            fontFamily = Theme.Typography.defaultFontFamily
            marginBottom = Theme.Spacing.lg
            maxWidth = "600px"
            marginLeft = "auto"
            marginRight = "auto"
        }
    }

    fun applyButtonStyle(element: HTMLElement) {
        element.style.apply {
            backgroundColor = Theme.Colors.secondary
            color = Theme.Colors.white
            padding = "${Theme.Spacing.sm} ${Theme.Spacing.md}"
            border = Theme.Spacing.none
            borderRadius = Theme.Spacing.borderRadius
            fontSize = Theme.Typography.fontSm
            cursor = "pointer"
        }
    }
}
