Akka Http
---------
>Project contains Akka Http feature tests and apps.

SSL
---
>See:
1. https://blog.knoldus.com/create-a-self-signed-ssl-certificate-using-openssl/
2. https://blog.knoldus.com/how-to-create-a-keystore-in-pkcs12-format/

SSL Passwords
-------------
1. Server Key Passphrase: test
2. Challenge Password: test
3. Server PEM: test
4. Export Password: test

SSL Artifacts
-------------
>Located in src/main/resources
1. server.crt
2. server.csr
3. server.key
4. server.pem
5. keystore.pkcs12

Https
-----
>See ( https://doc.akka.io/docs/akka-http/current/server-side/server-https-support.html#using-https ) for details.

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
   * NowSslApp - curl -vv https://localhost:7777/api/v1/now
4. Browser:
   * NowApp    - http://localhost:7777/
   * NowSslApp - https://localhost:7777/