import socket, common, datetime, pickle, random, time
from common import *
SENSOR_ID = "S"
UNIT = "µg/m³"

# Periodo de intervalo de envio de dados
TIMER = 10
SENSORLIST = []
# O Sensor ainda existe
ALIVE = True

def createSensor(s, sensorID, sensorType, sensorLocation, firmwareVersion, port):
    newSensor = Sensor(sensorID, sensorType, sensorLocation, firmwareVersion, port)
    SENSORLIST.append(newSensor)
    info = pickle.dumps((SENSOR_ID,newSensor))
    s.send(info)

def sendSample(s, sensorID, date, sampleValue, unit, firmwareVersion):
    newSample = SensorData(sensorID, date, sampleValue, unit, firmwareVersion)
    info = pickle.dumps((SENSOR_ID, newSample))
    s.send(info)

## Cria um socket usando IPV4 e protocolo TCP
clientSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
clientSocket.connect((HOST, PORT))

sID = input ("Sensor ID:") 
sType = input("Sensor type:")
location = input("Sensor location:")
fVersion = input("Firmware Version:")
createSensor(clientSocket, sID, sType, location, fVersion, clientSocket.getsockname())
    
while True:

    value = random.randint(1, 15)
    sendSample(clientSocket, sID, datetime.datetime.now(), value, UNIT, fVersion)
    time.sleep(TIMER)
    
    rcv = clientSocket.recv(RECV_BUFFER)
    if rcv.decode() is "0":
        break

clientSocket.close()