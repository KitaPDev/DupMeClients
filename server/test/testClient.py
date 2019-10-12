import socket

clientSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
clientSocket.connect(('192.168.1.64', 40404))
clientSocket.send('Hello Server!'.encode())
print(clientSocket.recv(1024).decode())

clientSocket.close()
