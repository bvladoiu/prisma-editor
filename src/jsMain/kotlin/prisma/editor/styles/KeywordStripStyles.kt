package prisma.editor.styles

import org.w3c.dom.HTMLElement

object KeywordStripStyles {
    fun applyContainerStyle(element: HTMLElement) {
        element.style.apply {
            padding = Theme.Spacing.md
            backgroundColor = Theme.Colors.lightTransparent
            borderRadius = Theme.Spacing.borderRadius
            textAlign = "center"
            fontFamily = Theme.Typography.defaultFontFamily
        }
    }

    fun applyParagraphStyle(element: HTMLElement) {
        element.style.apply {
            marginTop = Theme.Spacing.md
            marginBottom = Theme.Spacing.md
            lineHeight = Theme.Typography.lineHeightXLarge
            fontFamily = Theme.Typography.defaultFontFamily
        }
    }

    fun applySeparatorStyle(element: HTMLElement) {
        element.style.apply {
            margin = "${Theme.Spacing.none} ${Theme.Spacing.sm}"
        }
    }
}
