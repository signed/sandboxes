openssl genrsa -out server.key 2048
openssl req -new -key server.key -out server.csr
openssl x509 -req -days 365 -in server.csr -signkey server.key -out server.crt
openssl pkcs12 -export -in server.crt -name wischan -inkey server.key -out bundle.p12
openssl crl2pkcs7 -nocrl -certfile server.crt -out server.p7b