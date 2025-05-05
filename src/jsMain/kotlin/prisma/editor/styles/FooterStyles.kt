package prisma.editor.styles

import org.jetbrains.compose.web.css.*

object FooterStyles {
    val container: StyleBuilder.() -> Unit = {
        backgroundColor(Color("#101010"))
        padding(32.px, 0.px)
        color(Color("#cccccc"))
        textAlign("center")
        marginTop(32.px)
    }

    val content: StyleBuilder.() -> Unit = {
        maxWidth(1200.px)
        property("margin", "0 auto")
        padding(0.px, 16.px)
    }

    val link: StyleBuilder.() -> Unit = {
        color(Color.white)
        textDecoration("underline")
    }
}