package com.highbelief.scoreboard

import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.plugin.java.JavaPlugin

class BossBarManager(private val plugin: JavaPlugin) {
    private var bossBar: BossBar? = null

    // 보스바 생성
    fun createBossBar(title: String, durationInSeconds: Int) {
        bossBar = Bukkit.createBossBar(title, BarColor.BLUE, BarStyle.SOLID)
        bossBar?.progress = 1.0

        // 타이머를 통해 보스바 업데이트
        val totalTicks = durationInSeconds * 20 // 초를 틱으로 변환

        if (plugin != null) {
            Bukkit.getScheduler().runTaskTimer(
                plugin,
                object : Runnable {
                    var ticksLeft = totalTicks
                    override fun run() {
                        if (bossBar != null && ticksLeft > 0) {
                            bossBar?.progress = ticksLeft.toDouble() / totalTicks
                            ticksLeft--
                        } else {
                            bossBar?.isVisible = false
                            bossBar = null // 보스바를 null로 설정해 메모리 누수를 방지
                        }
                    }
                },
                0L,
                1L // 1틱마다 실행 (0.05초마다)
            )
        } else {
            plugin.logger.warning("HBAbility 플러그인을 찾을 수 없습니다.")
        }
    }

    // 보스바 제거
    fun removeBossBar() {
        bossBar?.removeAll()
        bossBar = null
    }
}
