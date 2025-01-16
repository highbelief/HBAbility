package com.highbelief.game

import org.bukkit.GameMode
import org.bukkit.entity.Player

class PlayerState(val player: Player) {
    var isAlive: Boolean = true
    var kills: Int = 0
    var ability: String? = null

    // 능력 할당
    fun assignAbility(abilityName: String) {
        ability = abilityName
        player.sendMessage("당신의 능력은 '$abilityName'입니다.")
    }

    // 킬 수 증가
    fun incrementKills() {
        kills++
        player.sendMessage("킬 수가 증가했습니다! 현재 킬 수: $kills")
    }

    // 플레이어를 탈락 처리
    fun markAsDead() {
        isAlive = false
        player.sendMessage("탈락하셨습니다. 관전자 모드로 전환됩니다.")
        player.gameMode = GameMode.SPECTATOR
    }

    // 초기화 (게임 종료 시 호출)
    fun reset() {
        isAlive = true
        kills = 0
        ability = null
        player.gameMode = GameMode.SURVIVAL
        player.sendMessage("게임이 초기화되었습니다.")
    }
}
