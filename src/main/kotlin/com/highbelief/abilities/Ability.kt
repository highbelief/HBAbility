package com.highbelief.abilities

import org.bukkit.entity.Player

abstract class Ability(
    val player: Player,             // 능력을 소유한 플레이어
    val name: String,               // 능력 이름
    private val cooldownTime: Long  // 쿨타임 (초 단위)
) {
    private var lastUsedTime: Long = 0

    /**
     * 능력을 발동합니다.
     */
    fun activate() {
        if (isOnCooldown()) {
            val remainingTime = getRemainingCooldown()
            player.sendMessage("능력을 사용할 수 없습니다. 남은 쿨타임: ${remainingTime}초")
            return
        }

        if (performAbility()) {
            lastUsedTime = System.currentTimeMillis()
            player.sendMessage("능력 '$name'이(가) 발동되었습니다!")
        } else {
            player.sendMessage("능력 발동에 실패했습니다.")
        }
    }

    /**
     * 능력 발동 시 실행되는 로직을 정의합니다.
     * @return 능력 발동 성공 여부
     */
    protected abstract fun performAbility(): Boolean

    /**
     * 능력 쿨타임이 활성화 중인지 확인합니다.
     * @return 활성화 중이면 true, 아니면 false
     */
    private fun isOnCooldown(): Boolean {
        val currentTime = System.currentTimeMillis()
        val elapsedTime = (currentTime - lastUsedTime) / 1000
        return elapsedTime < cooldownTime
    }

    /**
     * 남은 쿨타임을 반환합니다.
     * @return 남은 시간 (초 단위)
     */
    private fun getRemainingCooldown(): Long {
        val currentTime = System.currentTimeMillis()
        val elapsedTime = (currentTime - lastUsedTime) / 1000
        return (cooldownTime - elapsedTime).coerceAtLeast(0)
    }
}
