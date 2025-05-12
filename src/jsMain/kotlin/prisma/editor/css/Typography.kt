package prisma.editor.css


object Typography {

    val fontXs = "14px"
    val fontSm = "18px"
    val fontMd = "20px"
    val fontLg = "24px"
    val fontXl = "36px"


    val defaultFontFamily = "'Poppins', sans-serif"


    val lineHeightNormal = "1.5"
    val lineHeightLarge = "1.6"
    val lineHeightXLarge = "1.8"

    val fontWeightNormal = "400"
    val fontWeightBold = "bold"


    fun getCssRules(): List<CssRuleDefinition> {
        return listOf(

            ".typography-h1" to {
                fontSize = fontXl
                fontWeight = fontWeightNormal
                lineHeight = lineHeightNormal
                fontFamily = defaultFontFamily
            },

            ".typography-h2" to {
                fontSize = fontLg
                fontWeight = fontWeightBold
                lineHeight = lineHeightNormal
                fontFamily = defaultFontFamily
            },
            ".typography-body1" to {
                fontSize = fontMd
                fontWeight = fontWeightNormal
                lineHeight = lineHeightNormal
                fontFamily = defaultFontFamily
            },

            "body" to {
                backgroundColor = Theme.darkBackground
                color = Theme.white
                fontFamily = defaultFontFamily
                margin = "0"
                padding = "0"
            }
        )
    }
}