package prisma.editor.styles

import org.jetbrains.compose.web.css.*

object UpdatesListStyles {
    val list: StyleBuilder.() -> Unit = {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        gap(16.px)
        listStyleType("none")
        padding(0.px)
        margin(0.px)
    }

    val listItem: StyleBuilder.() -> Unit = {
        padding(8.px, 0.px)
        property("border-bottom", "1px solid rgba(255, 255, 255, 0.1)")
    }

    val updateLink: StyleBuilder.() -> Unit = {
        color(Color.white)
        textDecoration("none")
        fontSize(18.px)
        display(DisplayStyle.Block)
        marginBottom(4.px)
    }

    val metadata: StyleBuilder.() -> Unit = {
        fontSize(14.px)
        color(rgba(255, 255, 255, 0.7))
    }
}