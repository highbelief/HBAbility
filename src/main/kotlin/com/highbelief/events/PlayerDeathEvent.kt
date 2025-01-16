package com.highbelief.events

import com.highbelief.game.GameManager
import org.bukkit.Bukkit
import org.bukkit.FireworkEffect
import org.bukkit.entity.Firework
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.meta.FireworkMeta

class PlayerDeathEvent(private val gameManager: GameManager) : Listener {

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.entity

        // 플레이어 탈락 처리
        gameManager.eliminatePlayer(player)

        // 폭죽 효과 추가
        val world = player.world
        val location = player.location
        val firework = world.spawn(location, Firework::class.java)
        val fireworkMeta = firework.fireworkMeta
        fireworkMeta.addEffect(
            FireworkEffect.builder()
                .withColor(org.bukkit.Color.RED, org.bukkit.Color.BLUE)
                .withFade(org.bukkit.Color.GREEN)
                .with(FireworkEffect.Type.BALL_LARGE)
                .withTrail()
                .withFlicker()
                .build()
        )
        fireworkMeta.power = 1
        firework.fireworkMeta = fireworkMeta

        // 채팅 알림
        Bukkit.broadcastMessage("${player.name}님이 탈락하셨습니다. 남은 생존자: ${gameManager.getAlivePlayersCount()}")
    }
}
