package prisma.editor.styles

import org.jetbrains.compose.web.css.*

object NavigationMenuStyles {
    val container: StyleBuilder.() -> Unit = {
        position(Position.Fixed)
        top(0.px)
        left(0.px)
        right(0.px)
        backgroundColor(Color("#56b2f0"))
        property("z-index", "100")
    }

    val content: StyleBuilder.() -> Unit = {
        display(DisplayStyle.Flex)
        alignItems(AlignItems.Center)
        padding(0.px, 16.px)
        maxWidth(1200.px)
        property("margin", "0 auto")
        height(56.px)
    }

    val brand: StyleBuilder.() -> Unit = {
        color(Color.white)
        fontWeight("bold")
        textDecoration("none")
        marginRight(24.px)
    }

    val navList: StyleBuilder.() -> Unit = {
        display(DisplayStyle.Flex)
        listStyleType("none")
        margin(0.px)
        padding(0.px)
    }

    val navItem: StyleBuilder.() -> Unit = {
        margin(0.px, 4.px)
    }

    val navLink: StyleBuilder.() -> Unit = {
        color(Color.white)
        textDecoration("none")
        padding(8.px, 16.px)
        display(DisplayStyle.Block)
        backgroundColor(Color("#7112a1"))
        borderRadius(4.px)
    }
}