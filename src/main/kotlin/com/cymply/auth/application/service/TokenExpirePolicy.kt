package com.cymply.auth.application.service

object TokenExpirePolicy {
    const val ACCESS = 1800_000.toLong()  // 30 min
    const val REFRESH = 604_800_000.toLong()  // 7 days
    const val TEMPORAL = 900_000.toLong()  // 15 min
}