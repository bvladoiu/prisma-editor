package prisma.editor.css


object Theme {

    val primary = "#56b2f0"
    val secondary = "#7112a1"
    val white = "#ffffff"
    val lightGray = "#cccccc"
    val darkBackground = "#101010"
    val mediumGray = "#666666"
    val lightTransparent = "rgba(255, 255, 255, 0.1)"
    val veryLightTransparent = "rgba(255, 255, 255, 0.03)"
    val mediumTransparent = "rgba(255, 255, 255, 0.7)"
    val spacing = "var(--spacing)"
    val iconSize = "var(--icon-size)"

    fun getCssRules(): List<CssRuleDefinition> {
        return listOf(
            ":root" to {
                setProperty("--color-primary", primary)
                setProperty("--color-secondary", secondary)
                setProperty("--color-white", white)
                setProperty("--color-light-gray", lightGray)
                setProperty("--color-dark-background", darkBackground)
                setProperty("--color-medium-gray", mediumGray)
                setProperty("--color-light-transparent", lightTransparent)
                setProperty("--color-very-light-transparent", veryLightTransparent)
                setProperty("--color-medium-transparent", mediumTransparent)

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
