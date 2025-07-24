import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider
import java.io.File
import java.io.OutputStream

fun Project.ligntingTask(name: String, action: Task.() -> Unit): TaskProvider<Task?> {
    return tasks.register("lignting-$name") {
        group = "lignting"
        this.action()
    }
}

fun TaskProvider<Task?>.ligntingDescription(description: String) {
    this.configure {
        this.description = description
    }
}

fun Task.exec(
    commandLine: String,
    args: List<String> = listOf(),
    workingDir: String = "",
    standardOutput: OutputStream = System.out,
    action: Task.() -> Unit = {}
) {
    // will be deprecated in Gradle 9.0
    project.exec {
        this.workingDir = File("${project.projectDir.absolutePath}/${workingDir}")
        val command = listOf(commandLine) + args
        println("Executing command: ${command.joinToString(" ")}")
        this.commandLine = command
        this.standardOutput = standardOutput
        action()
    }
}