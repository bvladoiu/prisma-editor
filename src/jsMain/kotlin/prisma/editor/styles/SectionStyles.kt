package prisma.editor.styles

import org.jetbrains.compose.web.css.*

object SectionStyles {
    fun container(marginTop: CSSNumeric): StyleBuilder.() -> Unit = {
        marginTop(marginTop)
        marginBottom(2.cssRem)
        padding(0.px, 16.px)
        maxWidth(1200.px)
        property("margin", "0 auto")
    }

    val article: StyleBuilder.() -> Unit = {
        padding(16.px)
        backgroundColor(rgba(255, 255, 255, 0.03))
        borderRadius(4.px)
    }

    val headerWithDivider: StyleBuilder.() -> Unit = {
        fontSize(24.px)
        fontWeight("bold")
        marginBottom(16.px)
        paddingBottom(8.px)
        property("border-bottom", "1px solid #666666")
    }

    val headerWithoutDivider: StyleBuilder.() -> Unit = {
        marginBottom(16.px)
    }

    val heading: StyleBuilder.() -> Unit = {
        fontSize(24.px)
        fontWeight("bold")
        margin(0.px)
    }
}