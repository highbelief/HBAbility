package com.highbelief

import com.highbelief.commands.*
import com.highbelief.events.TimerUpdateEvent
import com.highbelief.game.GameManager
import com.highbelief.game.TimerManager
import org.bukkit.plugin.java.JavaPlugin

class HBAbility : JavaPlugin() {
    private lateinit var gameManager: GameManager

    override fun onEnable() {
        // GameManager 초기화
        gameManager = GameManager(plugin = this)

        // TimerManager 초기화
        val timerManager = TimerManager(
            onEnd = { onTimerEnd() } // 타이머 종료 시 호출할 함수 지정
        )

        // TimerUpdateEvent 등록
        val timerUpdateEvent = TimerUpdateEvent(this, timerManager)
        server.pluginManager.registerEvents(timerUpdateEvent, this)

        // 명령어 등록
        getCommand("시작")?.setExecutor(StartCommand(gameManager))
        getCommand("중지")?.setExecutor(StopCommand(gameManager))
        getCommand("상태")?.setExecutor(StatusCommand(gameManager))
        getCommand("능력")?.setExecutor(AbilityCommand(gameManager))
        getCommand("종료지점설정")?.setExecutor(SetEndPointCommand(gameManager))

        logger.info("HBAbility 플러그인이 성공적으로 활성화되었습니다!")
    }

    private fun onTimerEnd() {
        // 타이머가 종료될 때 수행할 작업
        logger.info("타이머가 종료되었습니다!")
        gameManager.stopGame()
    }

    override fun onDisable() {
        logger.info("HBAbility 플러그인이 비활성화되었습니다.")
    }
}
