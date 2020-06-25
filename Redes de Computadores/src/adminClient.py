import socket, common, pickle
from common import *
clientID = "A"

adminClient = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
adminClient.connect((HOST, PORT))
adminClient.settimeout(0.2)

def listSensors(collection):
    if(len(collection) == 0):
        print("No sensors are registred!")
    else:
        for ID in collection:
            print(f"ID: {collection[ID].id} || Type:{collection[ID].sType} || Location: {collection[ID].location} || Firmware Version: {collection[ID].version}")


print("Menu:\n"
      "1 - Get last entry for Sensor X;\n"
      "2 - List Sensors;\n"
      "3 - Send firmware update to Sensor X;\n"
      "4 - Deactivate sensor X.\n")

while True:
    choice = input("Choose: ")

    # Recebe a ultima leitura do sensor X
    if choice == "1":
        sensorID = input("Sensor ID: ")
        adminClient.send(pickle.dumps((clientID, choice, sensorID)))

        data = adminClient.recv(RECV_BUFFER)
        lastSample = decodeData(data)

        if isinstance(lastSample, SensorData):
            print(f"{lastSample.id} || {str(lastSample.date)} || {str(lastSample.value)} || {lastSample.unit}\n")
        else:
            print(f"Sensor {sensorID} does not exist.\n")
    
    # Lita todos os sensores existentes
    elif choice == "2":
        # Manda uma cena vazia para ser um tuplo com 3 posicoes
        # Tal como as outras opcoes no adminOptions
        adminClient.send(pickle.dumps((clientID, choice, "")))

        # Caso hajam muitos sensores e o buffer
        # nao tenha capacidade de receber tudo
        # de uma vez
        finalMessage = []    
        while True:
            try:
                data = adminClient.recv(RECV_BUFFER)
                finalMessage.append(data)
            except Exception as e:
                break
        decodedList = pickle.loads(b"".join(finalMessage))
        
        listSensors(decodedList)

    # Encaminhar ficheiros de firmware
    elif choice == "3":
        filename = input("Enter the file name: ")
        file = open(filename, 'rb')
        file_data = file.read(1024)
        adminClient.send(pickle.dumps((clientID, choice, file_data)))
        print("Data has been transmitted successfully")


    # Remove o sensor X e todas as suas ligacoes REVER
    elif choice == "4":
        sensorID = input("ID of the sensor to be removed: ")
        adminClient.send(pickle.dumps((clientID, choice, sensorID)))

    # Caso o utilizador introduza um comando que nao existe    
    else:
        break

adminClient.close()