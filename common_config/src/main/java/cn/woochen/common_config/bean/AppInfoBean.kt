package cn.woochen.common_config.bean

import cn.woochen.common_config.net.bean.DefaultValue

/**
 *
 * 类描述：
 * 创建人：woochen
 * 创建时间：2020-01-05 15:30
 * 修改备注：
 **/
 class AppInfoBean: DefaultValue {
        var packageTitle: String? = ""
        get() = defaultValue(field, "")
        var packageName: String? = ""
        get() = defaultValue(field, "")
        var isSystem: String? = ""
        get() = defaultValue(field, "")
        var firstInstallTime: Long? = 0
        get() = defaultValue(field, 0)
        var lastUpdateTime: Long = 0
        get() = defaultValue(field, 0)

    constructor(
        packageTitle: String?,
        packageName: String?,
        isSystem: String?,
        firstInstallTime: Long?,
        lastUpdateTime: Long
    ) {
        this.packageTitle = packageTitle
        this.packageName = packageName
        this.isSystem = isSystem
        this.firstInstallTime = firstInstallTime
        this.lastUpdateTime = lastUpdateTime
    }
}
