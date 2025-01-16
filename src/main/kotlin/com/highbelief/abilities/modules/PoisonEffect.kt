package com.highbelief.abilities.modules

import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class PoisonEffect {
    /**
     * 특정 엔티티에게 독 효과를 부여합니다.
     * @param entity 효과를 받을 엔티티
     * @param duration 효과 지속 시간 (초 단위)
     * @param amplifier 독 효과 강도 (0 = 기본 강도)
     * @param interval 독 대미지 간격 (틱 단위, 1초 = 20틱)
     */
    fun apply(entity: Entity, duration: Int, amplifier: Int = 0, interval: Long = 20L) {
        val endTime = System.currentTimeMillis() + duration * 1000

        val plugin = org.bukkit.Bukkit.getPluginManager().getPlugin("HBAbility")
        if (plugin == null) {
            org.bukkit.Bukkit.getLogger().warning("HBAbility 플러그인을 찾을 수 없습니다.")
            return
        }

        object : BukkitRunnable() {
            override fun run() {
                // 시간이 초과되거나 엔티티가 유효하지 않으면 작업 종료
                if (System.currentTimeMillis() > endTime || !entity.isValid) {
                    cancel()
                    return
                }

                // 독 효과 적용
                if (entity is Player) {
                    entity.addPotionEffect(PotionEffect(PotionEffectType.POISON, (duration * 20).toInt(), amplifier, false, false))
                    entity.sendMessage("독 효과가 ${duration}초 동안 적용되었습니다!")
                } else if (entity is LivingEntity) {
                    // 플레이어가 아닌 경우 지속 대미지 적용
                    val currentHealth = entity.health
                    val newHealth = (currentHealth - 1).coerceAtLeast(0.0)
                    entity.health = newHealth

                    if (newHealth <= 0) {
                        cancel()
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, interval)
    }
}
