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
   * NowSslApp - curl --cacert /etc/ssl/cert.pem \
                      --capath /etc/ssl/ \
                      --cert-type PEM \
                      --cert /etc/ssl/server.pem:test \
                      -v https://localhost:7443/api/v1/now
4. WGet: **Doesn't work!**                      
   * NowSslApp - wget --ca-certificate=/etc/ssl/cert.pem \
                      --ca-directory=/etc/ssl/ \
                      --certificate-type=PEM \
                      --certificate=/etc/ssl/server.pem \
                      https://localhost:7443/api/v1/now
4. Browser:
   * NowApp    - http://localhost:7777/
   * NowSslApp - https://localhost:7443/