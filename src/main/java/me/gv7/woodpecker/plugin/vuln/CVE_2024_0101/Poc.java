package me.gv7.woodpecker.plugin.vuln.CVE_2024_0101;

import me.gv7.woodpecker.plugin.*;
import me.gv7.woodpecker.plugin.utils.WPSettings;
import me.gv7.woodpecker.requests.RawResponse;
import me.gv7.woodpecker.requests.Requests;

import java.util.HashMap;

/**
 * {@code Poc}是woodpecker中用于批量检测的扫描组件
 *
 * <p>在{@code Poc}的开发中应遵循<b>不影响系统稳定性</b>的条件下验证漏洞是否存在，且尽量避免产生利用痕迹，如果利用痕迹无法避免则尽可能在检测完成后清理痕迹。
 */
public class Poc implements IPoc {
    private IPluginHelper helper;

    public Poc(IPluginHelper helper) {
        this.helper = helper;
    }

    /**
     * 发起漏洞检测的入口
     *
     * @param target 攻击目标
     * @param output 打印日志
     * @return 返回检测的结果
     * @throws Throwable
     */
    @Override
    public IScanResult doVerify(ITarget target, IResultOutput output) throws Throwable {
        IScanResult scanResult = helper.createScanResult();
        String addr = target.getAddress();
        addr += addr.endsWith("/") ? "" : "/"; // 在url后补全/字符
        RawResponse response = Requests.
                get(addr + "test/command").
                verify(false). // 忽略目标的SSL证书错误
                        send();        // 发送POC
        if (response.statusCode() == 200) {
            scanResult.setExists(true);
            scanResult.setMsg(String.format("目标%s存在漏洞路由！请前往EXP模块进行利用！", target.getAddress()));
        } else {
            scanResult.setExists(false);
            scanResult.setMsg(String.format("目标%s未检测到漏洞！", target.getAddress()));
        }
        return scanResult;
    }
}
