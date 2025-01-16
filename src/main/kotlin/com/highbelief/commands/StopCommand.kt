package com.highbelief.commands

import com.highbelief.game.GameManager
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class StopCommand(private val gameManager: GameManager) : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (!gameManager.isGameRunning) {
            sender.sendMessage("현재 진행 중인 게임이 없습니다.")
            return true
        }

        gameManager.stopGame()
        Bukkit.broadcastMessage("게임이 중단되었습니다!")
        return true
    }
}
