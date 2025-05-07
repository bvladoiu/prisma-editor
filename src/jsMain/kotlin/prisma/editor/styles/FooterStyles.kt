package prisma.editor.styles

import org.w3c.dom.HTMLElement

/**
 * Styles for the Footer component.
 */
object FooterStyles {
    // Style methods for direct manipulation (legacy support)
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

    // CSS Stylesheet for the Footer component
    val stylesheet: String get() = """
        [footer] {
            background-color: ${Theme.Colors.darkBackground};
            padding: ${Theme.Spacing.xl} ${Theme.Spacing.none};
            color: ${Theme.Colors.lightGray};
            text-align: center;
            margin-top: ${Theme.Spacing.xl};
            font-family: ${Theme.Typography.defaultFontFamily};
        }

        [footer] .content {
            max-width: ${Theme.Spacing.maxContentWidth};
            margin: 0 auto;
            padding: ${Theme.Spacing.none} ${Theme.Spacing.md};
        }

        [footer] a {
            color: ${Theme.Colors.white};
            text-decoration: underline;
            font-family: ${Theme.Typography.defaultFontFamily};
        }
    """.trimIndent()
}
