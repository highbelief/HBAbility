package com.highbelief.abilities.utility

import com.highbelief.abilities.Ability
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class InvisibilityAbility(player: Player) : Ability(player, "투명화 능력", 20) { // 쿨타임 20초
    private val durationInSeconds = 10 // 투명화 지속 시간

    override fun performAbility(): Boolean {
        if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
            player.sendMessage("이미 투명화 상태입니다!")
            return false
        }

        applyInvisibility()
        scheduleInvisibilityRemoval()
        player.sendMessage("투명화 능력이 ${durationInSeconds}초 동안 발동되었습니다!")
        return true
    }

    /**
     * 플레이어에게 투명화 효과를 적용합니다.
     */
    private fun applyInvisibility() {
        player.addPotionEffect(
            PotionEffect(PotionEffectType.INVISIBILITY, durationInSeconds * 20, 0, false, false)
        )
    }

    /**
     * 일정 시간이 지난 후 투명화 효과를 제거합니다.
     */
    private fun scheduleInvisibilityRemoval() {
        org.bukkit.Bukkit.getPluginManager().getPlugin("HBAbility")?.let {
            object : BukkitRunnable() {
                override fun run() {
                    if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                        player.removePotionEffect(PotionEffectType.INVISIBILITY)
                        player.sendMessage("투명화 효과가 종료되었습니다.")
                    }
                }
            }.runTaskLater(
                it,
                (durationInSeconds * 20).toLong()
            )
        }
    }
}
