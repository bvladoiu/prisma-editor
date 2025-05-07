package prisma.editor.styles

import kotlinx.browser.document
import org.w3c.dom.HTMLElement
import org.w3c.dom.css.CSSStyleSheet

/**
 * Styles for the Expertise component.
 * Uses CSSOM APIs targeting the 'expertise' attribute.
 */
object ExpertiseStyles {
    // Style methods for direct manipulation (legacy support)
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

    // CSS Stylesheet for the Expertise component using CSSOM APIs
    val stylesheet: String get() = """
        [expertise] {
            padding: ${Theme.Spacing.md};
            background-color: ${Theme.Colors.lightTransparent};
            border-radius: ${Theme.Spacing.borderRadius};
            height: ${Theme.Spacing.fullWidth};
        }

        [expertise] h3 {
            font-size: ${Theme.Typography.fontMd};
            font-family: ${Theme.Typography.defaultFontFamily};
            margin-bottom: ${Theme.Spacing.sm};
            color: ${Theme.Colors.white};
        }

        [expertise] p {
            margin: ${Theme.Spacing.none};
            line-height: ${Theme.Typography.lineHeightNormal};
            font-family: ${Theme.Typography.defaultFontFamily};
        }
    """.trimIndent()

    /**
     * Creates and adds a stylesheet for the Expertise component to the document.
     * This method can be used to add the stylesheet to the document.
     */
    fun addStyleSheetToDocument() {
        val styleElement = document.createElement("style")
        document.head?.appendChild(styleElement)
        styleElement.textContent = stylesheet
    }
}
