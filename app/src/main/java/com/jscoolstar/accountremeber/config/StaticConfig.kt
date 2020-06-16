package com.jscoolstar.accountremeber.config

object StaticConfig {
    val MAX_RETRY_TIMES =5
    var WRONGTIME_KEEP= 24*60*60*1000//5次密码错误之后，一天之后才可以再进行尝试
}