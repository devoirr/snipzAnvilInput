package me.snipz.anvilinput

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.MenuType
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

object AnvilInput {

    private lateinit var listener: AnvilInputListener
    private val map = mutableMapOf<UUID, (String) -> (Unit)>()

    private var isInitialized = false

    /**
     * Initializes the AnvilInput lib.
     *
     * @param plugin The JavaPlugin instance.
     */
    fun init(plugin: JavaPlugin) {
        if (isInitialized) {
            throw IllegalStateException("AnvilInput is already initialized.")
        }

        listener = AnvilInputListener()
        plugin.server.pluginManager.registerEvents(listener, plugin)

        isInitialized = true
    }

    /**
     * Ask input from a player.
     *
     * @param run The function that will be called when the player enters the input.
     * @param player The player who will enter the input.
     * @param resultItem The item that will be displayed as the result in the anvil.
     * @param fillerItems The items that will be displayed as fillers in the anvil.
     */
    fun createForPlayer(run: (String) -> (Unit), player: Player, resultItem: ItemStack, fillerItems: ItemStack) {
        MenuType.ANVIL.create(player).apply {
            this.setItem(0, fillerItems)
            this.setItem(1, fillerItems)

            this.setItem(2, resultItem)
        }.open()

        map[player.uniqueId] = run
    }

    fun get(player: Player): ((String) -> (Unit))? {
        return map[player.uniqueId]
    }

    fun clearData(uuid: UUID) = map.remove(uuid)
}