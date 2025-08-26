## goKt

### 这是什么？

goKt是一个新项目企划，旨在编写一门新语言，以Kotlin作为蓝本，目标是生成Go代码。

### 语法详情对比

#### Hello World

```go
package main

import "fmt"

func main() {
    fmt.Println("Hello, World!")
}
```

```goKt
fun main() {
    println("Hello, World!")
}
```

#### 变量与常量

```go
const x int = 10
var y string = "Hello"
```

```goKt
val x: Int = 10
var y: String = "Hello"
```

#### 函数定义

```go
func add(a int, b int) int {
    return a + b
}
```

```goKt
fun add(a: Int, b: Int): Int {
    return a + b
}
```

#### 条件语句

```go
if x > 0 {
    fmt.Println("Positive")
} else {
    fmt.Println("Non-positive")
}
```

```goKt
if (x > 0) {
    println("Positive")
} else {
    println("Non-positive")
}
```
#### 循环

```go
for i := 0; i < 5; i++ {
    fmt.Println(i)
}
```

```goKt
for (i in 0 until 5) {
    println(i)
}
```

#### 结构体与类

```go
type Person struct {
    Name string
    Age  int
}
func (p Person) Greet() {
    fmt.Printf("Hello, my name is %s and I am %d years old.\n", p.Name, p.Age)
}
```

```goKt
class Person(val name: String, val age: Int) {
    fun greet() {
        println("Hello, my name is $name and I am $age years old.")
    }
}
```