package prisma.editor.styles

import org.w3c.dom.HTMLElement

object ReasonItemStyles {
    fun applyContainerStyle(element: HTMLElement) {
        element.style.apply {
            padding = Theme.Spacing.md
            backgroundColor = Theme.Colors.lightTransparent
            borderRadius = Theme.Spacing.borderRadius
            fontFamily = Theme.Typography.defaultFontFamily
        }
    }

    fun applyTitleStyle(element: HTMLElement) {
        element.style.apply {
            fontWeight = Theme.Typography.fontWeightBold
            fontFamily = Theme.Typography.defaultFontFamily
            display = "block"
            marginBottom = Theme.Spacing.sm
        }
    }
}
