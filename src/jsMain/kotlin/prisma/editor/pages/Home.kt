package prisma.editor.pages

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.component.*

class Home {
    fun create(): HTMLElement {
        val container = document.create.div {
            attributes["home-page"] = ""
        }
        val heroSection = Section(
            title = "Embedded Software Solutions — Secure for Every Industry",
            isDivider = false
        )
        val heroElement = heroSection.preview()

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
            title = "Our Core Expertise",
            isDivider = true
        )
        val expertiseElement = expertiseSection.preview()

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
            val expertiseComponent = Expertise(title, description)
            val expertiseElement = expertiseComponent.preview()
            expertiseContainer.appendChild(expertiseElement)
        }

        expertiseElement.querySelector("div")?.appendChild(expertiseContainer)

        container.appendChild(expertiseElement)
        val keywordSection = Section(
            isDivider = false
        )
        val keywordElement = keywordSection.preview()

        val keywords = listOf(
            "ISO 26262", "ASPICE", "AUTOSAR", "Model-Based Development",
            "MATLAB/Simulink", "TargetLink", "IBM Rhapsody",
            "Agile/V-Cycle", "Kotlin", "Flutter", "React",
            "Jenkins", "GitLab", "GitHub Actions", "C/C++",
            "BLE, Wi-Fi, NFC", "IEC 62304", "OTA Updates",
            "Cryptography", "Asynchronous Programming", "Performance Optimization"
        )

        val keywordStrip = KeywordStrip(keywords)
        val keywordStripElement = keywordStrip.preview()

        keywordElement.querySelector("div")?.appendChild(keywordStripElement)

        container.appendChild(keywordElement)
        val whySection = Section(
            title = "Why Work With Us?",
            isDivider = true
        )
        val whyElement = whySection.preview()

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
            val reasonComponent = Expertise(title, description)
            val reasonElement = reasonComponent.preview()
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
            title = "Latest Updates",
            isDivider = true
        )
        val latestElement = latestSection.preview()

        val articles = listOf(
            ArticleCard("Expanding Beyond Automotive", "David", "Apr 3, 2024"),
            ArticleCard("How Can Marketing Help Your Business?", "Allen", "Apr 3, 2024")
        )

        val latestComponent = Latest(articles)
        val latestListElement = latestComponent.preview()

        latestElement.querySelector("div")?.appendChild(latestListElement)

        container.appendChild(latestElement)

        return container
    }
}
