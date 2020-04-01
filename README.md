Akka Http
---------
>Project contains Akka Http feature tests and apps.

Https
-----
>For details see:
1. https://doc.akka.io/docs/akka-http/current/server-side/server-https-support.html
2. https://lightbend.github.io/ssl-config/CertificateGeneration.html
>Also see x509 text file and directory.

SSL
---
>Both JKS and PKCS12 keystore formats have been tested. Edit the now.ssl.app.conf as follows:
1. passphrase="password", keystorePath="./x509/localhost.jks", keystoreType="JKS", sslProtocol="TLS", algorithm="SunX509"
2. passphrase="password", keystorePath="./x509/localhost.p12", keystoreType="PKCS12", sslProtocol="TLS", algorithm="SunX509"
>See the x509.txt file, section [ Create PKCS12 Keystore ] for how to create the localhost.p12 keystore.    
 
Test
----
1. sbt clean test

Run
---
1. sbt run
   * [1] akka.http.NowApp
   * [2] akka.http.NowSslApp
2. Select app by number.
3. Curl:
   * NowApp    - curl http://localhost:7777/api/v1/now
   * NowSslApp - curl https://localhost:7443/api/v1/now
4. Browser:
   * NowApp    - http://localhost:7777/
   * NowSslApp - https://localhost:7443/