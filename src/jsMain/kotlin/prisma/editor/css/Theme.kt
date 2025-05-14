package prisma.editor.css


object Theme {

    // Actual color values with semantic names
    val brandBlue = "#56b2f0"
    val brandPurple = "#7112a1"
    val pureWhite = "#ffffff"
    val softGray = "#cccccc"
    val deepBlack = "#101010"
    val neutralGray = "#666666"
    val whiteTransparentLight = "rgba(255, 255, 255, 0.1)"
    val whiteTransparentVeryLight = "rgba(255, 255, 255, 0.03)"
    val whiteTransparentMedium = "rgba(255, 255, 255, 0.7)"

    // Additional colors found in the codebase
    val brightPurple = "#7D3DF3"
    val vibrantPurple = "#6200EE"
    val blackShadowLight = "rgba(0, 0, 0, 0.2)"
    val blackShadowMedium = "rgba(0, 0, 0, 0.3)"
    val blackShadowHeavy = "rgba(0, 0, 0, 0.8)"

    // CSS variable references
    val primary = "var(--color-primary)"
    val secondary = "var(--color-secondary)"
    val white = "var(--color-white)"
    val lightGray = "var(--color-light-gray)"
    val darkBackground = "var(--color-dark-background)"
    val mediumGray = "var(--color-medium-gray)"
    val lightTransparent = "var(--color-light-transparent)"
    val veryLightTransparent = "var(--color-very-light-transparent)"
    val mediumTransparent = "var(--color-medium-transparent)"
    val drawerPurple = "var(--color-drawer-purple)"
    val scaffoldPurple = "var(--color-scaffold-purple)"
    val shadowLight = "var(--color-shadow-light)"
    val shadowMedium = "var(--color-shadow-medium)"
    val shadowHeavy = "var(--color-shadow-heavy)"

    val spacing = "var(--spacing)"
    val iconSize = "var(--icon-size)"

    fun getCssRules(): List<CssRuleDefinition> {
        return listOf(
            ":root" to {
                setProperty("--color-primary", brandBlue)
                setProperty("--color-secondary", brandPurple)
                setProperty("--color-white", pureWhite)
                setProperty("--color-light-gray", softGray)
                setProperty("--color-dark-background", deepBlack)
                setProperty("--color-medium-gray", neutralGray)
                setProperty("--color-light-transparent", whiteTransparentLight)
                setProperty("--color-very-light-transparent", whiteTransparentVeryLight)
                setProperty("--color-medium-transparent", whiteTransparentMedium)
                setProperty("--color-drawer-purple", brightPurple)
                setProperty("--color-scaffold-purple", vibrantPurple)
                setProperty("--color-shadow-light", blackShadowLight)
                setProperty("--color-shadow-medium", blackShadowMedium)
                setProperty("--color-shadow-heavy", blackShadowHeavy)

                setProperty("--spacing", "clamp(8px, calc(-24/7px + (40/21)vw), 24px)")
                setProperty("--icon-size", "clamp(24px, calc(3/140*1vw + 11.142857vw), 42px)")
            },

            "body" to {
                backgroundColor = "var(--color-dark-background)"
                color = "var(--color-white)"
            },
            "body.dark" to {

                backgroundColor = "var(--color-white)"
                color = "var(--color-dark-background)"
            }
        )
    }
}
