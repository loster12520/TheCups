package com.lignting.basic.encapsulation

import react.CSSProperties
import react.dom.html.HTMLAttributes
import web.dom.Element

fun <T> T.createObject() = js("{}").unsafeCast<T>()

fun <T : Element> HTMLAttributes<T>.style(css: CSSProperties.() -> Unit) {
    val props = CSSProperties.createObject()
    css(props)
    this.style = props
}