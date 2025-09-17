package com.lignting.basic.theme

import web.cssom.Color

interface ThemeColor {
    val background: Color
    val primary: Color
    val primaryVariant: Color
    val secondary: Color
    val secondaryVariant: Color
    val surface: Color
    val error: Color
}

object LightTheme : ThemeColor {
    override val background: Color
        get() = Color("#d7ecff")
    override val primary: Color
        get() = Color("#8ab9f6")
    override val primaryVariant: Color
        get() = Color("#6aaafe")
    override val secondary: Color
        get() = Color("#ffaeea")
    override val secondaryVariant: Color
        get() = Color("#fb8ade")
    override val surface: Color
        get() = Color("#FFFFFF")
    override val error: Color
        get() = Color("#B00020")
}

val Theme: ThemeColor = LightTheme