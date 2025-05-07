package prisma.editor.styles

import org.w3c.dom.HTMLElement

object NavigationMenuStyles {
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
}
