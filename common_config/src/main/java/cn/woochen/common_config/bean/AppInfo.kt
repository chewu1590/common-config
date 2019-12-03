package cn.woochen.common_config.bean

import cn.woochen.common_config.net.bean.DefaultValue

class AppInfo : DefaultValue {
    var appName: String? = ""
        get() = defaultValue(field, "")
    var packageName: String? = ""
        get() = defaultValue(field, "")
    var versionName: String? = ""
        get() = defaultValue(field, "")
    var versionCode: Int? = 0
        get() = defaultValue(field, 0)
    var firstInstallTime: Long? = 0
        get() = defaultValue(field, 0)
    var lastUpdateTime: Long = 0
        get() = defaultValue(field, 0)

    constructor(
        appName: String?,
        packageName: String?,
        versionName: String?,
        versionCode: Int?,
        firstInstallTime: Long?,
        lastUpdateTime: Long
    ) {
        this.appName = appName
        this.packageName = packageName
        this.versionName = versionName
        this.versionCode = versionCode
        this.firstInstallTime = firstInstallTime
        this.lastUpdateTime = lastUpdateTime
    }
}