package me.gv7.woodpecker.plugin.utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * 一个高效获取用户输入的参数的封装
 */
public class WPSettings {
    private Map<String, Object> _settings;

    public WPSettings(Map<String, Object> settings) {
        this._settings = settings;
    }

    /**
     * 将用户输入的字符串转换为字符串并返回。
     * @param key 参数Key
     * @return 转换结果，如果转换失败将返回{@code null}
     */
    public String getString(String key) {
        return getString(key, null);
    }

    /**
     * 将用户输入的字符串转换为字符串并返回。
     * @param key 参数Key
     * @param defaultValue 默认值
     * @return 转换结果，如果转换失败将返回{@code defaultValue}
     */
    public String getString(String key, String defaultValue) {
        if (_settings.containsKey(key)) {
            return _settings.get(key).toString();
        } else {
            return defaultValue;
        }
    }

    /**
     * 将用户输入的字符串转换为{@code int}并返回。
     * @param key 参数Key
     * @return 转换结果，如果转换失败将返回{@code null}
     */
    public int getInteger(String key) {
        return getInteger(key, null);
    }

    /**
     * 将用户输入的内容转换为布尔类型返回
     * @param key 参数Key
     * @param defaultValue 默认值
     * @return 转换结果，如果转换失败将返回{@code defaultValue}
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        if (_settings.containsKey(key)) {
            String obj = _settings.get(key).toString().toLowerCase();
            if (obj.equals("true") || obj.equals("1")) {
                return true;
            } else if (obj.equals("false") || obj.equals("0")) {
                return false;
            } else {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    /**
     * 将用户输入的字符串转换为{@code int}并返回。
     * @param key 参数Key
     * @param defaultValue 默认值
     * @return 转换结果，如果转换失败将返回{@code defaultValue}
     */
    public Integer getInteger(String key, Integer defaultValue) {
        if (_settings.containsKey(key)) {
            return Integer.parseInt(_settings.get(key).toString());
        } else {
            return defaultValue;
        }
    }

    /**
     * 将用户输入的字符串作为路径，读取该路径对应的文件内容并返回。
     * @param key 参数Key
     * @return 文件内容，如果文件读取失败将返回{@code null}
     */
    public byte[] getFileContent(String key) {
        if (_settings.containsKey(key)) {
            try {
                return Files.readAllBytes(Paths.get(_settings.get(key).toString()));
            } catch (Exception ex) {
                return null;
            }
        } else {
            return null;
        }
    }
}
