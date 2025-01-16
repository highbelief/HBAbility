package com.highbelief.abilities.modules

import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.util.Vector

class TeleportEffect {
    /**
     * 플레이어를 지정된 위치로 순간이동시킵니다.
     * @param player 이동할 플레이어
     * @param targetLocation 목표 위치
     * @param onTeleport 성공 시 실행할 추가 동작 (선택 사항)
     */
    fun teleportTo(player: Player, targetLocation: Location, onTeleport: (() -> Unit)? = null) {
        if (!player.teleport(targetLocation)) {
            player.sendMessage("순간이동에 실패했습니다.")
            return
        }
        player.sendMessage("지정된 위치로 순간이동되었습니다: ${targetLocation.blockX}, ${targetLocation.blockY}, ${targetLocation.blockZ}")
        onTeleport?.invoke()
    }

    /**
     * 플레이어를 현재 위치에서 상대적으로 이동시킵니다.
     * @param player 이동할 플레이어
     * @param offset 이동할 상대적인 방향과 거리 (Vector)
     * @param onTeleport 성공 시 실행할 추가 동작 (선택 사항)
     */
    fun teleportRelative(player: Player, offset: Vector, onTeleport: (() -> Unit)? = null) {
        val currentLocation = player.location
        val targetLocation = currentLocation.clone().add(offset)

        teleportTo(player, targetLocation, onTeleport)
    }
}
