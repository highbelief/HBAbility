package com.highbelief.abilities.utility

import com.highbelief.abilities.Ability
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.util.Vector

class GravityManipulationAbility(player: Player) : Ability(player, "중력 조작 능력", 20) { // 쿨타임 20초
    private val effectRadius = 10.0 // 효과 반경
    private val liftStrength = 1.5 // 공중으로 띄우는 힘
    private val pullStrength = 1.0 // 끌어당기는 힘
    private var isLiftMode = true // 공중으로 띄우기(true) 또는 끌어당기기(false)

    override fun performAbility(): Boolean {
        val nearbyEntities = getNearbyEntities(player.location, effectRadius)

        if (nearbyEntities.isEmpty()) {
            player.sendMessage("반경 ${effectRadius}블록 내에 효과를 적용할 대상이 없습니다.")
            return false
        }

        for (entity in nearbyEntities) {
            if (isLiftMode) {
                liftEntity(entity)
            } else {
                pullEntity(entity, player.location)
            }
        }

        val mode = if (isLiftMode) "공중으로 띄우기" else "끌어당기기"
        player.sendMessage("중력 조작 능력이 발동되었습니다! ${mode} 모드로 반경 ${effectRadius}블록 내 엔티티에 적용되었습니다.")
        return true
    }

    /**
     * 특정 반경 내의 엔티티를 가져옵니다.
     * @param location 중심 위치
     * @param radius 반경
     * @return 반경 내의 엔티티 목록
     */
    private fun getNearbyEntities(location: Location, radius: Double): List<Entity> {
        val world = location.world ?: return emptyList()
        return world.getNearbyEntities(location, radius, radius, radius)
            .filter { it != player }
            .toList()
    }

    /**
     * 엔티티를 공중으로 띄웁니다.
     * @param entity 효과를 받을 엔티티
     */
    private fun liftEntity(entity: Entity) {
        val liftVector = Vector(0.0, liftStrength, 0.0)
        entity.velocity = entity.velocity.add(liftVector)
        if (entity is Player) {
            entity.sendMessage("${player.name}님이 중력 조작 능력으로 당신을 공중으로 띄웠습니다!")
        }
    }

    /**
     * 엔티티를 특정 위치로 끌어당깁니다.
     * @param entity 효과를 받을 엔티티
     * @param targetLocation 끌어당길 위치
     */
    private fun pullEntity(entity: Entity, targetLocation: Location) {
        val direction = targetLocation.toVector().subtract(entity.location.toVector()).normalize()
        val pullVector = direction.multiply(pullStrength)
        entity.velocity = entity.velocity.add(pullVector)
        if (entity is Player) {
            entity.sendMessage("${player.name}님이 중력 조작 능력으로 당신을 끌어당겼습니다!")
        }
    }

    /**
     * 중력 조작 모드를 전환합니다.
     * @param liftMode true면 공중으로 띄우기 모드, false면 끌어당기기 모드
     */
    fun toggleMode(liftMode: Boolean) {
        isLiftMode = liftMode
        player.sendMessage("중력 조작 모드가 ${if (liftMode) "공중으로 띄우기" else "끌어당기기"}로 변경되었습니다.")
    }
}
