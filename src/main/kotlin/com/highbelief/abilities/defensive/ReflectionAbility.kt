package com.highbelief.abilities.defensive

import com.highbelief.abilities.Ability
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.scheduler.BukkitRunnable

class ReflectionAbility(player: Player) : Ability(player, "피해 반사 능력", 30), Listener { // 쿨타임 30초
    private val durationInSeconds = 10 // 반사 효과 지속 시간
    private val reflectionPercentage = 50 // 반사 비율 (%)

    private var isActive = false

    override fun performAbility(): Boolean {
        if (isActive) {
            player.sendMessage("이미 피해 반사 능력이 활성화되어 있습니다!")
            return false
        }

        isActive = true
        player.sendMessage("피해 반사 능력이 ${durationInSeconds}초 동안 활성화되었습니다!")

        // 지속 시간 후 효과 종료
        org.bukkit.Bukkit.getPluginManager().getPlugin("HBAbility")?.let {
            object : BukkitRunnable() {
                override fun run() {
                    isActive = false
                    player.sendMessage("피해 반사 능력이 종료되었습니다.")
                }
            }.runTaskLater(
                it,
                (durationInSeconds * 20).toLong()
            )
        }

        return true
    }

    @EventHandler
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        if (!isActive) return // 능력이 활성화되어 있지 않으면 무시

        val victim = event.entity as? Player ?: return
        if (victim != player) return // 피해자가 능력 소유자가 아니면 무시

        val attacker = event.damager
        val damage = event.damage

        // 반사 피해 계산
        val reflectedDamage = damage * (reflectionPercentage / 100.0)

        // 공격자에게 반사 피해 적용
        if (attacker is Player || attacker is org.bukkit.entity.Mob) {
            attacker.damage(reflectedDamage)
            player.sendMessage("공격을 반사하여 ${reflectedDamage}의 피해를 입혔습니다!")
        }
    }
}
