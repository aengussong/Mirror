package com.aengussong.mirror.model

sealed class Message

object InvalidPort:Message()
object InvalidIp:Message()
object CantConnect:Message()