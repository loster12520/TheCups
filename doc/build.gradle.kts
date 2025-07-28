/**
 * generated directory.md files for all the modules
 */
ligntingTask("directoryGeneration") {
    val dirs = listOf("/app")
    doLast {
        dirs.forEach {
            val path = "/document/$it"
            val file = File("$path/directory.md")
            val dir = File("$path/doc")
            dir.listFiles()
                .filter { it.isFile && it.extension == "md" }
                .joinToString("/n") {
                    "[${it.name}](${it.path})"
                }.let {
                    file.writeText(it)
                }
            
        }
    }
}.ligntingDescription("Generate document")