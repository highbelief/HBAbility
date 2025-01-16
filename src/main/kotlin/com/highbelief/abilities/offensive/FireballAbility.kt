package com.highbelief.abilities.offensive

import com.highbelief.abilities.Ability
import org.bukkit.Location
import org.bukkit.entity.Fireball
import org.bukkit.entity.Player
import org.bukkit.util.Vector

class FireballAbility(player: Player) : Ability(player, "화염구 능력", 10) { // 쿨타임 10초
    private val explosionPower = 2.0 // 폭발 강도

    override fun performAbility(): Boolean {
        val fireball = launchFireball(player.location, player.location.direction)
        return if (fireball != null) {
            player.sendMessage("화염구가 발사되었습니다!")
            true
        } else {
            player.sendMessage("화염구 발사에 실패했습니다.")
            false
        }
    }

    /**
     * 화염구를 발사합니다.
     * @param location 발사 위치
     * @param direction 발사 방향
     * @return 생성된 Fireball 객체 또는 null
     */
    private fun launchFireball(location: Location, direction: Vector): Fireball? {
        val world = location.world ?: return null
        val fireball = world.spawn(location.add(direction.multiply(1.5)), Fireball::class.java)
        fireball.shooter = player
        fireball.direction = direction
        fireball.yield = explosionPower.toFloat() // 폭발 강도 설정
        return fireball
    }
}
