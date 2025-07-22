import kotlinx.browser.document
import react.*
import react.dom.html.ReactHTML.h1
import react.dom.client.createRoot
import web.dom.Element

fun main() {
    val container: Element = document.getElementById("root") as? Element ?: error("Couldn't find root container!")
    createRoot(container).render(Fragment.create {
        h1 {
            +"Hello, React+Kotlin/JS!"
        }
    })
}