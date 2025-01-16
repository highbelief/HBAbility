package com.highbelief.abilities.utility

import com.highbelief.abilities.Ability
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class SpeedBoostAbility(player: Player) : Ability(player, "속도 증가 능력", 15) { // 쿨타임 15초
    private val durationInSeconds = 10 // 속도 증가 지속 시간
    private val amplifier = 2 // 속도 증가 강도

    override fun performAbility(): Boolean {
        if (player.hasPotionEffect(PotionEffectType.SPEED)) {
            player.sendMessage("이미 속도 증가 상태입니다!")
            return false
        }

        applySpeedBoost()
        scheduleSpeedBoostRemoval()
        player.sendMessage("속도 증가 능력이 ${durationInSeconds}초 동안 발동되었습니다!")
        return true
    }

    /**
     * 플레이어에게 속도 증가 효과를 적용합니다.
     */
    private fun applySpeedBoost() {
        player.addPotionEffect(
            PotionEffect(PotionEffectType.SPEED, durationInSeconds * 20, amplifier - 1, false, false)
        )
    }

    /**
     * 일정 시간이 지난 후 속도 증가 효과를 제거합니다.
     */
    private fun scheduleSpeedBoostRemoval() {
        org.bukkit.Bukkit.getPluginManager().getPlugin("HBAbility")?.let {
            object : BukkitRunnable() {
                override fun run() {
                    if (player.hasPotionEffect(PotionEffectType.SPEED)) {
                        player.removePotionEffect(PotionEffectType.SPEED)
                        player.sendMessage("속도 증가 효과가 종료되었습니다.")
                    }
                }
            }.runTaskLater(
                it,
                (durationInSeconds * 20).toLong()
            )
        }
    }
}
