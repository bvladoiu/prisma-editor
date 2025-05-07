package prisma.editor.styles

import org.w3c.dom.HTMLElement

object ExpertiseItemStyles {
    fun applyContainerStyle(element: HTMLElement) {
        element.style.apply {
            padding = Theme.Spacing.md
            backgroundColor = Theme.Colors.lightTransparent
            borderRadius = Theme.Spacing.borderRadius
            height = Theme.Spacing.fullWidth
        }
    }

    fun applyTitleStyle(element: HTMLElement) {
        element.style.apply {
            fontSize = Theme.Typography.fontMd
            fontFamily = Theme.Typography.defaultFontFamily
            marginBottom = Theme.Spacing.sm
            color = Theme.Colors.white
        }
    }

    fun applyDescriptionStyle(element: HTMLElement) {
        element.style.apply {
            margin = Theme.Spacing.none
            lineHeight = Theme.Typography.lineHeightNormal
            fontFamily = Theme.Typography.defaultFontFamily
        }
    }
}
