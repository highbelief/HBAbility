package com.highbelief.abilities.modules

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.scheduler.BukkitRunnable

class ShieldEffect : Listener {
    private val activeShields = mutableMapOf<Player, Int>()

    /**
     * 플레이어에게 방어막 효과를 부여합니다.
     * @param player 방어막을 받을 플레이어
     * @param duration 방어막 지속 시간 (초 단위)
     * @param damageReduction 피해 감소 비율 (0.0 = 피해 없음, 1.0 = 모든 피해 감소)
     */
    fun apply(player: Player, duration: Int, damageReduction: Double) {
        if (activeShields.containsKey(player)) {
            player.sendMessage("이미 방어막 효과가 적용 중입니다.")
            return
        }

        // 방어막 적용
        activeShields[player] = (damageReduction * 100).toInt()
        player.sendMessage("방어막이 ${duration}초 동안 적용되었습니다!")

        // 방어막 해제 스케줄링
        org.bukkit.Bukkit.getPluginManager().getPlugin("HBAbility")?.let {
            object : BukkitRunnable() {
                override fun run() {
                    removeShield(player)
                }
            }.runTaskLater(
                it,
                (duration * 20).toLong()
            )
        }
    }

    /**
     * 방어막 효과를 제거합니다.
     * @param player 방어막을 제거할 플레이어
     */
    private fun removeShield(player: Player) {
        if (activeShields.remove(player) != null) {
            player.sendMessage("방어막 효과가 종료되었습니다.")
        }
    }

    /**
     * 방어막 효과가 적용 중인 플레이어의 피해를 감소시킵니다.
     * @param event 엔티티 피해 이벤트
     */
    @EventHandler
    fun onEntityDamage(event: EntityDamageEvent) {
        val player = event.entity as? Player ?: return
        val reductionPercentage = activeShields[player] ?: return

        // 피해를 감소시킴
        val reducedDamage = event.damage * (1 - (reductionPercentage / 100.0))
        event.damage = reducedDamage
        player.sendMessage("방어막으로 피해가 ${reductionPercentage}% 감소되었습니다!")
    }
}
