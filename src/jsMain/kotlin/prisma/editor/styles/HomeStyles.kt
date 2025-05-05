package prisma.editor.styles

import org.jetbrains.compose.web.css.*

object HomeStyles {
    val container: StyleBuilder.() -> Unit = {
        backgroundColor(Color("#56b2f0"))
        color(Color.white)
        fontFamily("'Poppins', sans-serif")
        margin(0.px)
        padding(0.px)
    }

    val loadingContainer: StyleBuilder.() -> Unit = {
        display(DisplayStyle.Flex)
        justifyContent(JustifyContent.Center)
        alignItems(AlignItems.Center)
        height(100.vh)
    }

    val errorContainer: StyleBuilder.() -> Unit = {
        display(DisplayStyle.Flex)
        justifyContent(JustifyContent.Center)
        alignItems(AlignItems.Center)
        height(100.vh)
        color(Color.red)
    }

    val mainContent: StyleBuilder.() -> Unit = {
        marginTop(5.cssRem)
        marginBottom(2.cssRem)
    }

    fun sectionItemsGrid(minWidth: String): StyleBuilder.() -> Unit = {
        display(DisplayStyle.Grid)
        property("grid-template-columns", "repeat(auto-fit, minmax($minWidth, 1fr))")
        gap(16.px)
    }

    val buttonContainer: StyleBuilder.() -> Unit = {
        marginTop(2.cssRem)
    }

    val button: StyleBuilder.() -> Unit = {
        backgroundColor(Color("#7112a1"))
        color(Color("#56b2f0"))
        padding(8.px, 16.px)
        border(0.px)
        borderRadius(4.px)
        cursor("pointer")
    }
}
