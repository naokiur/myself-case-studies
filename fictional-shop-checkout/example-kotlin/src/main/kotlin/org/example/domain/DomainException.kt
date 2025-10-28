package org.example.domain

/**
 * ドメイン層で発生する例外
 */
class DomainException(message: String) : RuntimeException(message)
