val groupName = "lignting"

/**
 * 封装自定义task
 */
fun ligntingTask(name: String, action: Task.() -> Unit) {
    tasks.register("$groupName-$name") {
        group = groupName
        this.action()
    }
}

/**
 * install task
 * yarn install
 */
ligntingTask("install"){
    doLast {
        exec {
            commandLine("yarn", "install")
            workingDir = project.projectDir
        }
    }
}

/**
 * test task
 */
ligntingTask("test") {
    println("test")
}