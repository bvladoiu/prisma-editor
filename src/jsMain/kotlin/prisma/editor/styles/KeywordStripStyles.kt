package prisma.editor.styles

import org.jetbrains.compose.web.css.*

object KeywordStripStyles {
    val container: StyleBuilder.() -> Unit = {
        padding(16.px)
        backgroundColor(rgba(255, 255, 255, 0.1))
        borderRadius(4.px)
        textAlign("center")
    }

    val paragraph: StyleBuilder.() -> Unit = {
        marginTop(16.px)
        marginBottom(16.px)
        lineHeight("1.8")
    }

    val separator: StyleBuilder.() -> Unit = {
        margin(0.px, 8.px)
    }
}