package prisma.editor.styles

import org.jetbrains.compose.web.css.*

/**
 * Theme object containing all colors, spacings, and typography used throughout the application.
 */
object Theme {
    /**
     * Colors used throughout the application.
     */
    object Colors {
        // Primary colors
        val primary = Color("#56b2f0")
        val secondary = Color("#7112a1")

        // Text colors
        val white = Color.white
        val lightGray = Color("#cccccc")
        val red = Color.red

        // Background colors
        val darkBackground = Color("#101010")
        val mediumGray = Color("#666666")

        // Transparent colors
        val lightTransparent = rgba(255, 255, 255, 0.1)
        val veryLightTransparent = rgba(255, 255, 255, 0.03)
        val mediumTransparent = rgba(255, 255, 255, 0.7)
    }

    /**
     * Spacings used throughout the application.
     */
    object Spacing {
        // Base spacings
        val none = 0.px
        val xs = 4.px
        val sm = 8.px
        val md = 16.px
        val lg = 24.px
        val xl = 32.px

        // Rem values
        val rem2 = 2.cssRem
        val rem5 = 5.cssRem

        // Layout values
        val maxContentWidth = 1200.px
        val navHeight = 56.px
        val fullHeight = 100.vh
        val fullWidth = 100.percent

        // Border radius
        val borderRadius = 4.px
    }

    /**
     * Typography styles used throughout the application.
     */
    object Typography {
        // Font sizes
        val fontXs = 14.px
        val fontSm = 18.px
        val fontMd = 20.px
        val fontLg = 24.px
        val fontXl = 36.px

        // Font family
        val defaultFontFamily = "'Poppins', sans-serif"

        // Line heights
        val lineHeightNormal = "1.5"
        val lineHeightLarge = "1.6"
        val lineHeightXLarge = "1.8"

        // Font weights
        val fontWeightNormal = "400"
        val fontWeightBold = "bold"

        // Common text styles
        val h1: StyleBuilder.() -> Unit = {
            fontSize(fontXl)
            fontWeight(fontWeightNormal)
            lineHeight(lineHeightNormal)
            fontFamily(defaultFontFamily)
        }

        val h2: StyleBuilder.() -> Unit = {
            fontSize(fontLg)
            fontWeight(fontWeightBold)
            lineHeight(lineHeightNormal)
            fontFamily(defaultFontFamily)
        }

        val body1: StyleBuilder.() -> Unit = {
            fontSize(fontMd)
            fontWeight(fontWeightNormal)
            lineHeight(lineHeightNormal)
            fontFamily(defaultFontFamily)
        }

        val body2: StyleBuilder.() -> Unit = {
            fontSize(fontSm)
            fontWeight(fontWeightNormal)
            lineHeight(lineHeightLarge)
            fontFamily(defaultFontFamily)
        }

        val caption: StyleBuilder.() -> Unit = {
            fontSize(fontXs)
            fontWeight(fontWeightNormal)
            lineHeight(lineHeightNormal)
            fontFamily(defaultFontFamily)
        }
    }

    /**
     * Common style values used throughout the application.
     */
    object Styles {
        // Kept for backward compatibility
    }
}
