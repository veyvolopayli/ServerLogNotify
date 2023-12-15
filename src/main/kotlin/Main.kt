package org.example

import org.example.logger.LogService

fun main() {
    println("Service started!")
    LogService().initNewLogger(
        address = "5.181.255.253",
        username = System.getenv("SERVER_USERNAME"),
        password = System.getenv("SERVER_PASSWORD"),
        serviceName = "studhunter"
    )
}