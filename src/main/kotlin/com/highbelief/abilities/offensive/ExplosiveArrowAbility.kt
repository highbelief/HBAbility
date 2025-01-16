package com.highbelief.abilities.offensive

import com.highbelief.abilities.Ability
import org.bukkit.Bukkit
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.scheduler.BukkitRunnable

class ExplosiveArrowAbility(player: Player) : Ability(player, "폭발 화살 능력", 15), Listener { // 쿨타임 15초
    private val explosionPower = 2.0 // 폭발 강도

    override fun performAbility(): Boolean {
        player.sendMessage("폭발 화살 능력이 활성화되었습니다! 다음 발사된 화살이 폭발합니다.")

        // 폭발 화살이 발사된 후 일정 시간 동안 유지
        Bukkit.getPluginManager().getPlugin("HBAbility")?.let {
            object : BukkitRunnable() {
                override fun run() {
                    Bukkit.getPluginManager().getPlugin("HBAbility")?.let { p1 -> Bukkit.getPluginManager().registerEvents(this@ExplosiveArrowAbility, p1) }
                }
            }.runTaskLater(it, 20L * 10)
        } // 10초 동안 지속

        return true
    }

    @EventHandler
    fun onProjectileHit(event: ProjectileHitEvent) {
        val projectile = event.entity as? Arrow ?: return
        val shooter = projectile.shooter as? Player ?: return

        if (shooter != player) return // 능력 소유자의 화살이 아니면 무시

        // 화살이 적중한 위치에서 폭발
        val location = projectile.location
        location.world?.createExplosion(location, explosionPower.toFloat(), false, false)
        projectile.remove() // 화살 제거

        player.sendMessage("폭발 화살이 적중하여 폭발을 일으켰습니다!")
    }
}
