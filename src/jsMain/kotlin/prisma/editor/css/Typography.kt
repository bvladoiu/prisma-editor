package prisma.editor.css

/**
 * Typography constants for the application.
 * Note: Most typography styling is now handled by the Text component.
 * This object only contains basic constants that might be needed elsewhere.
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

    // Basic body styling
    fun getCssRules(): List<CssRuleDefinition> {
        return listOf(
            "body" to {
                backgroundColor = Theme.darkBackground
                color = Theme.white
                fontFamily = regularFontFamily
                margin = "0"
                padding = "0"
            }
        )
    }
}
