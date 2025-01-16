package com.highbelief.abilities.offensive

import com.highbelief.abilities.Ability
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.util.Vector

class KnockbackAbility(player: Player) : Ability(player, "넉백 능력", 15) { // 쿨타임 15초
    private val knockbackRadius = 5.0 // 넉백 효과 반경
    private val knockbackStrength = 2.0 // 넉백 힘

    override fun performAbility(): Boolean {
        val nearbyEntities = getNearbyEntities(player.location, knockbackRadius)

        if (nearbyEntities.isEmpty()) {
            player.sendMessage("주변에 밀쳐낼 대상이 없습니다.")
            return false
        }

        for (entity in nearbyEntities) {
            applyKnockback(entity, player.location)
        }

        player.sendMessage("넉백 능력이 발동되었습니다! 반경 ${knockbackRadius}블록 내의 적들을 밀쳐냈습니다.")
        return true
    }

    /**
     * 특정 반경 내의 엔티티를 가져옵니다.
     * @param location 중심 위치
     * @param radius 반경
     * @return 반경 내의 엔티티 목록
     */
    private fun getNearbyEntities(location: Location, radius: Double): List<Entity> {
        return location.world?.getNearbyEntities(location, radius, radius, radius)?.filter { it is Entity && it != player }?.toList() ?: emptyList()
    }

    /**
     * 대상 엔티티를 중심 위치로부터 반대 방향으로 밀쳐냅니다.
     * @param entity 밀쳐낼 엔티티
     * @param center 중심 위치
     */
    private fun applyKnockback(entity: Entity, center: Location) {
        val direction = entity.location.toVector().subtract(center.toVector()).normalize()
        val knockbackVector = direction.multiply(knockbackStrength)
        entity.velocity = knockbackVector

        if (entity is Player) {
            entity.sendMessage("${player.name}님이 넉백 능력을 사용하여 당신을 밀쳐냈습니다!")
        }
    }
}
