all: compile reg

reg:
	rmiregistry -J-classpath -Jbuild/ $(A0)

rs:
	java -classpath build/:resources/postgresql.jar server.StockDeProdutosServer $(A0) $(A1) $(A2) $(A3) $(A4)

rc:
	java -classpath build/:resources/sqlite-jdbc-3.30.1.jar client.StockDeProdutosClient $(A1) $(A0) $(A2)

compile:
	javac -d build  src/server/*.java src/client/*.java