package prisma.editor.ui.component

import kotlinx.html.*
import prisma.editor.model.Navigation

/**
 * Creates a navigation menu.
 */
fun FlowContent.NavigationMenu(
    brand: String = "Prisma-Software",
    items: List<String> = listOf("Home", "News", "Blog", "Company")
) {
    nav {
        attributes["style"] = """
            background-color: rgba(0, 0, 0, 0.8);
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            z-index: 1000;
        """

        div {
            attributes["style"] = """
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 16px;
                max-width: 1200px;
                margin: 0 auto;
            """

            a {
                href = "#"
                attributes["style"] = """
                    color: #56b2f0;
                    font-size: 20px;
                    font-weight: bold;
                    text-decoration: none;
                """
                +brand
            }

            navItems(items)
        }
    }
}

/**
 * Creates navigation items.
 */
private fun FlowContent.navItems(items: List<String>) {
    ul {
        attributes["style"] = """
            display: flex;
            list-style-type: none;
            margin: 0;
            padding: 0;
            gap: 16px;
        """

        items.forEach { item ->
            li {
                a {
                    href = "#"
                    attributes["style"] = """
                        color: white;
                        text-decoration: none;
                        font-size: 16px;
                    """
                    +item
                }
            }
        }
    }
}
