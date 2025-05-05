package prisma.editor.composables.component

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import prisma.editor.styles.HeroStyles
import prisma.editor.styles.ButtonStyles

@Composable
fun HeroContent(
    title: String,
    description: String,
    buttonText: String
) {
    H1(attrs = {
        style(HeroStyles.title)
    }) {
        Text(title)
    }

    P(attrs = {
        style(HeroStyles.description)
    }) {
        Text(description)
    }

    Button(attrs = {
        style(ButtonStyles.primary)
    }) {
        Text(buttonText)
    }
}
