package prisma.editor.styles

import org.w3c.dom.HTMLElement

/**
 * Styles for the Hero component.
 */
object HeroStyles {
    // Style methods for direct manipulation (legacy support)
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

    // CSS Stylesheet for the Hero component
    val stylesheet: String get() = """
        [hero] {
            text-align: center;
            padding: ${Theme.Spacing.xl} ${Theme.Spacing.md};
        }

        [hero] h1 {
            font-size: ${Theme.Typography.fontXl};
            font-weight: ${Theme.Typography.fontWeightNormal};
            font-family: ${Theme.Typography.defaultFontFamily};
            margin-top: ${Theme.Spacing.rem2};
            margin-bottom: ${Theme.Spacing.md};
            color: ${Theme.Colors.primary};
        }

        [hero] p {
            font-size: ${Theme.Typography.fontSm};
            line-height: ${Theme.Typography.lineHeightLarge};
            font-family: ${Theme.Typography.defaultFontFamily};
            margin-bottom: ${Theme.Spacing.lg};
            max-width: 600px;
            margin-left: auto;
            margin-right: auto;
        }

        [hero] button {
            background-color: ${Theme.Colors.secondary};
            color: ${Theme.Colors.white};
            padding: ${Theme.Spacing.sm} ${Theme.Spacing.md};
            border: ${Theme.Spacing.none};
            border-radius: ${Theme.Spacing.borderRadius};
            font-size: ${Theme.Typography.fontSm};
            cursor: pointer;
        }
    """.trimIndent()
}
