package com.highbelief.game

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class TimerManager(private val onEnd: () -> Unit) {
    private var remainingTime: Int = 0
    private var timerTask: BukkitRunnable? = null

    // 타이머 시작
    fun startTimer(durationInSeconds: Int) {
        stopTimer()
        remainingTime = durationInSeconds

        timerTask = object : BukkitRunnable() {
            override fun run() {
                if (remainingTime <= 0) {
                    cancel()
                    onEnd() // 타이머 종료 시 로직 실행
                } else {
                    remainingTime--
                    updateTimerDisplay()
                }
            }
        }
        Bukkit.getPluginManager().getPlugin("HBAbility")?.let { timerTask?.runTaskTimer(it, 0L, 20L) }
    }

    // 타이머 중단
    fun stopTimer() {
        timerTask?.cancel()
        timerTask = null
    }

    // 타이머 디스플레이 업데이트
    fun updateTimerDisplay() {
        val minutes = remainingTime / 60
        val seconds = remainingTime % 60
        Bukkit.broadcastMessage("남은 시간: ${minutes}분 ${seconds}초")
    }
}
