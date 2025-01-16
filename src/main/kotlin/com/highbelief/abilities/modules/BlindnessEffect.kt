package com.highbelief.abilities.modules

import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class BlindnessEffect {
    /**
     * 플레이어에게 실명 효과를 적용합니다.
     * @param player 효과를 받을 플레이어
     * @param duration 효과 지속 시간 (틱 단위, 20틱 = 1초)
     * @param amplifier 효과 강도 (0 = 기본 강도)
     */
    fun apply(player: Player, duration: Int, amplifier: Int) {
        player.addPotionEffect(
            PotionEffect(PotionEffectType.BLINDNESS, duration, amplifier)
        )
        player.sendMessage("실명 효과가 ${duration / 20}초 동안 적용되었습니다!")
    }
}
