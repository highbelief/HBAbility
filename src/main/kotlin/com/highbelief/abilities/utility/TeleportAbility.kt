package com.highbelief.abilities.utility

import com.highbelief.abilities.Ability
import org.bukkit.Location
import org.bukkit.entity.Player

class TeleportAbility(player: Player) : Ability(player, "순간이동 능력", 15) { // 쿨타임 15초
    private val maxTeleportDistance = 30 // 최대 순간이동 거리

    override fun performAbility(): Boolean {
        val targetLocation = getTargetLocation(player, maxTeleportDistance)

        if (targetLocation == null) {
            player.sendMessage("순간이동할 위치를 찾을 수 없습니다.")
            return false
        }

        player.teleport(targetLocation)
        player.sendMessage("순간이동 능력이 발동되었습니다! 위치: ${targetLocation.blockX}, ${targetLocation.blockY}, ${targetLocation.blockZ}")
        return true
    }

    /**
     * 플레이어가 조준한 위치를 계산하여 반환합니다.
     * @param player 조준하는 플레이어
     * @param maxDistance 최대 거리
     * @return 순간이동할 위치 또는 null
     */
    private fun getTargetLocation(player: Player, maxDistance: Int): Location? {
        val rayTraceResult = player.world.rayTraceBlocks(
            player.eyeLocation,
            player.location.direction,
            maxDistance.toDouble()
        )
        return rayTraceResult?.hitPosition?.toLocation(player.world)
    }
}
