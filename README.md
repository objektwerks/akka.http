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
>See ( https://doc.akka.io/docs/akka-http/current/server-side/server-https-support.html ) for details.
       
Test
----
1. sbt clean test

Run
---
1. sbt run
   * [1] akka.http.NowApp
   * [2] akka.http.NowSslApp
2. Select app by number.
3. Curl: **Doesn't work!**
   * NowApp    - curl http://localhost:7777/api/v1/now
   * NowSslApp - curl --capath ./src/main/resources \
                      --cert-type PEM \
                      --cert ./src/main/resources/server.pem:test \
                      --cacert ./src/main/resources/server.pem \
                      --cert-status \
                      -v https://localhost:7443/api/v1/now
4. WGet: **Doesn't work!**                      
   * NowSslApp - wget --certificate=./src/main/resources/server.pem \
                      --certificate-type=PEM \
                      --ca-certificate=./src/main/resources/server.crt \
                      --ca-directory=./src/main/resources \
                      https://localhost:7443/api/v1/now
4. Browser:
   * NowApp    - http://localhost:7777/
   * NowSslApp - https://localhost:7443/