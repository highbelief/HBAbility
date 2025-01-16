package com.highbelief.events

import com.highbelief.game.GameManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class PlayerKillEvent(private val gameManager: GameManager) : Listener {

    @EventHandler
    fun onPlayerKill(event: EntityDamageByEntityEvent) {
        if (event.entity is Player && event.damager is Player) {
            val victim = event.entity as Player
            val attacker = event.damager as Player

            // 확인: 플레이어가 사망했는지 여부
            if (victim.health - event.finalDamage <= 0) {
                event.isCancelled = true // 사망 이벤트를 취소 (게임 관리용)

                // 공격자의 킬 수 증가
                val attackerState = gameManager.getPlayerState(attacker)
                attackerState?.incrementKills()

                // 피해자 탈락 처리
                gameManager.eliminatePlayer(victim)

                // 채팅 알림
                Bukkit.broadcastMessage("${victim.name}님이 ${attacker.name}님에게 처치되었습니다!")
            }
        }
    }
}
