package example.com.dao

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

object DatabaseFactory {
    fun init() {
        Database.connect(createHikariDataSource())

    }

    private fun createHikariDataSource(): HikariDataSource {
        val driverClass = "org.postgresql.Driver"
        val jdbcUrl = "jdbc:postgresql://localhost:5432/kmmsocialdb"
        val hikariConfig = HikariConfig().apply {
            driverClassName = driverClass
            setJdbcUrl(jdbcUrl)
            maximumPoolSize = 3 // maximum number of db connections
            isAutoCommit = false // all transactions should be manual
            transactionIsolation = "TRANSACTION_REPEATABLE_READ" // prevent dirty reads : dirty read is a situation when a transaction reads data from a row that is being modified by another transaction; by TRANSACTION_REPEATABLE_READ we prevent this
            validate() // validate the configuration
        }
        return HikariDataSource(hikariConfig)
    }
}