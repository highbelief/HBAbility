package com.highbelief.commands

import com.highbelief.game.GameManager
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class StartCommand(private val gameManager: GameManager) : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (gameManager.isGameRunning) {
            sender.sendMessage("게임이 이미 진행 중입니다.")
            return true
        }

        gameManager.startGame()
        Bukkit.broadcastMessage("게임이 시작되었습니다! 모두 준비하세요.")
        return true
    }
}
