package com.highbelief.scoreboard

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard

class ScoreboardManager {
    private var scoreboard: Scoreboard? = null
    private var objective: Objective? = null
    private val SURVIVOR_COUNT_KEY = "${ChatColor.YELLOW}생존자 수"
    private val PLAYER_KILLS_KEY_PREFIX = "${ChatColor.RED}"

    // 스코어보드 생성
    fun createScoreboard() {
        scoreboard = Bukkit.getScoreboardManager().newScoreboard
        objective = scoreboard?.registerNewObjective("gameStatus", "dummy", "${ChatColor.GREEN}게임 상태")
        objective?.displaySlot = DisplaySlot.SIDEBAR
    }

    // 스코어보드 플레이어 추가
    fun addPlayer(player: Player) {
        if (scoreboard == null || objective == null) {
            createScoreboard()
        }
        scoreboard?.let {
            player.scoreboard = it
        } ?: run {
            throw IllegalStateException("Scoreboard를 생성하는 데 실패했습니다.")
        }
    }

    // 생존자 수 업데이트
    fun updateSurvivorCount(survivorCount: Int) {
        objective?.getScore(SURVIVOR_COUNT_KEY)?.score = survivorCount
    }

    // 킬 수 업데이트
    fun updatePlayerKills(player: Player, kills: Int) {
        objective?.getScore("$PLAYER_KILLS_KEY_PREFIX${player.name} 킬 수")?.score = kills
    }

    // 스코어보드 초기화
    fun clearScoreboard() {
        scoreboard?.clearSlot(DisplaySlot.SIDEBAR)
        scoreboard = null
        objective = null
    }
}
