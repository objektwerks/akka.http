Akka Http
---------
>Project contains Akka Http feature tests and apps.

SSL
---
>See ( https://blog.knoldus.com/create-a-self-signed-ssl-certificate-using-openssl/ ) for directions.
1. Server Key Passphrase: test
2. Challenge Password: jaaaannnn

SSL Artifacts
-------------
>Located in src/main/resources
1. server.crt
2. server.csr
3. server.key

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
3. Open browser to: http://localhost:7777/