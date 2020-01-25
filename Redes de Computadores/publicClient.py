import socket, common, datetime, pickle, select
import sys
from common import *
clientID = "P"

# O user ainda não subscreveu nenhum lugar
SUBSCRIBED = False

publicClientSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
publicClientSocket.connect((HOST, PORT))
notificationSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
notificationSocket.connect((HOST, PORT))


print("Menu:\n"
      "1 - Get locations of a poluent;\n"
      "2 - Get last sample from a location:\n"
      "3 - Publish-Subscribe\n")

while True:

    # para ser possivel escolher qual o socket que está a comunicar 
    socks,_,_ = select.select([notificationSocket, publicClientSocket, sys.stdin], [], [])

    for sock in socks:
        # Caso existam novas leituras
        if sock == notificationSocket:
            received = notificationSocket.recv(RECV_BUFFER)
            sample = decodeData(received)
            print("NOTIFICATION:\n")
            print(f"Last Sample: {sample.id} || {str(sample.date)} || {str(sample.value)} || {sample.unit}\n")
        elif sock == sys.stdin:
            choice = input()
                    
            # Obtem locais onde existe um poluente
            if choice == "1":
                poluent = input("Choose a poluent: ")
                publicClientSocket.send(pickle.dumps((clientID, choice, poluent, "")))
                
                data = publicClientSocket.recv(RECV_BUFFER)    
                decodedList = decodeData(data)

                if len(decodedList) == 0:
                    print("The chosen poluent does not exist in any place.\n")
                else:
                    for locat in decodedList:
                        print(locat)
                    print("\n")
            
            # Obtem a ultima leitura do local X
            elif choice == "2":
                location = input("Choose a location to get last sample:")
                publicClientSocket.send(pickle.dumps((clientID, choice, location, "")))

                data = publicClientSocket.recv(RECV_BUFFER)    
                chosenLocation = decodeData(data)

                if chosenLocation is not 0:
                    print(f"SELECTED LOCATION:{chosenLocation.sample.id} || {str(chosenLocation.sample.date)} || {str(chosenLocation.sample.value)} || {chosenLocation.sample.unit}\n")
                else:
                    print("Selected sensor does not exist.\n")

            # Publish Subscribe
            elif choice == "3":
                if not SUBSCRIBED:
                    location = input("Choose a location to get subscribed:")
                    publicClientSocket.send(pickle.dumps((clientID, choice, location, notificationSocket.getsockname())))

                    data = publicClientSocket.recv(RECV_BUFFER)
                    answer = decodeData(data)

                    if answer is 1:
                        SUBSCRIBED = True
                    else:
                        print("That location does not have any sensor\n")
            else:
                break  

publicClientSocket.close()
