import socket
import threading


class ServerThread(threading.Thread):

    def __init__(self, clientSocket, clientAddress):
        threading.Thread.__init__(self)
        self.clientAddress = clientAddress
        self.clientSocket = clientSocket

        print("Client added: ", clientSocket)

    def run(self):
        print("Running thread ", self.getName())
        receivedData = clientSocket.recv(1024).decode()
        print(receivedData)
        clientSocket.send("Hello Client!".encode())
        clientSocket.close()


TCP_IP = '0.0.0.0'
TCP_PORT = 54321

serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
serverSocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
serverSocket.bind((TCP_IP, TCP_PORT))

print("Server started")
print("Waiting on port", TCP_PORT)

dictClientAddress_ThreadName = {}

while True:
    serverSocket.listen(1)
    (clientSocket, clientAddress) = serverSocket.accept()
    newClientThread = ServerThread(clientAddress, clientSocket)
    dictClientAddress_ThreadName.update(clientAddress, newClientThread.getName())
    newClientThread.start()
