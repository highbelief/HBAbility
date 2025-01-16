package com.highbelief.utils

import org.bukkit.Bukkit
import java.text.SimpleDateFormat
import java.util.Date

object Logger {
    private const val PREFIX = "[HBAbility]"
    private val dateFormat = ThreadLocal.withInitial { SimpleDateFormat("yyyy-MM-dd HH:mm:ss") }

    // 플러그인의 로거를 사용하도록 수정
    private val pluginLogger = Bukkit.getPluginManager().getPlugin("HBAbility")?.logger

    // 일반 로그 메시지
    fun info(message: String) {
        val timestamp = dateFormat.get().format(Date())
        pluginLogger?.info("$PREFIX [$timestamp] $message")
    }

    // 경고 메시지
    fun warning(message: String) {
        val timestamp = dateFormat.get().format(Date())
        pluginLogger?.warning("$PREFIX [$timestamp] $message")
    }

    // 오류 메시지
    fun error(message: String) {
        val timestamp = dateFormat.get().format(Date())
        pluginLogger?.severe("$PREFIX [$timestamp] $message")
    }
}
