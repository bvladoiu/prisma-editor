package prisma.editor.styles

import org.w3c.dom.HTMLElement

/**
 * Styles for the KeywordStrip component.
 */
object KeywordStripStyles {
    // Style methods for direct manipulation (legacy support)
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

    // CSS Stylesheet for the KeywordStrip component
    val stylesheet: String get() = """
        [keyword-strip] {
            display: flex;
            flex-wrap: wrap;
            gap: 8px;
            justify-content: center;
        }

        [keyword-strip] span {
            background-color: rgba(255, 255, 255, 0.1);
            color: #56b2f0;
            padding: 4px 12px;
            border-radius: 16px;
            font-size: 14px;
        }
    """.trimIndent()
}
