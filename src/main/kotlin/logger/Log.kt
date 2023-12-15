package org.example.logger

interface Log {
    fun initNewLogger(address: String, username: String, password: String, serviceName: String, port: Int = 22)
}