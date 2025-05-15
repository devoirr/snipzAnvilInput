package me.snipz.anvilinput

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.view.AnvilView

class AnvilInputListener: Listener {

    @EventHandler(ignoreCancelled = true)
    fun onInventoryClick(event: InventoryClickEvent) {
        val data = AnvilInput.get(event.whoClicked as Player) ?: return

        event.isCancelled = true
        val view = event.view as AnvilView
        if (event.slot == 2) {
            val text = view.renameText
            if (text != null) {
                data(text)
                AnvilInput.clearData(event.whoClicked.uniqueId)

                event.whoClicked.closeInventory()
                return
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onInventoryClose(event: InventoryCloseEvent) {
        AnvilInput.clearData(event.player.uniqueId)
    }


}
