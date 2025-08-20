package com.lignting.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.auth0.jwt.interfaces.JWTVerifier
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.MessageDigest
import java.security.SecureRandom
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.text.SimpleDateFormat
import java.util.Date

object JwtUtils {
    private val keyPair: KeyPair = generateRsaKeyPair()
    private val algorithm = Algorithm.RSA256(keyPair.public as RSAPublicKey, keyPair.private as RSAPrivateKey)
    private val verifier: JWTVerifier = JWT.require(algorithm).build()
    
    private fun generateRsaKeyPair(): KeyPair {
        val date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        val seed = MessageDigest.getInstance("SHA-256").digest(date.toByteArray())
        val secureRandom = SecureRandom.getInstance("SHA1PRNG").apply { setSeed(seed) }
        val keyGen = KeyPairGenerator.getInstance("RSA")
        keyGen.initialize(2048, secureRandom)
        return keyGen.generateKeyPair()
    }
    
    fun jwt(account: String = "root"): String {
        return JWT.create()
            .withSubject(account)
            .withIssuedAt(Date())
            .withExpiresAt(Date(System.currentTimeMillis() + 3600_000))
            .sign(algorithm)
    }
    
    fun parseJwt(token: String): String {
        val decoded: DecodedJWT = verifier.verify(token)
        return decoded.subject
    }
    
    fun getVerifier() = verifier
}