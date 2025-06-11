package com.cymply.auth.adapter.`in`.security

import com.cymply.common.exception.TokenExpiredException
import com.cymply.common.util.JwtUtils
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtUtils: JwtUtils
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
    ) {
        try {
            val authorization = request.getHeader("Authorization")
            if (authorization != null && authorization.startsWith("Bearer ")) {
                val token = authorization.removePrefix("Bearer ").trim()
                val identity = jwtUtils.getIdentities(token)
                val principal = PrincipalDetail(attributes = identity)
                val authentication = UsernamePasswordAuthenticationToken(principal, null, principal.authorities)
                SecurityContextHolder.getContext().authentication = authentication
            }
            filterChain.doFilter(request, response)

        } catch (e: ExpiredJwtException) {
            throw TokenExpiredException()
        } catch (e: Exception) {
            SecurityContextHolder.clearContext()
            throw Exception(e.message)
        }
    }
}
