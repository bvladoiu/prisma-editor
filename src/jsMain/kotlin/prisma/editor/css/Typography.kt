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
