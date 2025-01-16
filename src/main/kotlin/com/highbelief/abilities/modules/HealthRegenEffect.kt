package com.highbelief.abilities.modules

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.min

class HealthRegenEffect {
    /**
     * 플레이어의 체력을 일정 시간 동안 점진적으로 회복시킵니다.
     * @param player 효과를 받을 플레이어
     * @param totalDuration 전체 회복 지속 시간 (초 단위)
     * @param interval 체력 회복 간격 (틱 단위, 1초 = 20틱)
     * @param amount 회복량 (회복 간격마다 추가되는 체력)
     */
    fun apply(player: Player, totalDuration: Int, interval: Long, amount: Double) {
        val endTime = System.currentTimeMillis() + totalDuration * 1000

        val plugin = Bukkit.getPluginManager().getPlugin("HBAbility")
        if (plugin == null) {
            Bukkit.getLogger().warning("HBAbility 플러그인을 찾을 수 없습니다.")
            return
        }

        player.sendMessage("체력 회복 효과가 시작되었습니다!")

        object : BukkitRunnable() {
            override fun run() {
                // 시간이 초과되거나 플레이어가 오프라인 상태라면 종료
                if (System.currentTimeMillis() > endTime || !player.isOnline) {
                    cancel()
                    return
                }

                // 현재 체력을 최대 체력 이하로 제한하며 회복
                val currentHealth = player.health
                val maxHealth = player.maxHealth
                val newHealth = min(currentHealth + amount, maxHealth)

                player.health = newHealth
                player.sendMessage("체력이 ${amount}만큼 회복되었습니다! 현재 체력: $newHealth / $maxHealth")
            }
        }.runTaskTimer(plugin, 0L, interval)
    }
}
