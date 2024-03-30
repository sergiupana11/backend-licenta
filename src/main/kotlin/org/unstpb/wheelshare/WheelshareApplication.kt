package org.unstpb.wheelshare

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories

@SpringBootApplication
@EnableCassandraRepositories
class WheelshareApplication

fun main(args: Array<String>) {
    runApplication<WheelshareApplication>(*args)
}
