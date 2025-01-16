package com.highbelief.abilities.offensive

import com.highbelief.abilities.Ability
import org.bukkit.Location
import org.bukkit.entity.Player

class LightningStrikeAbility(player: Player) : Ability(player, "번개 소환 능력", 15) { // 쿨타임 15초
    private val strikeRadius = 3.0 // 번개 소환 반경

    override fun performAbility(): Boolean {
        val targetLocation = getTargetLocation(player, 30) // 30블록까지의 조준 위치를 가져옴

        if (targetLocation == null) {
            player.sendMessage("번개를 소환할 위치를 찾을 수 없습니다.")
            return false
        }

        summonLightning(targetLocation)
        player.sendMessage("번개가 소환되었습니다! 위치: ${targetLocation.blockX}, ${targetLocation.blockY}, ${targetLocation.blockZ}")
        return true
    }

    /**
     * 플레이어가 바라보는 위치를 계산합니다.
     * @param player 조준하는 플레이어
     * @param maxDistance 최대 거리
     * @return 조준 위치 또는 null
     */
    private fun getTargetLocation(player: Player, maxDistance: Int): Location? {
        val rayTraceResult = player.world.rayTraceBlocks(
            player.eyeLocation,
            player.location.direction,
            maxDistance.toDouble()
        )
        return rayTraceResult?.hitPosition?.let { hitVector ->
            player.world.getBlockAt(hitVector.toLocation(player.world)).location
        }
    }

    /**
     * 주어진 위치에 번개를 소환합니다.
     * @param location 번개를 소환할 위치
     */
    private fun summonLightning(location: Location) {
        location.world?.strikeLightningEffect(location) // 번개 효과 생성
        location.world?.getNearbyEntities(location, strikeRadius, strikeRadius, strikeRadius)?.forEach { entity ->
            if (entity is Player && entity != player) {
                entity.damage(5.0) // 적중한 엔티티에 피해를 입힘
                entity.sendMessage("${player.name}님이 소환한 번개에 맞았습니다!")
            }
        }
    }
}
