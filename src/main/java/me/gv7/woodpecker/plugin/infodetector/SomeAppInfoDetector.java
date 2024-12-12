package me.gv7.woodpecker.plugin.infodetector;

import me.gv7.woodpecker.plugin.*;
import me.gv7.woodpecker.plugin.utils.WPSettings;
import me.gv7.woodpecker.requests.RawResponse;
import me.gv7.woodpecker.requests.Requests;

import java.util.*;

/**
 * {@code InfoDetector}模块是woodpecker中信息探测组件
 *
 * <p>信息探测组件一般用于版本信息探测场景。
 */
public class SomeAppInfoDetector implements InfoDetectorPlugin {

    private static InfoDetectorPluginCallbacks callbacks;
    private static IPluginHelper helper;

    @Override
    public void InfoDetectorPluginMain(InfoDetectorPluginCallbacks callbacks) {
        this.callbacks = callbacks;
        this.helper = callbacks.getPluginHelper();
        callbacks.setInfoDetectorPluginAuthor("<插件作者ID>");
        callbacks.setInfoDetectorPluginName("SomeApp接口信息探测");
        callbacks.setInfoDetectorPluginDescription("通过检测路径来判断是否存在信息暴露");
        callbacks.setInfoDetectorPluginVersion("0.1.0");
        callbacks.registerInfoDetector(new ArrayList<InfoDetector>() {{
            add(new VersionDetector());
        }});
    }

    public static class VersionDetector implements InfoDetector {
        @Override
        public String getInfoDetectorTabCaption() {
            return "Get Version";
        }

        @Override
        public IArgsUsageBinder getInfoDetectorCustomArgs() {
            IArgsUsageBinder argsUsageBinder = helper.createArgsUsageBinder();
            List<IArg> args = new ArrayList<>();
            final IArg application = helper.createArg();
            application.setName("application");
            application.setRequired(true);
            application.setDefaultValue("api");
            args.add(application);
            argsUsageBinder.setArgsList(args);
            return argsUsageBinder;
        }

        @Override
        public LinkedHashMap<String, String> doDetect(ITarget target, Map<String, Object> map, IResultOutput output) throws Throwable {
            WPSettings settings = new WPSettings(map);
            LinkedHashMap<String, String> result = new LinkedHashMap<>();
            String addr = target.getAddress();
            addr += addr.endsWith("/") ? "" : "/"; // 在url后补全/字符
            HashMap<String, String> postBody = new HashMap<>();
            postBody.put("application", settings.getString("application"));
            output.startPrintln("发送EXP...");
            RawResponse response = Requests.
                    post(addr + "test/command").
                    verify(false). // 忽略目标的SSL证书错误
                            body(postBody).// 设置POST请求的Body
                            send();        // 发送请求
            if (response.statusCode() == 200) {
                output.successPrintln("获取成功");
                result.put("status_code", String.valueOf(response.statusCode()));
            } else {
                output.errorPrintln("获取失败");
            }
            return result;
        }

    }
}
