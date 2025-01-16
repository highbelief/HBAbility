package com.highbelief.abilities.modules

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class IceTrailEffect {
    /**
     * 플레이어가 일정 시간 동안 이동할 때 발밑에 얼음 블록을 생성합니다.
     * @param player 효과를 받을 플레이어
     * @param duration 효과 지속 시간 (초 단위)
     * @param interval 얼음 생성 간격 (틱 단위, 1초 = 20틱)
     * @param temporary 얼음을 일정 시간 후 제거할지 여부
     * @param removalDelay 얼음 제거 지연 시간 (초 단위, `temporary`가 true일 때만 적용)
     */
    fun apply(player: Player, duration: Int, interval: Long, temporary: Boolean = false, removalDelay: Int = 5) {
        val endTime = System.currentTimeMillis() + duration * 1000

        org.bukkit.Bukkit.getPluginManager().getPlugin("HBAbility")?.let {
            object : BukkitRunnable() {
                override fun run() {
                    // 시간이 초과되거나 플레이어가 오프라인 상태라면 종료
                    if (System.currentTimeMillis() > endTime || !player.isOnline) {
                        cancel()
                        return
                    }

                    // 플레이어의 발밑 위치에 얼음 생성
                    val location = player.location.add(0.0, -1.0, 0.0)
                    createIce(location, temporary, removalDelay)
                }
            }.runTaskTimer(
                it,
                0L,
                interval
            )
        }
    }

    /**
     * 특정 위치에 얼음 블록을 생성합니다.
     * @param location 얼음을 생성할 위치
     * @param temporary 얼음을 일정 시간 후 제거할지 여부
     * @param removalDelay 얼음 제거 지연 시간 (초 단위)
     */
    private fun createIce(location: Location, temporary: Boolean, removalDelay: Int) {
        val block = location.block
        if (!block.type.isSolid) {
            block.type = Material.ICE
            if (temporary) {
                scheduleIceRemoval(block.location, removalDelay)
            }
        }
    }

    /**
     * 일정 시간 후 얼음을 제거합니다.
     * @param location 제거할 얼음 블록의 위치
     * @param delay 얼음 제거 지연 시간 (초 단위)
     */
    private fun scheduleIceRemoval(location: Location, delay: Int) {
        org.bukkit.Bukkit.getPluginManager().getPlugin("HBAbility")?.let {
            object : BukkitRunnable() {
                override fun run() {
                    val block = location.block
                    if (block.type == Material.ICE) {
                        block.type = Material.AIR
                    }
                }
            }.runTaskLater(
                it,
                delay * 20L
            )
        }
    }
}
