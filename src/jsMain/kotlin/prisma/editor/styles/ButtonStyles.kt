package prisma.editor.styles

import org.w3c.dom.HTMLElement

object ButtonStyles {
    fun applyPrimaryStyle(element: HTMLElement) {
        element.style.apply {
            backgroundColor = Theme.Colors.secondary
            color = Theme.Colors.primary
            padding = "${Theme.Spacing.sm} ${Theme.Spacing.md}"
            border = Theme.Spacing.none
            borderRadius = Theme.Spacing.borderRadius
            cursor = "pointer"
            fontFamily = Theme.Typography.defaultFontFamily
            fontWeight = Theme.Typography.fontWeightBold
        }
    }
}
