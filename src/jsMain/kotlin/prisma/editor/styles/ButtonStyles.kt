package prisma.editor.styles

import org.jetbrains.compose.web.css.*

object ButtonStyles {
    val primary: StyleBuilder.() -> Unit = {
        backgroundColor(Color("#7112a1"))
        color(Color("#56b2f0"))
        padding(8.px, 16.px)
        border(0.px)
        borderRadius(4.px)
        cursor("pointer")
    }
}