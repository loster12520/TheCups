### 开发日志

### 2025-07-24

- 初步配置好项目，已保证能正常运行。
- 编写了前端的[gradle管理脚本](../../app/build.gradle.kts)。
- 编写了buildSrc用于方便后续脚本开发。

### 2025-07-25

- 前端：
    - 增添了[基础路由配置](../../app/appMain/src/views/router.tsx)
    - 增加了文章[关于前端分层必要性的简述](appoc/关于前端分层必要性的简述.md)

### 2025-07-29

- 前端：
    - 开始编写[command页面](../../app/appMain/src/views/pages/command/index.tsx)
- 后端：
    - 增添了数据驱动部分（详情请见api模块的com.lignting.data包下文件）
    - 添加了test-get和test-post接口，用于测试数据驱动功能
- 架构：
  - 后端确定技术选型为
    - ktor负责管理端口
    - sqlite作为数据存储
    - jimmer作为ORM

### 2025-07-30

- 前端：
  - 完成[command页面](../../app/appMain/src/views/pages/command/index.tsx)基础布局
  - 开始编写[keyboard组件](../../app/appMain/src/views/pages/command/components/keyboard-panel/index.tsx)用于在command页面添加键盘