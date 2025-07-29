/**
 * test task
 */
ligntingTask("test") {
    doLast {
        println("Hello world!")
    }
}.ligntingDescription("a test task")

/**
 * install task
 * yarn install
 */
ligntingTask("yarnInstall") {
    doLast {
        exec(
            workingDir = "appMain",
            commandLine = "yarn.cmd",
            args = listOf("install"),
        )
    }
}.ligntingDescription("Install dependencies using yarn")

/**
 * dev task
 * yarn dev
 */
ligntingTask("yarnDev") {
    doLast {
        exec(
            workingDir = "appMain",
            commandLine = "yarn.cmd",
            args = listOf("dev"),
        )
    }
}.ligntingDescription("Install dependencies using yarn")