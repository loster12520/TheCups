import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider
import org.gradle.process.ExecSpec
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
    errorOutput: OutputStream = System.err
) {
    val command = listOf(commandLine) + args
    val pb = ProcessBuilder(command)
    if (workingDir.isNotEmpty()) {
        pb.directory(File(project.projectDir, workingDir))
    }
    pb.redirectErrorStream(true)
    val process = pb.start()
    
    // 输出流转发
    Thread {
        process.inputStream.copyTo(standardOutput)
    }.start()
    Thread {
        process.errorStream.copyTo(errorOutput)
    }.start()
    
    // 允许输入
    Thread {
        System.`in`.copyTo(process.outputStream)
    }.start()
    
    // 构建结束时强制杀死进程
    Runtime.getRuntime().addShutdownHook(Thread {
        process.destroyForcibly()
    })
    
    // 等待进程结束
    process.waitFor()
}