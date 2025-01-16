package com.highbelief.game

import com.highbelief.scoreboard.ScoreboardManager
import com.highbelief.scoreboard.BossBarManager
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class GameManager(private val plugin: JavaPlugin) {
    var isGameRunning: Boolean = false
        private set

    private val playerStates = mutableMapOf<Player, PlayerState>()
    private var endPoint: Location? = null
    private val timerManager = TimerManager { endGame() }
    private val scoreboardManager = ScoreboardManager()
    private val bossBarManager = BossBarManager(plugin)

    // 게임 시작
    fun startGame() {
        if (isGameRunning) return
        isGameRunning = true
        initializeGame()
        Bukkit.broadcastMessage("게임이 시작되었습니다! 생존자 수: ${playerStates.size}")
    }

    // 게임 종료
    fun stopGame() {
        if (!isGameRunning) return
        isGameRunning = false
        cleanupGame()
        Bukkit.broadcastMessage("게임이 중단되었습니다.")
    }

    // 게임 강제 종료 또는 타이머 종료 시 호출
    private fun endGame() {
        Bukkit.broadcastMessage("시간이 만료되어 게임이 종료되었습니다!")
        stopGame()
    }

    // 생존자 수 반환
    fun getAlivePlayersCount(): Int {
        return playerStates.values.count { it.isAlive }
    }

    // 특정 플레이어 상태 반환
    fun getPlayerState(player: Player): PlayerState? {
        return playerStates[player]
    }

    // 플레이어 탈락 처리
    fun eliminatePlayer(player: Player) {
        val state = playerStates[player] ?: return
        state.markAsDead()
        updateGameStatus("${player.name}님이 탈락했습니다.")
    }

    // 우승자 선언
    private fun declareWinner() {
        val winner = playerStates.entries.firstOrNull { it.value.isAlive }?.key
        if (winner != null) {
            Bukkit.broadcastMessage("축하합니다! ${winner.name}님이 승리했습니다!")
        } else {
            Bukkit.broadcastMessage("생존자가 없습니다. 게임이 종료되었습니다.")
        }
        stopGame()
    }

    // 종료 지점 설정
    fun setEndPoint(location: Location) {
        endPoint = location
    }

    // 종료 지점 반환
    fun getEndPoint(): Location? {
        return endPoint
    }

    // 게임 초기화
    private fun initializeGame() {
        playerStates.clear()
        Bukkit.getOnlinePlayers().forEach { player ->
            setupPlayer(player)
        }

        scoreboardManager.createScoreboard()
        scoreboardManager.updateSurvivorCount(playerStates.size)
        bossBarManager.createBossBar("남은 시간", 30 * 60)
        timerManager.startTimer(30 * 60) // 30분 타이머 시작
    }

    // 플레이어 초기 설정
    private fun setupPlayer(player: Player) {
        playerStates[player] = PlayerState(player).apply {
            assignAbility("랜덤 능력") // 임시로 랜덤 능력 부여
        }
        scoreboardManager.addPlayer(player)
        player.sendMessage("게임에 참가하셨습니다!")
    }

    // 게임 종료 후 정리
    private fun cleanupGame() {
        playerStates.values.forEach { it.reset() }
        playerStates.clear()
        scoreboardManager.clearScoreboard()
        bossBarManager.removeBossBar()
        timerManager.stopTimer()
    }

    // 게임 상태 업데이트
    private fun updateGameStatus(message: String) {
        scoreboardManager.updateSurvivorCount(getAlivePlayersCount())
        Bukkit.broadcastMessage(message)

        if (getAlivePlayersCount() == 1) {
            declareWinner()
        }
    }
}
