Akka Http
---------
>This project contains Akka Http feature tests.

SSL
---
>See ( https://blog.knoldus.com/create-a-self-signed-ssl-certificate-using-openssl/ ) for directions.
1. Server Key Passphrase: bhsf
2. Challenge Password: doctorZ

SSL Artifacts
-------------
>Located in src/main/resources
1. server.crt
2. server.csr
3. server.key

Test
----
1. sbt clean test

Run
---
1. sbt run
2. Open browser to: http://localhost:7777/