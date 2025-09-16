package example.com.lignting.view.pages.com.lignting.view

import com.lignting.view.pages.Home
import react.FC
import react.create
import react.router.RouteObject
import react.router.RouterProvider
import react.router.dom.createBrowserRouter

data class BasicRoute(
    val path: String,
    val element: FC<*>? = null,
)

val routers = listOf(
    BasicRoute(
        path = "/",
        element = Home,
    ),
    BasicRoute(
        path = "home",
        element = Home,
    ),
).map {
    RouteObject(
        path = "${it.path}",
        element = it.element?.create(),
    )
}.toTypedArray().let { createBrowserRouter(it) }

val Router = FC {
    RouterProvider {
        router = routers
    }
}