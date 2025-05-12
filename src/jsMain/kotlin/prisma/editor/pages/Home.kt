package prisma.editor.pages

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.component.*
import prisma.editor.styles.Theme

/**
 * Creates the home page component that aggregates other components.
 */
class Home {
    /**
     * Creates the home page.
     */
    fun create(): HTMLElement {
        // Create the main container
        val container = document.create.div {
            attributes["home-page"] = ""
        }

        // Hero / Intro Section
        val heroSection = Section(
            title = "Embedded Software Solutions — Secure for Every Industry",
            isDivider = false
        )
        val heroElement = heroSection.preview()

        // Add content to the hero section
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

        // Find the content div in the section and append the hero content
        heroElement.querySelector("div")?.appendChild(heroContent)

        // Append hero section to the container
        container.appendChild(heroElement)

        // Core Expertise Section
        val expertiseSection = Section(
            title = "Our Core Expertise",
            isDivider = true
        )
        val expertiseElement = expertiseSection.preview()

        // Create a grid container for the expertise cards
        val expertiseContainer = document.create.div {
            attributes["expertise-grid"] = ""
        }

        // Add expertise cards
        val expertiseItems = listOf(
            Pair("Automotive", "ISO 26262, ASPICE, AUTOSAR — We excel at functional safety, powertrain control, battery management, and ADAS. From OTA updates to secure diagnostics, our solutions combine precision and compliance for tomorrow's vehicles."),
            Pair("MedTech & Industry 4.0", "With select experience in IEC 62304 and smart manufacturing, our team embeds connectivity (BLE, Wi-Fi, and industrial protocols) to drive secure, data-driven solutions. We help you meet strict compliance without sacrificing innovation."),
            Pair("Cloud & Application Engineering", "We build multi-platform apps with Kotlin, Flutter, or React — offline-first, secure, and integrated with your backend. Leveraging CI/CD pipelines (Jenkins, GitLab, GitHub Actions), we ensure smooth builds, rapid deployment, and scalable cloud architectures.")
        )

        expertiseItems.forEach { (title, description) ->
            val expertiseComponent = Expertise(title, description)
            val expertiseElement = expertiseComponent.preview()
            expertiseContainer.appendChild(expertiseElement)
        }

        // Append the grid to the section content div
        expertiseElement.querySelector("div")?.appendChild(expertiseContainer)

        // Append expertise section to the container
        container.appendChild(expertiseElement)

        // Keyword Strip Section
        val keywordSection = Section(
            isDivider = false
        )
        val keywordElement = keywordSection.preview()

        // Create keywords list
        val keywords = listOf(
            "ISO 26262", "ASPICE", "AUTOSAR", "Model-Based Development",
            "MATLAB/Simulink", "TargetLink", "IBM Rhapsody",
            "Agile/V-Cycle", "Kotlin", "Flutter", "React",
            "Jenkins", "GitLab", "GitHub Actions", "C/C++",
            "BLE, Wi-Fi, NFC", "IEC 62304", "OTA Updates",
            "Cryptography", "Asynchronous Programming", "Performance Optimization"
        )

        // Create KeywordStrip component with the keywords
        val keywordStrip = KeywordStrip(keywords)
        val keywordStripElement = keywordStrip.preview()

        // Append the keyword strip to the section content div
        keywordElement.querySelector("div")?.appendChild(keywordStripElement)

        // Append keyword section to the container
        container.appendChild(keywordElement)

        // Why Work With Us Section
        val whySection = Section(
            title = "Why Work With Us?",
            isDivider = true
        )
        val whyElement = whySection.preview()

        // Create a grid container for the reasons
        val reasonsContainer = document.create.div {
            attributes["reasons-grid"] = ""
        }

        // Add reasons
        val reasons = listOf(
            Pair("Proven Expertise", "20+ years delivering in complex compliance environments, with a deep record in automotive embedded."),
            Pair("Continuous Improvement", "Agile methodologies, TDD, code reviews, and tight feedback loops."),
            Pair("End-to-End Perspective", "From firmware to mobile apps to cloud backends—our holistic approach keeps everything in sync."),
            Pair("Scalable Collaboration", "Whether a feasibility study or multi-year engagement, we match your roadmap at every step.")
        )

        reasons.forEach { (title, description) ->
            val reasonComponent = Expertise(title, description)
            val reasonElement = reasonComponent.preview()
            reasonsContainer.appendChild(reasonElement)
        }

        // Append the grid to the section content div
        whyElement.querySelector("div")?.appendChild(reasonsContainer)

        // Add button
        val buttonContainer = document.create.div {
            button {
                attributes["class"] = "primary-button"
                +"Let's Build Something Great"
            }
        }

        // Append the button to the section content div
        whyElement.querySelector("div")?.appendChild(buttonContainer)

        // Append why section to the container
        container.appendChild(whyElement)

        // Latest Updates Section
        val latestSection = Section(
            title = "Latest Updates",
            isDivider = true
        )
        val latestElement = latestSection.preview()

        // Create article cards
        val articles = listOf(
            ArticleCard("Expanding Beyond Automotive", "David", "Apr 3, 2024"),
            ArticleCard("How Can Marketing Help Your Business?", "Allen", "Apr 3, 2024")
        )

        // Create Latest component with the articles
        val latestComponent = Latest(articles)
        val latestListElement = latestComponent.preview()


        // Append the latest list to the section content div
        latestElement.querySelector("div")?.appendChild(latestListElement)

        // Append latest section to the container
        container.appendChild(latestElement)

        return container
    }
}
