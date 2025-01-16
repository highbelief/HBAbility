package com.highbelief.abilities.defensive

import com.highbelief.abilities.Ability
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class AbsorptionAbility(player: Player) : Ability(player, "흡수 보호막 능력", 20) { // 쿨타임 20초
    private val absorptionAmount = 4 // 흡수 보호막 추가 체력 (하트 수)
    private val durationInSeconds = 15 // 보호막 지속 시간

    override fun performAbility(): Boolean {
        if (!applyAbsorption()) {
            player.sendMessage("흡수 보호막을 적용할 수 없습니다.")
            return false
        }

        // 일정 시간 후 보호막 해제
        org.bukkit.Bukkit.getPluginManager().getPlugin("HBAbility")?.let {
            object : BukkitRunnable() {
                override fun run() {
                    if (player.hasPotionEffect(PotionEffectType.ABSORPTION)) {
                        player.removePotionEffect(PotionEffectType.ABSORPTION)
                        player.sendMessage("흡수 보호막이 사라졌습니다.")
                    }
                }
            }.runTaskLater(
                it,
                (durationInSeconds * 20).toLong()
            )
        }

        player.sendMessage("흡수 보호막이 ${durationInSeconds}초 동안 적용되었습니다!")
        return true
    }

    /**
     * 플레이어에게 흡수 보호막을 적용합니다.
     * @return 적용 성공 여부
     */
    private fun applyAbsorption(): Boolean {
        if (player.hasPotionEffect(PotionEffectType.ABSORPTION)) {
            return false // 이미 흡수 보호막이 있는 경우 적용 불가
        }

        player.addPotionEffect(
            PotionEffect(PotionEffectType.ABSORPTION, durationInSeconds * 20, absorptionAmount / 2 - 1)
        )
        return true
    }
}
