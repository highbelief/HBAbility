package com.highbelief.commands

import com.highbelief.game.GameManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AbilityCommand(private val gameManager: GameManager) : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (sender !is Player) {
            sender.sendMessage("플레이어만 이 명령어를 사용할 수 있습니다.")
            return true
        }

        val playerState = gameManager.getPlayerState(sender)
        if (playerState == null) {
            sender.sendMessage("게임에 참가하지 않았습니다.")
        } else {
            val ability = playerState.ability ?: "능력이 아직 부여되지 않았습니다."
            sender.sendMessage("당신의 능력은 '$ability'입니다.")
        }
        return true
    }
}
