package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography

class Expertise(var name: String, var description: String) {

    fun preview(): HTMLElement {
        return document.create.div {
            {
                attributes["expertise"] = ""
                h3 {
                    +name
                }
                p {
                    +description
                }
            }
        }
    }

    companion object {
        const val TAG = "expertise"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[expertise]" to {
                    padding = Theme.spacing
                    backgroundColor = "var(--color-light-transparent)"
                    borderRadius = "4px"
                    height = "100%"
                },

                "[expertise] h3" to {
                    fontSize = Typography.fontMd
                    fontFamily = Typography.defaultFontFamily
                    marginBottom = "8px"
                    color = Theme.white
                },

                "[expertise] p" to {
                    margin = "0"
                    lineHeight = Typography.lineHeightNormal
                    fontFamily = Typography.defaultFontFamily
                }
            )
        }
    }
}
