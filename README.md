# Compact Java Dependency Manager

基于 JEP 512 紧凑源文件的 Java 依赖管理工具，支持从 Maven 仓库下载 JAR 依赖。

## 🌟 项目特性

- **JEP 512 紧凑源文件** - 现代 Java 紧凑文件格式，无需类声明
- **Maven 依赖下载** - 自动从 Maven 中央仓库或私有仓库下载 JAR
- **双版本支持** - Java 紧凑文件 + Shell 脚本版本
- **智能依赖管理** - 跳过已下载文件，支持依赖列表注释
- **环境变量配置** - 自定义 Maven 仓库地址

## 📁 项目结构

```
compact-app/
├── LibUp.java          # Java 版本依赖下载器
├── lib-up.sh           # Shell 版本依赖下载器
├── Hello.java          # 示例应用程序
├── lib.txt             # 依赖列表文件
├── run-hello.sh        # 运行脚本
├── lib/                # 下载的 JAR 目录
├── JEP-512.md          # JEP 512 规范文档
└── README.md           # 本文档
```

## 🚀 快速开始

### 1. 配置依赖列表

编辑 `lib.txt` 文件，添加 Maven 依赖：

```
# 示例依赖
org.apache.commons:commons-lang3:3.12.0
com.google.guava:guava:31.1-jre
```

### 2. 下载依赖

```bash
# Java 版本
java LibUp.java

# Shell 版本（推荐，更快）
./lib-up.sh
```

### 3. 运行示例

```bash
./run-hello.sh
```

## 🛠️ 详细使用

### 依赖下载工具

#### Java 版本
```bash
java LibUp.java
MAVEN_REPO=https://nexus.company.com java LibUp.java
```

#### Shell 版本（推荐）
```bash
./lib-up.sh
MAVEN_REPO=https://nexus.company.com ./lib-up.sh
```

功能特性：
- 自动创建 `lib/` 目录
- 跳过已存在的 JAR 文件
- 支持 `#` 注释行
- 详细的下载进度显示
- 完整的错误处理

### Hello.java 示例程序

```bash
# 直接运行
java -cp "lib/*" Hello.java

# 或使用脚本
./run-hello.sh
```

演示功能：
- **Commons Lang3** - 字符串操作（反转、首字母大写、截断）
- **Guava** - 集合操作（转换、连接、映射）
- **现代 Java 特性** - var 类型推断、流式处理

### 运行脚本

```bash
./run-hello.sh          # 运行 Hello.java（自动检查依赖）
./run-hello.sh --clean  # 清理并重新下载依赖
./run-hello.sh --help   # 显示帮助信息
```

## 📋 依赖格式

`lib.txt` 文件使用标准的 Maven 依赖格式：

```
groupId:artifactId:version
```

示例：
```
# Apache Commons 工具库
org.apache.commons:commons-lang3:3.12.0

# Google Guava 集合工具
com.google.guava:guava:31.1-jre
```

## 🔧 环境变量

- `MAVEN_REPO` - 自定义 Maven 仓库地址（默认使用 Maven Central）

示例：
```bash
# 使用阿里云 Maven 镜像（Java 版本）
export MAVEN_REPO=https://maven.aliyun.com/repository/central
java LibUp.java

# 使用公司私有仓库（Shell 版本）
MAVEN_REPO=https://nexus.company.com ./lib-up.sh
```

## 🏗️ 技术栈

### 核心特性
- **JEP 512 紧凑源文件** - 无需类声明的 Java 程序
- **JEP 511 模块导入** - `import module` 语法
- **现代 Java 特性** - var、HttpClient、Optional 等

### 开发工具
- **Java 25+** - 支持紧凑源文件的最新 Java 版本
- **Bash 脚本** - 跨平台运行支持
- **curl** - Shell 版本依赖（标准工具）

## 📊 版本对比

| 特性 | Java 版本 | Shell 版本 |
|------|-----------|------------|
| 启动速度 | 需要 JVM 启动 | 立即执行 |
| 依赖 | Java 25+ | Bash + curl |
| 错误处理 | Java 异常机制 | Shell 错误检测 |
| 跨平台 | 支持 Java 25 的平台 | Unix/Linux/macOS |
| 代码行数 | 63 行 | 45 行 |
| 执行方式 | `java LibUp.java` | `./lib-up.sh` |

## 🎯 项目亮点

1. **简洁性** - 45/63 行代码完成功能完整的依赖下载器
2. **现代性** - 充分利用 Java 最新特性
3. **灵活性** - Java 和 Shell 双版本，适应不同场景
4. **教育性** - 展示 JEP 512 和现代 Java 编程范式

## 🚀 运行要求

- **Java 25+** - 支持紧凑源文件特性（仅 Java 版本需要）
- **网络连接** - 下载 Maven 依赖
- **Bash 环境** - 运行脚本
- **curl** - Shell 版本依赖（通常已预装）

## 📚 相关资源

- [JEP 512: Compact Source Files](https://openjdk.org/jeps/512)
- [JEP 511: Module Import Declarations](https://openjdk.org/jeps/511)
- [Maven Central Repository](https://search.maven.org/)

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

## 📄 许可证

[MIT 许可证](LICENSE) - 允许商业使用、修改和私人使用。