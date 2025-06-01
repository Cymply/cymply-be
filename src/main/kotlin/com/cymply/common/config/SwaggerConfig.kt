package com.cymply.common.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    private val securitySchemeName = "JWT Authorization"

    @Bean
    fun openAPI(): OpenAPI =
        OpenAPI()
            .info(getInfo())
            .addSecurityItem(getSecurityRequirement())
            .components(Components().addSecuritySchemes(securitySchemeName, getSecurityScheme()))

//    @Bean
//    fun sortSchemas(): OpenApiCustomizer = OpenApiCustomizer { openApi: OpenAPI ->
//        val components: Components = openApi.components ?: Components().also { openApi.components = it }
//
//        val originalSchemas: Map<String, io.swagger.v3.oas.models.media.Schema<*>> =
//            components.schemas ?: emptyMap()
//
//        if (originalSchemas.isNotEmpty()) {
//            val sorted: SortedMap<String, Schema<*>> = TreeMap()
//            sorted.putAll(originalSchemas)
//
//            components.schemas = sorted
//        }
//    }

    private fun getInfo(): Info =
        Info()
            .title("Cymply API")
            .description("Cymply Demo Server Swagger")
            .contact(getContact())
            .version("0.0.1")

    private fun getContact(): Contact =
        Contact()
            .name("Github Repository")
            .url("https://github.com/Cymply/cymply-be")
            .email("cymply.official25@gmail.com")

    private fun getSecurityRequirement(): SecurityRequirement =
        SecurityRequirement().addList(securitySchemeName)

    private fun getSecurityScheme(): SecurityScheme =
        SecurityScheme()
            .name(securitySchemeName)
            .type(SecurityScheme.Type.HTTP)
            .`in`(SecurityScheme.In.HEADER)
            .scheme("Bearer")
            .bearerFormat("JWT")

}