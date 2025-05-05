package prisma.editor.styles

import org.jetbrains.compose.web.css.*

object ExpertiseItemStyles {
    val container: StyleBuilder.() -> Unit = {
        padding(16.px)
        backgroundColor(rgba(255, 255, 255, 0.1))
        borderRadius(4.px)
        height(100.percent)
    }

    val title: StyleBuilder.() -> Unit = {
        fontSize(20.px)
        marginBottom(8.px)
        color(Color.white)
    }

    val description: StyleBuilder.() -> Unit = {
        margin(0.px)
        lineHeight("1.5")
    }
}