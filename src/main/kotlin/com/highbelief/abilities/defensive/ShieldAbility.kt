package com.highbelief.abilities.defensive

import com.highbelief.abilities.Ability
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.scheduler.BukkitRunnable

class ShieldAbility(player: Player) : Ability(player, "방어막 능력", 25), Listener { // 쿨타임 25초
    private val durationInSeconds = 10 // 방어막 지속 시간
    private var isShieldActive = false

    override fun performAbility(): Boolean {
        if (isShieldActive) {
            player.sendMessage("이미 방어막이 활성화되어 있습니다!")
            return false
        }

        isShieldActive = true
        player.sendMessage("방어막이 ${durationInSeconds}초 동안 활성화되었습니다!")

        // 지속 시간 후 방어막 비활성화
        org.bukkit.Bukkit.getPluginManager().getPlugin("HBAbility")?.let {
            object : BukkitRunnable() {
                override fun run() {
                    isShieldActive = false
                    player.sendMessage("방어막 효과가 종료되었습니다.")
                }
            }.runTaskLater(
                it,
                (durationInSeconds * 20).toLong()
            )
        }

        return true
    }

    @EventHandler
    fun onEntityDamage(event: EntityDamageEvent) {
        // 방어막이 활성화된 상태에서만 효과 적용
        if (!isShieldActive) return

        val victim = event.entity as? Player ?: return
        if (victim != player) return // 피해자가 능력 소유자가 아니면 무시

        event.isCancelled = true // 피해 차단
        player.sendMessage("방어막이 피해를 차단했습니다!")
    }
}
