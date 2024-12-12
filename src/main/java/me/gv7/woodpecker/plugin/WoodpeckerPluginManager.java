package me.gv7.woodpecker.plugin;

import me.gv7.woodpecker.plugin.helper.SomeHelper;
import me.gv7.woodpecker.plugin.infodetector.SomeAppInfoDetector;
import me.gv7.woodpecker.plugin.vuln.CVE_2024_0101_VulPlugin;

public class WoodpeckerPluginManager implements IPluginManager {
    @Override
    public void registerPluginManagerCallbacks(IPluginManagerCallbacks callbacks) {
        // 注册漏洞组件
        callbacks.registerVulPlugin(new CVE_2024_0101_VulPlugin());
        // 注册信息探测组件
        callbacks.registerInfoDetectorPlugin(new SomeAppInfoDetector());
        // 注册助手组件
        callbacks.registerHelperPlugin(new SomeHelper());
    }
}
