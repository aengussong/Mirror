package com.aengussong.mirror.util

import android.util.Patterns
import com.aengussong.mirror.model.InvalidIp
import com.aengussong.mirror.model.InvalidPort
import com.aengussong.mirror.model.Message

private const val MIN_PORT = 0
private const val MAX_PORT = 65535

class Validator {

    companion object {

        fun validatePort(port: String): Boolean {
            val portInt: Int = port.toIntOrNull() ?: return false
            return portInt in (MIN_PORT..MAX_PORT)
        }

        fun validateIp(ip: String): Boolean {
            return Patterns.IP_ADDRESS.matcher(ip).matches()
        }

        fun validateAddress(ip: String, port: String): Message? {
            if(!validateIp(ip)) return InvalidIp
            if(!validatePort(port)) return InvalidPort
            return null
        }
    }
}