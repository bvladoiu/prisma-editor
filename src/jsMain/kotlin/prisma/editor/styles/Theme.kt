package prisma.editor.styles

import kotlinx.browser.document
import org.w3c.dom.css.*
import org.w3c.dom.HTMLElement

/**
 * Theme object containing all colors, spacings, and typography used throughout the application.
 */
object Theme {
    /**
     * Colors used throughout the application.
     */
    object Colors {
        // Primary colors
        val primary = "#56b2f0"
        val secondary = "#7112a1"

        // Text colors
        val white = "#ffffff"
        val lightGray = "#cccccc"
        val red = "#ff0000"

        // Background colors
        val darkBackground = "#101010"
        val mediumGray = "#666666"

        // Transparent colors
        val lightTransparent = "rgba(255, 255, 255, 0.1)"
        val veryLightTransparent = "rgba(255, 255, 255, 0.03)"
        val mediumTransparent = "rgba(255, 255, 255, 0.7)"
    }

    /**
     * Spacings used throughout the application.
     */
    object Spacing {
        // Base spacings
        val none = "0px"
        val xs = "4px"
        val sm = "8px"
        val md = "16px"
        val lg = "24px"
        val xl = "32px"

        // Rem values
        val rem2 = "2rem"
        val rem5 = "5rem"

        // Layout values
        val maxContentWidth = "1200px"
        val navHeight = "56px"
        val fullHeight = "100vh"
        val fullWidth = "100%"

        // Border radius
        val borderRadius = "4px"
    }

    /**
     * Typography styles used throughout the application.
     */
    object Typography {
        // Font sizes
        val fontXs = "14px"
        val fontSm = "18px"
        val fontMd = "20px"
        val fontLg = "24px"
        val fontXl = "36px"

        // Font family
        val defaultFontFamily = "'Poppins', sans-serif"

        // Line heights
        val lineHeightNormal = "1.5"
        val lineHeightLarge = "1.6"
        val lineHeightXLarge = "1.8"

        // Font weights
        val fontWeightNormal = "400"
        val fontWeightBold = "bold"

        // Common text styles (kept for backward compatibility)
        fun applyH1Style(element: HTMLElement) {
            element.style.apply {
                fontSize = fontXl
                fontWeight = fontWeightNormal
                lineHeight = lineHeightNormal
                fontFamily = defaultFontFamily
            }
        }

        fun applyH2Style(element: HTMLElement) {
            element.style.apply {
                fontSize = fontLg
                fontWeight = fontWeightBold
                lineHeight = lineHeightNormal
                fontFamily = defaultFontFamily
            }
        }

        fun applyBody1Style(element: HTMLElement) {
            element.style.apply {
                fontSize = fontMd
                fontWeight = fontWeightNormal
                lineHeight = lineHeightNormal
                fontFamily = defaultFontFamily
            }
        }

        fun applyBody2Style(element: HTMLElement) {
            element.style.apply {
                fontSize = fontSm
                fontWeight = fontWeightNormal
                lineHeight = lineHeightLarge
                fontFamily = defaultFontFamily
            }
        }

        fun applyCaptionStyle(element: HTMLElement) {
            element.style.apply {
                fontSize = fontXs
                fontWeight = fontWeightNormal
                lineHeight = lineHeightNormal
                fontFamily = defaultFontFamily
            }
        }
    }

    /**
     * Common style values used throughout the application.
     */
    object Styles {
        // Kept for backward compatibility
    }

    /**
     * Creates and returns a stylesheet for the Theme.
     * This method uses CSSOM API to create a stylesheet with theme-related styles.
     */
    fun stylesheet(): CSSStyleSheet {
        // Create a new style element
        val styleElement = document.createElement("style")
        document.head?.appendChild(styleElement)

        // Get the stylesheet from the document's styleSheets collection
        val stylesheet = document.styleSheets[document.styleSheets.length - 1] as CSSStyleSheet

        // Add typography rules to the stylesheet

        // H1 style
        val h1Rule = """
            .typography-h1 {
                font-size: ${Typography.fontXl};
                font-weight: ${Typography.fontWeightNormal};
                line-height: ${Typography.lineHeightNormal};
                font-family: ${Typography.defaultFontFamily};
            }
        """.trimIndent()
        stylesheet.insertRule(h1Rule, stylesheet.cssRules.length)

        // H2 style
        val h2Rule = """
            .typography-h2 {
                font-size: ${Typography.fontLg};
                font-weight: ${Typography.fontWeightBold};
                line-height: ${Typography.lineHeightNormal};
                font-family: ${Typography.defaultFontFamily};
            }
        """.trimIndent()
        stylesheet.insertRule(h2Rule, stylesheet.cssRules.length)

        // Body1 style
        val body1Rule = """
            .typography-body1 {
                font-size: ${Typography.fontMd};
                font-weight: ${Typography.fontWeightNormal};
                line-height: ${Typography.lineHeightNormal};
                font-family: ${Typography.defaultFontFamily};
            }
        """.trimIndent()
        stylesheet.insertRule(body1Rule, stylesheet.cssRules.length)

        // Body2 style
        val body2Rule = """
            .typography-body2 {
                font-size: ${Typography.fontSm};
                font-weight: ${Typography.fontWeightNormal};
                line-height: ${Typography.lineHeightLarge};
                font-family: ${Typography.defaultFontFamily};
            }
        """.trimIndent()
        stylesheet.insertRule(body2Rule, stylesheet.cssRules.length)

        // Caption style
        val captionRule = """
            .typography-caption {
                font-size: ${Typography.fontXs};
                font-weight: ${Typography.fontWeightNormal};
                line-height: ${Typography.lineHeightNormal};
                font-family: ${Typography.defaultFontFamily};
            }
        """.trimIndent()
        stylesheet.insertRule(captionRule, stylesheet.cssRules.length)

        // Add global styles

        // Body style
        val bodyRule = """
            body {
                background-color: ${Colors.darkBackground};
                color: ${Colors.white};
                font-family: ${Typography.defaultFontFamily};
                margin: 0;
                padding: 0;
            }
        """.trimIndent()
        stylesheet.insertRule(bodyRule, stylesheet.cssRules.length)

        return stylesheet
    }
}
