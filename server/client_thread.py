import socket
import threading


class ClientThread(threading.Thread):

    def __init__(self, clientSocket, clientAddress):
        threading.Thread.__init__(self)
        self.clientAddress = clientAddress
        self.clientSocket = clientSocket

        print("Log: Client added:", clientAddress[0], ":", clientAddress[1], "\n")

    def run(self):
        print("Log: Running thread " + self.getName() + "\n")

        receivedData = self.clientSocket.recv(1024).decode()
        print("Received data:" + receivedData + "\n")

        receivedData = receivedData.split()
        if receivedData.len() == 0:
            self.clientSocket.send("Invalid Process Request Syntax!")
            self.clientSocket.close()

        else:




        self.clientSocket.send("Hello Client!".encode())
        self.clientSocket.close()

        print("Log: " + self.getName() + " closed\n")
