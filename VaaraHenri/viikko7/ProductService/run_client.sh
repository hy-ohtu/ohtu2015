export OHTU_KRYPTO="salainen"
export CONF_API="http://localhost:4569/configurations"
mvn clean install exec:java -Dexec.mainClass=ohtu.productservice.Main
