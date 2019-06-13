package com.example.pro.fra.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @ClassName: AppConfigUtil
 * @Description: 获取服务配置的工具
 * @Author: ccz
 * @Date: 2019/2/21 10:07
 */
@Component
public class AppConfig {

    public static String serverName;

    public static String serverId;

    public static String platformId;

    public static String platformName;

    @Value("${server.name}")
    public void setServerName(String serverName) {
        AppConfig.serverName = serverName;
    }
    @Value("${server.id}")
    public void setServerId(String serverId) {
        AppConfig.serverId = serverId;
    }

    @Value("${platform.id}")
    public void setPlatformId(String platformId) {
        AppConfig.platformId = platformId;
    }
    @Value("${platform.name}")
    public void setPlatformName(String platformName) {
        AppConfig.platformName = platformName;
    }
}
