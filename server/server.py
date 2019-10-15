import socket

from client_thread import *


TCP_IP = '0.0.0.0'
TCP_PORT = 54321

serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
serverSocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
serverSocket.bind((TCP_IP, TCP_PORT))

print("Log: Server started")
print("Log: Waiting on port", TCP_PORT)

dictClientAddress_ThreadName = {}

while True:
    serverSocket.listen(1)
    (clientSocket, clientAddress) = serverSocket.accept()
    newClientThread = ClientThread(clientSocket, clientAddress)

    strClientAddress = clientAddress[0] + "," + str(clientAddress[1])
    dictClientAddress_ThreadName.update({strClientAddress: newClientThread.getName()})

    newClientThread.start()
