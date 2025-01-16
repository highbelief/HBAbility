package com.highbelief.commands

import com.highbelief.game.GameManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class StatusCommand(private val gameManager: GameManager) : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val status = if (gameManager.isGameRunning) "진행 중" else "대기 중"
        sender.sendMessage("현재 게임 상태: $status")
        sender.sendMessage("생존자 수: ${gameManager.getAlivePlayersCount()}")
        sender.sendMessage("종료 지점: ${gameManager.getEndPoint() ?: "설정되지 않음"}")
        return true
    }
}
