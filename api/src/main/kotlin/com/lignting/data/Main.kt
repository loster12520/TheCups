package com.lignting.com.lignting.data

import org.babyfish.jimmer.sql.dialect.SQLiteDialect
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.newKSqlClient
import org.babyfish.jimmer.sql.runtime.ConnectionManager
import java.io.File
import java.nio.file.Paths
import java.sql.Connection
import java.sql.DriverManager

/**
 * DatabaseManager is responsible for managing the SQLite database connection and initialization.
 */
const val databaseFilePath = "database.db"

/**
 * DatabaseManager is a singleton object that initializes the SQLite database connection
 */
object DatabaseManager {
    /**
     * The URL for the SQLite database connection.
     */
    private var dbUrl = "jdbc:sqlite:${databaseFilePath}"
    
    /**
     * The KSqlClient instance for interacting with the SQLite database.
     */
    val sqlClient: KSqlClient by lazy {
        init()
        val driverManagerConnection = DriverManager.getConnection(dbUrl)
        initDatabase(driverManagerConnection)
        newKSqlClient {
            setDialect(SQLiteDialect())
            setConnectionManager(ConnectionManager.singleConnectionManager(driverManagerConnection))
        }
    }
    
    /**
     * Initializes the database connection and creates the database file if it does not exist.
     */
    private fun init() {
        val resourceDir = Paths.get(
            DatabaseManager::class.java.classLoader.getResource("")!!.toURI()
        ).toFile()
        val dbFile = File(resourceDir, databaseFilePath)
        if (!dbFile.exists()) {
            dbFile.createNewFile()
        }
        dbUrl = "jdbc:sqlite:${dbFile.absolutePath}"
    }
    
    /**
     * Initializes the database schema by executing the SQL statements in the init.sql file.
     *
     * @param connection The database connection to use for executing the SQL statements.
     */
    private fun initDatabase(connection: Connection) {
        val inputStream = DatabaseManager::class.java.classLoader.getResourceAsStream("init.sql")
            ?: throw IllegalStateException("找不到 init.sql")
        val sql = inputStream.bufferedReader().use { it.readText() }
        connection.createStatement().use { statement ->
            statement.executeUpdate(sql)
        }
    }
}