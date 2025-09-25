# Compact Java Dependency Manager

一个基于 JEP 512 紧凑源文件特性的 Java 依赖管理工具，支持从 Maven 仓库下载 JAR 依赖。

## 🌟 项目特性

- **JEP 512 紧凑源文件** - 使用现代 Java 紧凑文件格式，无需类声明
- **Maven 依赖下载** - 自动从 Maven 中央仓库或私有仓库下载 JAR 文件
- **环境变量配置** - 支持自定义 Maven 仓库地址
- **智能依赖管理** - 跳过已下载的文件，支持依赖列表注释
- **现代 Java 特性** - 使用 HttpClient、var 类型推断等现代 Java 特性

## 📁 项目结构

```
compact-app/
├── LibUp.java          # 依赖下载工具（紧凑 Java 文件）
├── Hello.java          # 示例应用程序（紧凑 Java 文件）
├── lib.txt             # 依赖列表文件
├── run-hello.sh        # 运行脚本
├── lib/                # 下载的 JAR 文件目录
├── JEP-512.md          # JEP 512 规范文档
└── README.md           # 本文档
```

## 🚀 快速开始

### 1. 配置依赖列表

编辑 `lib.txt` 文件，添加你需要的 Maven 依赖：

```
# 示例依赖
org.apache.commons:commons-lang3:3.12.0
com.google.guava:guava:31.1-jre
```

### 2. 下载依赖

```bash
java LibUp.java
```

### 3. 运行示例程序

```bash
./run-hello.sh
```

## 🛠️ 详细使用

### LibUp.java - 依赖下载工具

基于 JEP 512 紧凑源文件特性，无需类声明即可运行：

```bash
# 使用默认 Maven 中央仓库
java LibUp.java

# 使用私有 Maven 仓库
MAVEN_REPO=https://nexus.company.com/repository/maven-public java LibUp.java
```

#### 功能特性：
- 自动创建 `lib/` 目录
- 跳过已存在的 JAR 文件
- 支持 `#` 注释行
- 详细的下载进度显示
- 完整的错误处理

### Hello.java - 示例应用程序

演示如何使用下载的依赖：

```bash
# 直接运行（需要 lib/ 目录中有依赖 JAR）
java -cp "lib/*" Hello.java

# 或使用提供的脚本
./run-hello.sh
```

#### 演示功能：
- **Commons Lang3** - 字符串操作（反转、首字母大写、截断）
- **Guava** - 集合操作（转换、连接、映射）
- **现代 Java 特性** - var 类型推断、流式处理

### 运行脚本

`run-hello.sh` 提供了便捷的依赖管理和运行功能：

```bash
# 运行 Hello.java（自动检查依赖）
./run-hello.sh

# 清理依赖并重新下载
./run-hello.sh --clean

# 显示帮助信息
./run-hello.sh --help

# 使用私有仓库
MAVEN_REPO=https://nexus.company.com ./run-hello.sh
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

# 注释行以 # 开头
# org.slf4j:slf4j-api:1.7.36
```

## 🔧 环境变量

- `MAVEN_REPO` - 自定义 Maven 仓库地址（默认使用 Maven Central）

示例：
```bash
# 使用阿里云 Maven 镜像
export MAVEN_REPO=https://maven.aliyun.com/repository/central
java LibUp.java

# 使用公司私有仓库
MAVEN_REPO=https://nexus.company.com/repository/maven-public java LibUp.java
```

## 🏗️ 技术栈

### 核心特性
- **JEP 512 紧凑源文件** - 无需类声明的 Java 程序
- **JEP 511 模块导入** - `import module` 语法
- **现代 Java 特性** - var、HttpClient、Optional 等

### 使用的库
- **Commons Lang3** - Apache 通用工具库
- **Guava** - Google 核心 Java 库
- **Java HttpClient** - 现代 HTTP 客户端

### 开发工具
- **Java 25+** - 支持紧凑源文件的最新 Java 版本
- **Bash 脚本** - 跨平台运行支持

## 🎯 项目亮点

1. **简洁性** - 63 行代码完成功能完整的依赖下载器
2. **现代性** - 充分利用 Java 最新特性
3. **可维护性** - 代码结构清晰，异常处理完善
4. **可扩展性** - 支持自定义仓库，易于扩展
5. **教育性** - 展示 JEP 512 和现代 Java 编程范式

## 🚀 运行要求

- **Java 25 或更高版本** - 支持紧凑源文件特性
- **网络连接** - 用于下载 Maven 依赖
- **Bash 环境** - 用于运行脚本（可选）

## 📚 相关资源

- [JEP 512: Compact Source Files and Instance Main Methods](https://openjdk.org/jeps/512)
- [JEP 511: Module Import Declarations](https://openjdk.org/jeps/511)
- [Maven Central Repository](https://search.maven.org/)
- [Apache Commons Lang](https://commons.apache.org/proper/commons-lang/)
- [Google Guava](https://github.com/google/guava)

## 🤝 贡献

欢迎提交 Issue 和 Pull Request 来改进这个项目！

## 📄 许可证

本项目采用 [MIT 许可证](LICENSE) - 详见 [LICENSE](LICENSE) 文件。

MIT 许可证是一种宽松的开源许可证，允许：
- ✅ 商业使用
- ✅ 修改和分发
- ✅ 私人使用
- ✅ 专利使用

只需要保留版权声明和许可证声明即可。