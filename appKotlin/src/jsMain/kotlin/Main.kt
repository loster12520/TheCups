import emotion.react.css
import kotlinx.browser.document
import react.Fragment
import react.create
import react.dom.client.createRoot
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import web.cssom.NamedColor
import web.dom.Element

fun main() {
    val container = document.getElementById("root") as Element? ?: error("Couldn't find root container!")
    createRoot(container).render(Fragment.create {
        div {
            css {
                color = NamedColor.red
            }
            h1 {
                +"Hello, React+Kotlin/JS!"
            }
        }
    })
}