package prisma.editor.styles

import org.w3c.dom.HTMLElement

object FooterStyles {
    fun applyContainerStyle(element: HTMLElement) {
        element.style.apply {
            backgroundColor = Theme.Colors.darkBackground
            padding = "${Theme.Spacing.xl} ${Theme.Spacing.none}"
            color = Theme.Colors.lightGray
            textAlign = "center"
            marginTop = Theme.Spacing.xl
            fontFamily = Theme.Typography.defaultFontFamily
        }
    }

    fun applyContentStyle(element: HTMLElement) {
        element.style.apply {
            maxWidth = Theme.Spacing.maxContentWidth
            margin = "0 auto"
            padding = "${Theme.Spacing.none} ${Theme.Spacing.md}"
        }
    }

    fun applyLinkStyle(element: HTMLElement) {
        element.style.apply {
            color = Theme.Colors.white
            textDecoration = "underline"
            fontFamily = Theme.Typography.defaultFontFamily
        }
    }
}
