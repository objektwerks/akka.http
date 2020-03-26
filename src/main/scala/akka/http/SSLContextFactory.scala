package akka.http

import java.security.{KeyStore, SecureRandom}

import javax.net.ssl.{KeyManagerFactory, SSLContext, TrustManagerFactory}

object SSLContextFactory {
  def newInstance(passphrase: String): SSLContext = {
    val keystore = KeyStore.getInstance("PKCS12")
    val serverKey = getClass.getClassLoader.getResourceAsStream("/server.key")
    val password = passphrase.toCharArray
    keystore.load(serverKey, password)

    val keyManagerFactory = KeyManagerFactory.getInstance("SunX509")
    keyManagerFactory.init(keystore, password)

    val trustManagerFactory = TrustManagerFactory.getInstance("SunX509")
    trustManagerFactory.init(keystore)

    val sslContext = SSLContext.getInstance("TLS")
    sslContext.init(keyManagerFactory.getKeyManagers, trustManagerFactory.getTrustManagers, new SecureRandom)
    sslContext
  }
}