package prisma.editor.styles

import org.w3c.dom.HTMLElement

object UpdatesListStyles {
    fun applyListStyle(element: HTMLElement) {
        element.style.apply {
            display = "flex"
            flexDirection = "column"
            this.asDynamic().gap = Theme.Spacing.md
            listStyleType = "none"
            padding = Theme.Spacing.none
            margin = Theme.Spacing.none
        }
    }

    fun applyListItemStyle(element: HTMLElement) {
        element.style.apply {
            padding = "${Theme.Spacing.sm} ${Theme.Spacing.none}"
            borderBottom = "1px solid ${Theme.Colors.lightTransparent}"
            fontFamily = Theme.Typography.defaultFontFamily
        }
    }

    fun applyUpdateLinkStyle(element: HTMLElement) {
        element.style.apply {
            color = Theme.Colors.white
            textDecoration = "none"
            fontSize = Theme.Typography.fontSm
            fontFamily = Theme.Typography.defaultFontFamily
            display = "block"
            marginBottom = Theme.Spacing.xs
        }
    }

    fun applyMetadataStyle(element: HTMLElement) {
        element.style.apply {
            fontSize = Theme.Typography.fontXs
            fontFamily = Theme.Typography.defaultFontFamily
            color = Theme.Colors.mediumTransparent
        }
    }
}
