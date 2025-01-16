package com.highbelief.commands

import com.highbelief.game.GameManager
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetEndPointCommand(private val gameManager: GameManager) : CommandExecutor {
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

        if (args.size != 3) {
            sender.sendMessage("사용법: /종료지점설정 <X Y Z>")
            return true
        }

        try {
            val x = args[0].toDouble()
            val y = args[1].toDouble()
            val z = args[2].toDouble()
            val location = Location(sender.world, x, y, z)
            gameManager.setEndPoint(location)
            Bukkit.broadcastMessage("종료 지점이 (${x}, ${y}, ${z})로 설정되었습니다!")
        } catch (e: NumberFormatException) {
            sender.sendMessage("X, Y, Z는 숫자여야 합니다.")
        }
        return true
    }
}
