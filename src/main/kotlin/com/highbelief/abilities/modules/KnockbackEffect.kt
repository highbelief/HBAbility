package com.highbelief.abilities.modules

import org.bukkit.entity.Entity
import org.bukkit.util.Vector

class KnockbackEffect {
    /**
     * 지정된 엔티티를 특정 방향으로 밀쳐냅니다.
     * @param entity 밀쳐낼 엔티티
     * @param direction 밀쳐낼 방향 (Vector 객체로 지정)
     * @param strength 밀쳐내는 힘의 크기
     */
    fun apply(entity: Entity, direction: Vector, strength: Double) {
        // 방향 벡터를 정규화하고 힘을 곱해 적용
        val knockbackVector = direction.normalize().multiply(strength)
        entity.velocity = knockbackVector

        // 플레이어인 경우 알림 메시지 출력
        if (entity is org.bukkit.entity.Player) {
            entity.sendMessage("당신은 밀쳐졌습니다!")
        }
    }

    /**
     * 지정된 엔티티를 다른 엔티티로부터 반대 방향으로 밀쳐냅니다.
     * @param target 밀쳐질 엔티티
     * @param source 밀쳐내는 출발점 (또는 엔티티)
     * @param strength 밀쳐내는 힘의 크기
     */
    fun applyFromEntity(target: Entity, source: Entity, strength: Double) {
        // 타겟과 소스의 위치를 기반으로 반대 방향 계산
        val direction = target.location.toVector().subtract(source.location.toVector())
        apply(target, direction, strength)
    }
}
