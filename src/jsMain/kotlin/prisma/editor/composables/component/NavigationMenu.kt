package prisma.editor.composables.component

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import prisma.editor.styles.NavigationMenuStyles
import prisma.editor.model.Navigation

@Composable
fun NavigationMenu(navigation: Navigation? = null) {
    Nav(attrs = {
        style(NavigationMenuStyles.container)
    }) {
        Div(attrs = {
            style(NavigationMenuStyles.content)
        }) {
            A(attrs = {
                style(NavigationMenuStyles.brand)
            }) {
                Text(navigation?.brand ?: "Prisma-Software")
            }

            NavItems(
                items = navigation?.items ?: listOf("Home", "News", "Blog", "Company")
            )
        }
    }
}

@Composable
fun NavItems(items: List<String>) {
    Ul(attrs = {
        style(NavigationMenuStyles.navList)
    }) {
        items.forEach { item ->
            Li(attrs = {
                style(NavigationMenuStyles.navItem)
            }) {
                A(attrs = {
                    style(NavigationMenuStyles.navLink)
                }) {
                    Text(item)
                }
            }
        }
    }
}
