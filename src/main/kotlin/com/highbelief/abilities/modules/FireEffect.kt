package com.highbelief.abilities.modules

import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

class FireEffect {
    /**
     * 특정 엔티티에 화염 효과를 적용합니다.
     * @param entity 효과를 받을 엔티티
     * @param duration 화염 지속 시간 (틱 단위, 20틱 = 1초)
     */
    fun applyToEntity(entity: Entity, duration: Int) {
        entity.fireTicks = duration
        if (entity is Player) {
            entity.sendMessage("화염 효과가 ${duration / 20}초 동안 적용되었습니다!")
        }
    }

    /**
     * 특정 위치에 화염을 생성합니다.
     * @param location 화염을 생성할 위치
     */
    fun applyToLocation(location: Location) {
        val block = location.block
        if (!block.type.isSolid) {
            block.type = org.bukkit.Material.FIRE
        }
    }
}
