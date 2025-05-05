package prisma.editor.styles

import org.jetbrains.compose.web.css.*

object HeroStyles {
    val title: StyleBuilder.() -> Unit = {
        fontSize(36.px)
        fontWeight("400")
        marginTop(2.cssRem)
        marginBottom(16.px)
    }

    val description: StyleBuilder.() -> Unit = {
        fontSize(18.px)
        lineHeight("1.6")
        marginBottom(24.px)
    }
}