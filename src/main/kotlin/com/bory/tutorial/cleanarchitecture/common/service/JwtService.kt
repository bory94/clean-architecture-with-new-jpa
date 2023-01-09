package com.bory.tutorial.cleanarchitecture.common.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*

/* from https://www.allkeysgenerator.com/Random/Security-Encryption-Key-Generator.aspx 256bit + HEX mode */
private const val SECRET_KEY = "7234753778214125442A472D4B614E645267556B58703273357638792F423F45"

/* Token Expiration time is 14 days */
private const val EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 14

@Service
class JwtService {
    fun generateToken(userDetails: UserDetails, extraClaims: Map<String, Any> = mapOf()): String =
        Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact()

    fun extractSubject(token: String): String? = extractClaim(token, Claims::getSubject)

    fun <T> extractClaim(token: String, claimResolver: (Claims) -> T): T =
        extractAllClaims(token).let(claimResolver::invoke)

    private fun extractAllClaims(token: String): Claims =
        Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).body

    private fun getSigningKey(): Key = Decoders.BASE64.decode(SECRET_KEY)
        .let {
            Keys.hmacShaKeyFor(it)
        }

    fun isTokenValid(token: String, userDetails: UserDetails) =
        (extractSubject(token) == userDetails.username) && !isTokenExpired(token)

    private fun isTokenExpired(token: String): Boolean =
        extractClaim(token, Claims::getExpiration).before(Date())

}