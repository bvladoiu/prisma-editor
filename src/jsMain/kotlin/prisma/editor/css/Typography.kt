package prisma.editor.css

/**
 * Typography constants and styling for the application.
 * Provides typography classes (display, headline, regular, action) that can be applied to text elements.
 */
object Typography {
    // Font families
    val displayFontFamily = "'Poppins', sans-serif"
    val regularFontFamily = "'Roboto Flex', sans-serif"
    val defaultFontFamily = regularFontFamily

    // Font sizes
    val fontXs = "12px"
    val fontSm = "14px"
    val fontMd = "16px"
    val fontLg = "20px"
    val fontXl = "24px"

    // Font weights
    val fontWeightNormal = "400"
    val fontWeightBold = "700"

    // Line heights
    val lineHeightNormal = "1.5"
    val lineHeightLarge = "1.8"

    // CSS class names for typography styles
    const val DISPLAY = "display"
    const val HEADLINE = "headline"
    const val REGULAR = "regular"
    const val ACTION = "action"

    // Basic body styling and typography classes
    fun getCssRules(): List<CssRuleDefinition> {
        return listOf(
            "body" to {
                fontFamily = regularFontFamily
                margin = "0"
                padding = "0"
            },
            ".$DISPLAY" to {
                fontFamily = displayFontFamily
                fontSize = "36px"
                fontWeight = fontWeightBold
                lineHeight = "1.2"
                margin = "0 0 16px 0"
            },
            ".$HEADLINE" to {
                fontFamily = displayFontFamily
                fontSize = "24px"
                fontWeight = fontWeightBold
                lineHeight = "1.3"
                margin = "0 0 12px 0"
            },
            ".$REGULAR" to {
                fontFamily = regularFontFamily
                fontSize = fontMd
                fontWeight = fontWeightNormal
                lineHeight = lineHeightNormal
                margin = "0 0 8px 0"
            },
            ".$ACTION" to {
                fontFamily = regularFontFamily
                fontSize = fontSm
                fontWeight = "500"
                lineHeight = "1.4"
                textTransform = "uppercase"
                letterSpacing = "0.5px"
                margin = "0"
            }
        )
    }
}
