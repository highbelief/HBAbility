package com.highbelief.abilities.modules

import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class SpeedEffect {
    /**
     * 플레이어에게 속도 증가 효과를 부여합니다.
     * @param player 효과를 받을 플레이어
     * @param duration 효과 지속 시간 (초 단위)
     * @param amplifier 속도 증가 강도 (0 = 기본 강도)
     * @param afterEffect 메시지 출력 또는 추가 동작 (효과 종료 후 실행)
     */
    fun apply(player: Player, duration: Int, amplifier: Int = 0, afterEffect: (() -> Unit)? = null) {
        // 속도 증가 효과 적용
        player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, duration * 20, amplifier))
        player.sendMessage("이동 속도가 ${duration}초 동안 증가했습니다!")

        // 효과 종료 후 동작 스케줄링
        if (afterEffect != null) {
            org.bukkit.Bukkit.getPluginManager().getPlugin("HBAbility")?.let {
                object : BukkitRunnable() {
                    override fun run() {
                        afterEffect()
                        player.sendMessage("속도 증가 효과가 종료되었습니다.")
                    }
                }.runTaskLater(
                    it,
                    (duration * 20).toLong()
                )
            }
        }
    }
}
