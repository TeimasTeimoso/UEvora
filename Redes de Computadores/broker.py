import socket, select, traceback, common, datetime, pickle
from common import *

SOCKET_LIST = []
SENSOR_COLLECTION = {}
BLOCK_LIST = []
# Diconario com as ultimas leituras de cada local
LOCATION_LAST_ENTRY = {}
updateSubscription = None

# Funcao que remove o sensor
def deactivateSensor(sensorID):
    # Se a key nao existir, diz isso, caso contrario elimina
    if SENSOR_COLLECTION.pop(sensorID, None) is None:
        print("There's not a sensor with a matching ID.")
    else:
        print("Sensor deactivated successfully")

# Lista os locais em que existem um certo poluente
def listLocals(poluent):
    locals = []

    for sensor in SENSOR_COLLECTION:
        if (SENSOR_COLLECTION[sensor].sType == poluent) and (SENSOR_COLLECTION[sensor].location not in locals):
            locals.append(SENSOR_COLLECTION[sensor].location)

    return locals

# escolhe as operações do sensor
def sensorOperations(s, data):

    # Caso seja um sensor
    if isinstance(data, Sensor):
        # Verifica se o sensor existe na hashtable
        if data.id in SENSOR_COLLECTION.keys():
            return

        # Verifica que sensor com este id não foi já removido
        if data.id not in BLOCK_LIST:
            SENSOR_COLLECTION[data.id] = data
            # Se a localizacao do sensor for nova, adiciona no dicionario
            if data.location not in LOCATION_LAST_ENTRY.keys():
                LOCATION_LAST_ENTRY[data.location] = subscribedLocation(None) 

    elif isinstance(data, SensorData):
        if data.id not in SENSOR_COLLECTION.keys():
            s.send("0".encode())
            return
        else: s.send("1".encode())

        #PRINT DE TESTE
        print(data.id + " || " + str(data.date) + " || " + str(data.value) + " || " + data.unit)
        # Obtem local do setor a partir do ID
        locat = SENSOR_COLLECTION[data.id].location
        # Envia leitura para a respetiva queue
        SENSOR_COLLECTION[data.id].sampleList.pushToQueue(data)

        LOCATION_LAST_ENTRY[locat].sample = data
        LOCATION_LAST_ENTRY[locat].updated = False

        return locat


def adminOperations(s, op, data):
    # obter última leitura do sensor com identificador X
    if op == "1":
        if data in SENSOR_COLLECTION.keys():
            sensorLastEntry = SENSOR_COLLECTION[data].sampleList.getLastEntry()
            s.send(pickle.dumps(sensorLastEntry))
        #Caso o sensor não exista       
        else:
            s.send(pickle.dumps(0))

    # Lista os sensores que existem
    elif op == "2":
        s.send(pickle.dumps(SENSOR_COLLECTION))
    
    # Receber ficheiro de firmware
    elif op == "3":

        fileData = str(data)[2:-1].split(" ")
        sensorT = fileData[0]
        fVersion = fileData[1]
        
        #Descobrir que sensores precisam de ser atualizados
        for sensor in SENSOR_COLLECTION:
            if (SENSOR_COLLECTION[sensor].sType == sensorT) and (SENSOR_COLLECTION[sensor].version < fVersion):
                SENSOR_COLLECTION[sensor].version = fVersion
                filename = str(SENSOR_COLLECTION[sensor].id) + ".txt"
                file = open(filename, 'wb')
                file.write(data)

    ## Desativa sensor (ainda nao funciona)
    elif op == "4":
        deactivateSensor(data)


def publicOperations(s, op, data, sendingSocket):
    if op == "1":
        locations = listLocals(data)
        s.send(pickle.dumps(locations))
    
    elif op == "2":
        if data in LOCATION_LAST_ENTRY.keys():
            lastEntry = LOCATION_LAST_ENTRY[data]
            s.send(pickle.dumps(lastEntry))
        else:
            s.send(pickle.dumps(0))

    elif op == "3":
        if data in LOCATION_LAST_ENTRY.keys():
            LOCATION_LAST_ENTRY[data].sList.append(sendingSocket)
            s.send(pickle.dumps(1))
        else:
            s.send(pickle.dumps(0))

            

## Cria um socket usando IPV4 e protocolo TCP
serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
serverSocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
serverSocket.bind(("", PORT))
## Valor recomendado nas docs de python (Backlog Queue)
serverSocket.listen(15)
serverSocket.setblocking(0)
## Adiciona o socket do Broker à lista de sockets
SOCKET_LIST.append(serverSocket)


while True:

    #apagamos sockets que morreram entretanto
    for sock in SOCKET_LIST:
        if sock.fileno() < 0:
            SOCKET_LIST.remove(sock)

    # (DA AULA) agora, esperamos que haja dados em algum dos sockets que temos
    rsocks,_,_ = select.select(SOCKET_LIST, [], [])

    for sock in rsocks:
        if sock == serverSocket: #Ha uma nova ligacao
            newSock, adress = serverSocket.accept()
            newSock.setblocking(0)
            SOCKET_LIST.append(newSock)
        else:
            try:
                ## Recebe a informação para o Buffer
                received = sock.recv(RECV_BUFFER)

                if received:
                    decodedData = decodeData(received)
                    ## Operacoes dos sensores
                    if decodedData[0] == "S":
                        updateSubscription = sensorOperations(sock, decodedData[1])
                    ## Operacoes de admin client
                    elif decodedData[0] == "A":
                        adminOperations(sock, decodedData[1], decodedData[2])
                    ## Operacoes de public client
                    elif decodedData[0] == "P":
                        publicOperations(sock, decodedData[1], decodedData[2], decodedData[3])

                else:
                    print("Client disconnected 1")
                    sock.close()
                    SOCKET_LIST.remove(sock)

            except Exception as e:
                    print("Client disconnected")
                    print("Exception -> %s" % (e,))
                    print(traceback.format_exc())
                    
                    sock.close()
                    SOCKET_LIST.remove(sock)
                                   
    # PUBLISH SUBSCRIBE
    if updateSubscription is not None and not LOCATION_LAST_ENTRY[updateSubscription].updated:
        for sample in LOCATION_LAST_ENTRY[updateSubscription].sList:
            i = 0
            for conn in SOCKET_LIST:
                if i == 0:
                    i=i+1
                else:
                    if conn.getpeername() == sample:
                        conn.send(pickle.dumps(LOCATION_LAST_ENTRY[updateSubscription].sample))

                        LOCATION_LAST_ENTRY[updateSubscription].updated = True


serverSocket.close()
