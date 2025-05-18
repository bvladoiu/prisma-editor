package prisma.editor.pages

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.component.*

class Home : Page() {
    override val tag: String = TAG

    companion object {
        const val TAG = "home-page"
    }
    override fun create(): HTMLElement {
        // Call parent's create() to set up common page elements
        val container = super.create()

        // Add home-page attribute
        container.setAttribute("home-page", "")
        val heroSection = Section(
            initialTitle = "Embedded Software Solutions — Secure for Every Industry",
            initialIsDivider = false
        )
        val heroElement = heroSection.buildHtml()

        val heroContent = document.create.div {
            p {
                +"Over 20 years of embedded expertise in mission-critical systems, from in-vehicle platforms to robust, cloud-connected applications. We focus on quality, compliance, and a user-first experience to ensure your products excel in competitive markets."
            }

            div {
                button {
                    attributes["class"] = "primary-button"
                    +"Get in Touch"
                }
            }
        }

        heroElement.querySelector("div")?.appendChild(heroContent)

        container.appendChild(heroElement)
        val expertiseSection = Section(
            initialTitle = "Our Core Expertise",
            initialIsDivider = true
        )
        val expertiseElement = expertiseSection.buildHtml()

        val expertiseContainer = document.create.div {
            attributes["expertise-grid"] = ""
        }

        val expertiseItems = listOf(
            Pair(
                "Automotive",
                "ISO 26262, ASPICE, AUTOSAR — We excel at functional safety, powertrain control, battery management, and ADAS. From OTA updates to secure diagnostics, our solutions combine precision and compliance for tomorrow's vehicles."
            ),
            Pair(
                "MedTech & Industry 4.0",
                "With select experience in IEC 62304 and smart manufacturing, our team embeds connectivity (BLE, Wi-Fi, and industrial protocols) to drive secure, data-driven solutions. We help you meet strict compliance without sacrificing innovation."
            ),
            Pair(
                "Cloud & Application Engineering",
                "We build multi-platform apps with Kotlin, Flutter, or React — offline-first, secure, and integrated with your backend. Leveraging CI/CD pipelines (Jenkins, GitLab, GitHub Actions), we ensure smooth builds, rapid deployment, and scalable cloud architectures."
            )
        )

        expertiseItems.forEach { (title, description) ->
            val expertiseComponent = Expertise(initialName = title, initialDescription = description)
            val expertiseElement = expertiseComponent.buildHtml()
            expertiseContainer.appendChild(expertiseElement)
        }

        expertiseElement.querySelector("div")?.appendChild(expertiseContainer)

        container.appendChild(expertiseElement)
        val keywordSection = Section(
            initialIsDivider = false
        )
        val keywordElement = keywordSection.buildHtml()

        val keywords = listOf(
            "ISO 26262", "ASPICE", "AUTOSAR", "Model-Based Development",
            "MATLAB/Simulink", "TargetLink", "IBM Rhapsody",
            "Agile/V-Cycle", "Kotlin", "Flutter", "React",
            "Jenkins", "GitLab", "GitHub Actions", "C/C++",
            "BLE, Wi-Fi, NFC", "IEC 62304", "OTA Updates",
            "Cryptography", "Asynchronous Programming", "Performance Optimization"
        )

        val keywordStrip = KeywordStrip(initialKeywords = keywords)
        val keywordStripElement = keywordStrip.buildHtml()

        keywordElement.querySelector("div")?.appendChild(keywordStripElement)

        container.appendChild(keywordElement)
        val whySection = Section(
            initialTitle = "Why Work With Us?",
            initialIsDivider = true
        )
        val whyElement = whySection.buildHtml()

        val reasonsContainer = document.create.div {
            attributes["reasons-grid"] = ""
        }

        val reasons = listOf(
            Pair(
                "Proven Expertise",
                "20+ years delivering in complex compliance environments, with a deep record in automotive embedded."
            ),
            Pair("Continuous Improvement", "Agile methodologies, TDD, code reviews, and tight feedback loops."),
            Pair(
                "End-to-End Perspective",
                "From firmware to mobile apps to cloud backends—our holistic approach keeps everything in sync."
            ),
            Pair(
                "Scalable Collaboration",
                "Whether a feasibility study or multi-year engagement, we match your roadmap at every step."
            )
        )

        reasons.forEach { (title, description) ->
            val reasonComponent = Expertise(initialName = title, initialDescription = description)
            val reasonElement = reasonComponent.buildHtml()
            reasonsContainer.appendChild(reasonElement)
        }

        whyElement.querySelector("div")?.appendChild(reasonsContainer)

        val buttonContainer = document.create.div {
            button {
                attributes["class"] = "primary-button"
                +"Let's Build Something Great"
            }
        }

        whyElement.querySelector("div")?.appendChild(buttonContainer)

        container.appendChild(whyElement)
        val latestSection = Section(
            initialTitle = "Latest Updates",
            initialIsDivider = true
        )
        val latestElement = latestSection.buildHtml()

        val articles = listOf(
            ArticleCard(initialTitle = "Expanding Beyond Automotive", initialAuthor = "David", initialDate = "Apr 3, 2024"),
            ArticleCard(initialTitle = "How Can Marketing Help Your Business?", initialAuthor = "Allen", initialDate = "Apr 3, 2024")
        )

        val latestComponent = Latest(initialArticles = articles)
        val latestListElement = latestComponent.buildHtml()

        latestElement.querySelector("div")?.appendChild(latestListElement)

        container.appendChild(latestElement)

        return container
    }
}
