package akka.http

import java.security.{KeyStore, SecureRandom}

import javax.net.ssl.{KeyManagerFactory, SSLContext, TrustManagerFactory}

object SSLContextFactory {
  def newInstance(passphrase: String): SSLContext = {
    val inputstream = getClass.getClassLoader.getResourceAsStream("/keystore.pkcs12")
    val password = passphrase.toCharArray
    val keystore = KeyStore.getInstance("PKCS12")
    keystore.load(inputstream, password)
    require(keystore != null, "Keystore is null. Load a valid keystore file.")

    val keyManagerFactory = KeyManagerFactory.getInstance("SunX509")
    keyManagerFactory.init(keystore, password)

    val trustManagerFactory = TrustManagerFactory.getInstance("SunX509")
    trustManagerFactory.init(keystore)

    val sslContext = SSLContext.getInstance("TLS")
    sslContext.init(keyManagerFactory.getKeyManagers, trustManagerFactory.getTrustManagers, new SecureRandom)
    sslContext
  }
}