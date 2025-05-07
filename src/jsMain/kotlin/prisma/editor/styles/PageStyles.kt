package prisma.editor.styles

import org.w3c.dom.HTMLElement

object PageStyles {
    fun applyContainerStyle(element: HTMLElement) {
        element.style.apply {
            backgroundColor = Theme.Colors.primary
            color = Theme.Colors.white
            fontFamily = Theme.Typography.defaultFontFamily
            margin = Theme.Spacing.none
            padding = Theme.Spacing.none
        }
    }

    fun applyLoadingContainerStyle(element: HTMLElement) {
        element.style.apply {
            display = "flex"
            justifyContent = "center"
            alignItems = "center"
            height = Theme.Spacing.fullHeight
        }
    }

    fun applyErrorContainerStyle(element: HTMLElement) {
        element.style.apply {
            display = "flex"
            justifyContent = "center"
            alignItems = "center"
            height = Theme.Spacing.fullHeight
            color = Theme.Colors.red
            fontFamily = Theme.Typography.defaultFontFamily
        }
    }

    fun applyMainContentStyle(element: HTMLElement) {
        element.style.apply {
            marginTop = Theme.Spacing.rem5
            marginBottom = Theme.Spacing.rem2
        }
    }

    fun applySectionItemsGridStyle(element: HTMLElement, minWidth: String) {
        element.style.apply {
            display = "grid"
            this.asDynamic().gridTemplateColumns = "repeat(auto-fit, minmax($minWidth, 1fr))"
            this.asDynamic().gap = Theme.Spacing.md
        }
    }

    fun applyButtonContainerStyle(element: HTMLElement) {
        element.style.apply {
            marginTop = Theme.Spacing.rem2
        }
    }

    fun applyButtonStyle(element: HTMLElement) {
        element.style.apply {
            backgroundColor = Theme.Colors.secondary
            color = Theme.Colors.primary
            padding = "${Theme.Spacing.sm} ${Theme.Spacing.md}"
            border = Theme.Spacing.none
            borderRadius = Theme.Spacing.borderRadius
            cursor = "pointer"
            fontFamily = Theme.Typography.defaultFontFamily
        }
    }
}
