package com.example.chatkotlin.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.UUID

@Component
class MdcFilter : OncePerRequestFilter() {
    private val logger = LoggerFactory.getLogger(MdcFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val traceId = UUID.randomUUID().toString()
        MDC.put("traceId", traceId)
        try {
            logger.info("Request: {} {}", request.method, request.requestURI)
            filterChain.doFilter(request, response)
        } finally {
            logger.info("Response: status={}", response.status)
            MDC.remove("traceId")
        }
    }
}
