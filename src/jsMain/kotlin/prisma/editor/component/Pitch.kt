package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography


class Pitch(var title: String = "", var description: String = "") {
    fun preview(): HTMLElement {
        return document.create.div {
            attributes["pitch"] = ""
            h3 {
                +title
            }
            p {
                +description
            }
        }
    }

    companion object {
        const val TAG = "pitch"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[pitch]" to {
                    padding = Theme.spacing
                    backgroundColor = "var(--color-light-transparent)"
                    borderRadius = "4px"
                    fontFamily = Typography.defaultFontFamily
                },

                "[pitch] h3" to {
                    fontSize = Typography.fontMd
                    fontWeight = Typography.fontWeightBold
                    fontFamily = Typography.defaultFontFamily
                    marginTop = "0"
                    marginBottom = "8px"
                    color = Theme.primary
                },

                "[pitch] p" to {
                    margin = "0"
                    fontFamily = Typography.defaultFontFamily
                }
            )
        }
    }
}
