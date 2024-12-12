# woodpecker-plugin-template

woodpecker-plugin-template是woodpecker插件开发的模版，该模版中给出了woodpecker插件的常见开发范式示例，方便新手快速开发插件。

## 开发环境

推荐使用IntelliJ IDEA作为开发工具，Maven作为包管理工具，尽可能使用<=1.8的JDK版本。

## 插件介绍

Woodpecker插件内部具有Exploit、Poc、Helper、InfoDetector四种功能组件类型。

### Exploit

Exploit组件是woodpecker中漏洞的利用组件。

一个利用组件应尽量只面向一种利用方式，如果某个漏洞具有多种利用方式，则应对应开发多个Exploit模块。

一个简单的Exploit组件的[代码示例](./src/main/java/me/gv7/woodpecker/plugin/vuln/CVE_2024_0101/RCEExploit.java)。

### Poc

Poc是woodpecker中用于批量漏洞检测的扫描组件。

Poc的开发中应遵循<b>不影响系统稳定性</b>的条件下验证漏洞是否存在，且尽量避免产生利用痕迹，如果利用痕迹无法避免则尽可能在检测完成后清理痕迹。

一个简单的Poc组件的[代码示例](./src/main/java/me/gv7/woodpecker/plugin/vuln/CVE_2024_0101/Poc.java)。

### Helper

Helper组件是woodpecker中用于生成复杂载荷的组件。

一个简单的Helper组件的[代码示例](./src/main/java/me/gv7/woodpecker/plugin/helper/SomeHelper.java)。

### InfoDetector

InfoDetector模块是woodpecker中信息探测组件。

信息探测组件一般用于版本信息探测场景。

一个简单的InfoDetector组件的[代码示例](./src/main/java/me/gv7/woodpecker/plugin/infodetector/SomeAppInfoDetector.java)。

