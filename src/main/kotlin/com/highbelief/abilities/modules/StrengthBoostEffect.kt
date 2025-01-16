package com.highbelief.abilities.modules

import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class StrengthBoostEffect {
    /**
     * 플레이어에게 공격력 증가 효과를 부여합니다.
     * @param player 효과를 받을 플레이어
     * @param duration 효과 지속 시간 (초 단위)
     * @param amplifier 공격력 증가 강도 (0 = 기본 강도)
     * @param afterEffect 메시지 출력 또는 추가 동작 (효과 종료 후 실행)
     */
    fun apply(player: Player, duration: Int, amplifier: Int = 0, afterEffect: (() -> Unit)? = null) {
        // 공격력 증가 효과 적용
        player.addPotionEffect(PotionEffect(PotionEffectType.STRENGTH, duration * 20, amplifier))
        player.sendMessage("공격력이 ${duration}초 동안 증가했습니다!")

        // 효과 종료 후 동작 스케줄링
        if (afterEffect != null) {
            val plugin = org.bukkit.Bukkit.getPluginManager().getPlugin("HBAbility")
            if (plugin == null) {
                org.bukkit.Bukkit.getLogger().warning("HBAbility 플러그인을 찾을 수 없습니다.")
                return
            }

            object : BukkitRunnable() {
                override fun run() {
                    afterEffect()
                    player.sendMessage("공격력 증가 효과가 종료되었습니다.")
                }
            }.runTaskLater(plugin, (duration * 20).toLong())
        }
    }
}
