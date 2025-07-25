ligntingTask("directoryGeneration") {
    val dirs = listOf("/app")
    doLast {
        dirs.forEach {
            val path = "/document/$it"
            val file = File("$path/directory.md")
            val dir = File("$path/doc")
            dir.listFiles().forEach {
            
            }
        }
    }
}