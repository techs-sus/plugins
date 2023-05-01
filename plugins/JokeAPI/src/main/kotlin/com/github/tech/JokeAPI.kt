package com.github.tech

import android.content.Context
import com.aliucord.Http
import com.aliucord.annotations.AliucordPlugin
import com.aliucord.api.CommandsAPI
import com.aliucord.entities.Plugin
import com.aliucord.utils.GsonUtils

class Joke {
    var joke: String? = null
}

// Aliucord Plugin annotation. Must be present on the main class of your plugin
@AliucordPlugin(
        requiresRestart =
                false /* Whether your plugin requires a restart after being installed/updated */
)
// Plugin class. Must extend Plugin and override start and stop
// Learn more:
// https://github.com/Aliucord/documentation/blob/main/plugin-dev/1_introduction.md#basic-plugin-structure
class JokeAPI : Plugin() {
    override fun load(context: Context) {}

    override fun start(context: Context) {
        // Register a command with the name hello and description "My first command!" and no
        // arguments.
        // Learn more: https://github.com/Aliucord/documentation/blob/main/plugin-dev/2_commands.md

        // A bit more advanced command with arguments
        commands.registerCommand("fetch_safe_joke", "Fetchs a safe joke!", listOf()) { ctx ->
            var json = Http.simpleGet("https://v2.jokeapi.dev/joke/Any?type=single&safe-mode")
            var parsed: Joke = GsonUtils.fromJson(json, Joke::class.java)

            CommandsAPI.CommandResult("Look at this joke I found: \n%s".format(parsed.joke))
        }
    }

    override fun stop(context: Context) {
        // Unregister our commands
        commands.unregisterAll()
    }
}
