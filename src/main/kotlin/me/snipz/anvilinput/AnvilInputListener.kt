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
                event.whoClicked.closeInventory()
                return
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onInventoryClose(event: InventoryCloseEvent) {
        AnvilInput.get(event.player as Player)?.let { data ->
            event.inventory.setItem(0, null)
            event.inventory.setItem(1, null)
            event.inventory.setItem(2, null)
        }

        // Remove the data from the AnvilInput
        AnvilInput.clearData(event.player.uniqueId)
    }


}
