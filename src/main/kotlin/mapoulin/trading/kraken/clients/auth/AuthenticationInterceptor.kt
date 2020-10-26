package mapoulin.trading.kraken.clients.auth

import feign.RequestInterceptor
import feign.RequestTemplate
import java.security.MessageDigest
import java.util.Base64
import java.util.Date
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class AuthenticationInterceptor(
    private val publicKey: String,
    private val privateKey: String
) : RequestInterceptor {
    private var nonce = 0

    override fun apply(template: RequestTemplate) {
        val postData = template.queryLine().substringAfter("?")
        val endpoint = template.path().substringAfter("/derivatives")
        val nonce = getNonce()

        val sha256 = MessageDigest.getInstance("SHA-256")
        sha256.update(postData.encodeToByteArray())
        sha256.update(nonce.encodeToByteArray())
        sha256.update(endpoint.encodeToByteArray())

        val secretDecoded = Base64.getDecoder().decode(privateKey)

        val mac512 = Mac.getInstance("HmacSHA512")
        mac512.init(SecretKeySpec(secretDecoded, "HmacSHA512"))
        mac512.update(sha256.digest())

        val authent = Base64.getEncoder().encodeToString(mac512.doFinal())

        template.header("Authent", authent)
        template.header("Nonce", nonce)
        template.header("APIKey", publicKey)
    }

    private fun getNonce(): String {
        return String.format("%s%04d", Date().time, nonce++)
    }
}
