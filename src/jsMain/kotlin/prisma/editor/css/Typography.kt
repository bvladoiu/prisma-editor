package prisma.editor.css

/**
 * Typography constants and styling for the application.
 * Defines typography styles using Lexend (for headings)
 * and Roboto Flex (for body text, UI labels, and small elements),
 * with fluid sizing and custom axis adjustments.
 * Font imports (e.g., via <link> tags in HTML head) for Lexend and Roboto Flex
 * must be included separately. Optical size ('opsz') is omitted from
 * font-variation-settings to enable automatic browser management.
 */
object Typography {
    val headingFontFamily = "'Lexend', sans-serif"
    val bodyFontFamily = "'Roboto Flex', sans-serif"
    val iconFontFamily = "'Material Symbols Outlined', sans-serif"

    // CSS class names for typography styles
    const val DISPLAY = "display"
    const val HEADLINE = "headline"
    const val BODY = "body"
    const val CODE = "code"
    const val ACTION = "action"
    const val CAPTION = "caption"
    const val TAGLINE = "tagline"
    const val QUOTE = "quote"
    const val SMALL_TEXT = "small-text"
    const val METRIC = "metric"
    const val MENU_LABEL = "menu-label"
    const val ICON = "icon"

    // Fluid typography helper function
    private fun fluidSize(minSize: Double, maxSize: Double): String {
        return "clamp(${minSize}rem, ${minSize - (minSize * 0.25)}rem + ${(maxSize - minSize) * 0.952}vw, ${maxSize}rem)"
    }

    // Basic body styling and typography classes
    fun getCssRules(): List<CssRuleDefinition> {
        return listOf(
            "body" to {
                fontFamily = bodyFontFamily
                margin = "0"
                padding = "0"
            },

            // DISPLAY – ultra-large text for hero sections or prominent headings
            ".$DISPLAY" to {
                fontFamily = headingFontFamily
                fontWeight = "700"
                fontSize = fluidSize(2.25, 2.75)
                lineHeight = "1.1"
                letterSpacing = "-0.02em"
                textTransform = "none"
            },

            // HEADLINE – primary section or page headings
            ".$HEADLINE" to {
                fontFamily = headingFontFamily
                fontWeight = "600"
                fontSize = fluidSize(2.0, 3.0)
                lineHeight = "1.2"
                letterSpacing = "-0.01em"
                textTransform = "none"
            },

            // BODY – main body text for paragraphs and long-form content
            ".$BODY" to {
                fontFamily = bodyFontFamily
                fontWeight = "400"
                fontSize = fluidSize(1.0, 1.25)
                lineHeight = "1.5"
                letterSpacing = "0em"
                textTransform = "none"
            },

            // CODE – inline or block code snippets
            ".$CODE" to {
                fontFamily = bodyFontFamily
                fontWeight = "500"
                fontSize = fluidSize(0.9, 1.0)
                lineHeight = "1.45"
                letterSpacing = "0em"
                textTransform = "none"
            },

            // ACTION – text for buttons or calls-to-action
            ".$ACTION" to {
                fontFamily = bodyFontFamily
                fontWeight = "600"
                fontSize = fluidSize(0.875, 1.0)
                lineHeight = "1.3"
                letterSpacing = "0.05em"
                textTransform = "uppercase"
            },

            // CAPTION – small text for image captions or annotations
            ".$CAPTION" to {
                fontFamily = bodyFontFamily
                fontWeight = "400"
                fontSize = fluidSize(0.813, 0.875)
                lineHeight = "1.4"
                letterSpacing = "0em"
                textTransform = "none"
            },

            // TAGLINE – secondary headings or subtitles
            ".$TAGLINE" to {
                fontFamily = headingFontFamily
                fontWeight = "500"
                fontSize = fluidSize(1.125, 1.5)
                lineHeight = "1.3"
                letterSpacing = "0em"
                textTransform = "none"
            },

            // QUOTE – stylized text for blockquotes or featured quotes
            ".$QUOTE" to {
                fontFamily = bodyFontFamily
                fontWeight = "400"
                fontSize = fluidSize(1.125, 1.5)
                lineHeight = "1.4"
                letterSpacing = "0em"
                textTransform = "none"
                fontStyle = "italic"
            },

            // SMALL-TEXT – very small fine-print text for disclaimers, footnotes, etc.
            ".$SMALL_TEXT" to {
                fontFamily = bodyFontFamily
                fontWeight = "500"
                fontSize = fluidSize(0.75, 0.875)
                lineHeight = "1.4"
                letterSpacing = "0em"
                textTransform = "none"
            },

            // METRIC – large numeric values or statistics for emphasis
            ".$METRIC" to {
                fontFamily = headingFontFamily
                fontWeight = "700"
                fontSize = fluidSize(2.25, 3.75)
                lineHeight = "1.2"
                letterSpacing = "0em"
                textTransform = "none"
            },

            // MENU-LABEL – labels for navigation menus or small UI sections
            ".$MENU_LABEL" to {
                fontFamily = bodyFontFamily
                fontSize = fluidSize(0.875, 1.125)
                fontWeight = "600"
                lineHeight = "1.3"
                letterSpacing = "0.05em"
                setProperty("font-feature-settings", "'liga' on, 'dlig' on")
                transition = "font-variation-settings 0.2s ease-in-out, opacity 0.3s ease-in-out"
                setProperty("font-variation-settings", "'GRAD' 0")
            },

            // MENU-LABEL variants
            ".$MENU_LABEL.deemphasized" to {
                fontWeight = "600" // Maintain base weight
                setProperty("font-variation-settings", "'GRAD' -200")
            },

            ".$MENU_LABEL.emphasized" to {
                fontWeight = "600" // Maintain base weight
                setProperty("font-variation-settings", "'GRAD' 150")
            },

            // ICON - Material Symbols icons
            "[$ICON]" to {
                fontFamily = iconFontFamily
                fontSize = "var(--icon-size)"
                setProperty("font-variation-settings", "'FILL' 0, 'wght' 400, 'GRAD' 0")
                transition = "font-variation-settings 0.2s ease-in-out, opacity 0.3s ease-in-out"
            },

            // ICON variants
            "[$ICON][deemphasized]" to {
                setProperty("font-variation-settings", "'FILL' 0, 'wght' 400, 'GRAD' -50")
            },

            "[$ICON][emphasized]" to {
                setProperty("font-variation-settings", "'FILL' 1, 'wght' 400, 'GRAD' 200")
            },

            // ICON active/selected state - like regular but with fill
            "[$ICON][active]" to {
                setProperty("font-variation-settings", "'FILL' 1, 'wght' 400, 'GRAD' 0")
            }
        )
    }
}
