package org.example.logger

import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.JSch
import com.jcraft.jsch.JSchException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.Properties

class LogService : Log {
    override fun initNewLogger(address: String, username: String, password: String, serviceName: String, port: Int) {
        try {
            val jsch = JSch()
            val session = jsch.getSession(username, address, port).also { it.setPassword(password) }

            val config = Properties().also { it["StrictHostKeyChecking"] = "no" }
            session.setConfig(config)

            session.connect()

            val channel = session.openChannel("exec") as ChannelExec
            val command = "journalctl -f -u $serviceName"

            channel.setCommand(command)

            val input = BufferedReader(InputStreamReader(channel.inputStream))
            channel.connect()

            var line = input.readLine()
            while (line != null) {
                println(line)
                line = input.readLine()
            }

            channel.disconnect()
            session.disconnect()
        } catch (e: JSchException) {
            when(e.message) {
                "Auth fail" -> {
                    // todo Send authorization error
                    println("Auth data is incorrect. Check your username and password.")
                }
            }
        }
    }


}