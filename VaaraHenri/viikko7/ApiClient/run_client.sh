export CONF_API="http://localhost:4569/configurations"
mvn clean install exec:java -Dexec.mainClass=ohtu.apiclient.Main
