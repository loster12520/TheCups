package com.lignting.basic.theme.compoments

import com.lignting.basic.encapsulation.style
import com.lignting.basic.theme.Theme
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import web.cssom.Border

external interface ButtonProps : Props {
    var text: String
    var onClick: () -> Unit
}

val Button = FC<ButtonProps> { props ->
    button {
//        css {
//            color = Theme.background
//            backgroundColor = Theme.primary
//            border = "none".unsafeCast<Border>()
//        }
        style {
            color = Theme.background
            backgroundColor = Theme.primary
            border = "none".unsafeCast<Border>()
        }
        key = props.key
        onClick = {
            props.onClick()
        }
        +props.text
    }
}