package prisma.editor.styles

import org.w3c.dom.HTMLElement

/**
 * Styles for the NavigationMenu component.
 */
object NavigationMenuStyles {
    // Style methods for direct manipulation (legacy support)
    fun applyContainerStyle(element: HTMLElement) {
        element.style.apply {
            position = "fixed"
            top = Theme.Spacing.none
            left = Theme.Spacing.none
            right = Theme.Spacing.none
            backgroundColor = Theme.Colors.primary
            zIndex = "100"
        }
    }

    fun applyContentStyle(element: HTMLElement) {
        element.style.apply {
            display = "flex"
            alignItems = "center"
            padding = "${Theme.Spacing.none} ${Theme.Spacing.md}"
            maxWidth = Theme.Spacing.maxContentWidth
            margin = "0 auto"
            height = Theme.Spacing.navHeight
        }
    }

    fun applyBrandStyle(element: HTMLElement) {
        element.style.apply {
            color = Theme.Colors.white
            fontWeight = Theme.Typography.fontWeightBold
            fontFamily = Theme.Typography.defaultFontFamily
            textDecoration = "none"
            marginRight = Theme.Spacing.lg
        }
    }

    fun applyNavListStyle(element: HTMLElement) {
        element.style.apply {
            display = "flex"
            listStyleType = "none"
            margin = Theme.Spacing.none
            padding = Theme.Spacing.none
        }
    }

    fun applyNavItemStyle(element: HTMLElement) {
        element.style.apply {
            margin = "${Theme.Spacing.none} ${Theme.Spacing.xs}"
        }
    }

    fun applyNavLinkStyle(element: HTMLElement) {
        element.style.apply {
            color = Theme.Colors.white
            textDecoration = "none"
            padding = "${Theme.Spacing.sm} ${Theme.Spacing.md}"
            display = "block"
            backgroundColor = Theme.Colors.secondary
            borderRadius = Theme.Spacing.borderRadius
            fontFamily = Theme.Typography.defaultFontFamily
        }
    }

    // CSS Stylesheet for the NavigationMenu component
    val stylesheet: String get() = """
        [navigation-menu] {
            background-color: rgba(0, 0, 0, 0.8);
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            z-index: 1000;
        }

        [navigation-menu] > div {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 16px;
            max-width: 1200px;
            margin: 0 auto;
        }

        [navigation-menu] a.brand {
            color: #56b2f0;
            font-size: 20px;
            font-weight: bold;
            text-decoration: none;
        }

        [navigation-menu] ul {
            display: flex;
            list-style-type: none;
            margin: 0;
            padding: 0;
            gap: 16px;
        }

        [navigation-menu] ul a {
            color: white;
            text-decoration: none;
            font-size: 16px;
        }
    """.trimIndent()
}
