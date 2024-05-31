package org.ecorous.bot.template

import com.kotlindiscord.kord.extensions.utils.envOrNull
import org.jetbrains.exposed.sql.Database

object DB {
	lateinit var db : Database;
	fun init(): Boolean {
		if (this::db.isInitialized) {
			logger.warn("Tried to initialize the database twice - ignoring.")
			return false
		}
		try {
			val dbName = envOrNull("BOT_DATABASE") ?: "bot-template";
			val user = envOrNull("BOT_DATABASE_USER") ?: "postgres";
			val password = envOrNull("BOT_DATABASE_PASSWORD") ?: "example";
			val dbHost = envOrNull("BOT_DATABASE_HOST") ?: "localhost";
			val dbPort = envOrNull("BOT_DATABASE_PORT") ?: "5432";
			val url = "jdbc:postgresql://$dbHost:$dbPort/$dbName";
			val driver = "org.postgresql.Driver";
			db = Database.connect(
				url,
				driver,
				user,
				password
			);
			return true;
		} catch (e: Exception) {
			logger.error("Failed to initialize the database: ${e.message}");
			return false;
		}
	}
}
