package prisma.editor.styles

import org.jetbrains.compose.web.css.*

object ReasonItemStyles {
    val container: StyleBuilder.() -> Unit = {
        padding(16.px)
        backgroundColor(rgba(255, 255, 255, 0.1))
        borderRadius(4.px)
    }

    val title: StyleBuilder.() -> Unit = {
        fontWeight("bold")
        display(DisplayStyle.Block)
        marginBottom(8.px)
    }
}