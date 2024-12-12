package me.gv7.woodpecker.plugin.vuln;

import me.gv7.woodpecker.plugin.*;
import me.gv7.woodpecker.plugin.vuln.CVE_2024_0101.*;

import java.util.ArrayList;

/**
 * 漏洞的入口类，用于指定漏洞的基础信息、Poc、Exploit。
 */
public class CVE_2024_0101_VulPlugin implements IVulPlugin {
    public static IVulPluginCallbacks callbacks;
    public static IPluginHelper helper;

    @Override
    public void VulPluginMain(IVulPluginCallbacks _callbacks) {
        callbacks = _callbacks;
        helper = callbacks.getPluginHelper();
        // 漏洞插件名称
        callbacks.setVulPluginName("SomeApp CVE-2024-0101 exploit");
        // 漏洞插件版本
        callbacks.setVulPluginVersion("0.1.0");
        // 漏洞插件作者的ID
        callbacks.setVulPluginAuthor("<插件作者ID>");
        // 漏洞CVSS分数
        callbacks.setVulCVSS(9.8);
        // 漏洞名称，将展示在woodpecker的漏洞列表中
        callbacks.setVulName("SomeApp反序列化漏洞");
        // 漏洞编号
        callbacks.setVulId("CVE-2024-0101");
        // 漏洞作者ID
        callbacks.setVulAuthor("<漏洞作者ID>");
        // 简单描述漏洞原理和利用成功后的成果
        callbacks.setVulDescription("该漏洞可在未经身份验证，通过网络访问SomeApp服务器。成功的攻击可以接管操作系统");
        // 漏洞分类：Bypass, FileUpload, RCE...
        callbacks.setVulCategory("RCE");
        // 漏洞影响范围
        callbacks.setVulScope("<=10.2.3");
        // 漏洞纰漏时间
        callbacks.setVulDisclosureTime("2024-01-02");
        // 漏洞影响的产品名称
        callbacks.setVulProduct("SomeApp");
        // 漏洞严重性
        callbacks.setVulSeverity("high");
        // 注册漏洞Poc
        callbacks.registerPoc(new Poc(helper));
        // 注册漏洞Exploit
        callbacks.registerExploit(new ArrayList<IExploit>() {{
            add(new RCEExploit(helper));
            add(new FileUploadExploit(helper));
        }});
        // 注册漏洞Payload生成器
        callbacks.registerPayloadGenerator(new ArrayList<IPayloadGenerator>() {{
            add(new XmlPayloadGenerator(helper));
        }});
    }
}
