package com.highbelief.events

import com.highbelief.game.TimerManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.event.Listener

class TimerUpdateEvent(private val plugin: JavaPlugin, private val timerManager: TimerManager) : Listener {

    init {
        startTimerTask()
    }

    private fun startTimerTask() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, Runnable {
            // 매 틱마다 타이머 상태를 갱신
            timerManager.updateTimerDisplay()
        }, 0L, 1L) // 1틱(0.05초)마다 반복
    }
}
