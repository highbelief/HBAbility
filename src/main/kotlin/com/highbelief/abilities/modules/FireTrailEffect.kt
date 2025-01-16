package com.highbelief.abilities.modules

import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class FireTrailEffect {
    /**
     * 플레이어가 일정 시간 동안 이동할 때 발밑에 불길을 생성합니다.
     * @param player 효과를 받을 플레이어
     * @param duration 효과 지속 시간 (초 단위)
     * @param interval 불길 생성 간격 (틱 단위, 1초 = 20틱)
     */
    fun apply(player: Player, duration: Int, interval: Long) {
        val endTime = System.currentTimeMillis() + duration * 1000

        org.bukkit.Bukkit.getPluginManager().getPlugin("HBAbility")?.let {
            object : BukkitRunnable() {
                override fun run() {
                    // 시간이 초과되면 작업 종료
                    if (System.currentTimeMillis() > endTime || !player.isOnline) {
                        cancel()
                        return
                    }

                    // 플레이어의 발밑 위치에 불길 생성
                    val location = player.location.add(0.0, -1.0, 0.0)
                    createFire(location)
                }
            }.runTaskTimer(
                it,
                0L,
                interval
            )
        }
    }

    /**
     * 특정 위치에 불길을 생성합니다.
     * @param location 불길을 생성할 위치
     */
    private fun createFire(location: Location) {
        val block = location.block
        if (!block.type.isSolid) {
            block.type = org.bukkit.Material.FIRE
        }
    }
}
