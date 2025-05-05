package prisma.editor.composables.component

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Composable
fun HeroContent(
    title: String,
    description: String,
    buttonText: String
) {
    H1(attrs = {
        style {
            fontSize(36.px)
            fontWeight("400")
            marginTop(2.cssRem)
            marginBottom(16.px)
        }
    }) {
        Text(title)
    }

    P(attrs = {
        style {
            fontSize(18.px)
            lineHeight("1.6")
            marginBottom(24.px)
        }
    }) {
        Text(description)
    }

    Button(attrs = {
        style {
            backgroundColor(Color("#7112a1"))
            color(Color("#56b2f0"))
            padding(8.px, 16.px)
            border(0.px)
            borderRadius(4.px)
            cursor("pointer")
        }
    }) {
        Text(buttonText)
    }
}