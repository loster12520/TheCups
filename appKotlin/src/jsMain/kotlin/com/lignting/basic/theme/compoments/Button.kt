package com.lignting.basic.theme.compoments

import com.lignting.basic.theme.Theme
import emotion.react.css
import react.FC
import react.Key
import react.Props
import react.dom.html.ReactHTML.button

external class ButtonProps(
    override var key: Key?,
    val text: String,
    val onClick: () -> Unit,
) : Props

val Button = FC<ButtonProps> { props ->
    button {
        css {
            color = Theme.background
            backgroundColor = Theme.primary
        }
        style
        key = props.key
        onClick = {
            props.onClick()
        }
        +props.text
    }
}