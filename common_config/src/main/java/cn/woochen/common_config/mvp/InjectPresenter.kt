package cn.woochen.common_config.mvp

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * presenter注解
 * @author woochen123
 * @time 2018/1/19 10:24
 * @desc
 */
@Target(AnnotationTarget.FIELD)
@Retention(RetentionPolicy.RUNTIME)
annotation class InjectPresenter
