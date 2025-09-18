package com.lignting.view.pages

import com.lignting.basic.theme.compoments.Button
import react.FC
import react.dom.html.ReactHTML.div

val Home = FC {
    div {
        Button {
            key = "button1"
            text = "Click Me"
            onClick = {
                println("Button clicked!")
            }
        }
    }
}