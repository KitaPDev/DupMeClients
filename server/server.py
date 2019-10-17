import socket
import random
from match_thread import MatchThread
from client_thread import *

TCP_IP = '0.0.0.0'
TCP_PORT = 54321


class MainServer(threading.Thread):
    lsClientThreads: list
    lsClientThreads_unmatched: list

    def __init__(self, ip_address, port):
        super().__init__()
        self.setName('Thread-MainServer')
        self.ipAddress = ip_address
        self.port = port

    def run(self):
        serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        serverSocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        serverSocket.bind((TCP_IP, TCP_PORT))

        print(self.getName() + ": Server started")
        print(self.getName() + ": Waiting on port ", TCP_PORT)

        while True:
            serverSocket.listen(10)
            (clientSocket, clientAddress) = serverSocket.accept()
            clientThread = ClientThread(clientSocket, clientAddress)

            self.lsClientThreads.append(clientThread)

            clientThread.run()

    def get_clientThreads(self) -> []:
        """Returns a list of running ClientThreads."""
        return self.lsClientThreads

    def remove_clientThread(self, clientThread: ClientThread):
        """Removes stopped ClientThread from MainServer thread list."""
        self.lsClientThreads.remove(clientThread)


def stop_clientThread(clientThread: ClientThread):
    """Removes stopped ClientThread from MainServer thread and main thread list."""
    mainServerThread.remove_clientThread(clientThread)
    lsClientThreads_unmatched.remove(clientThread)


mainServerThread = MainServer(TCP_IP, TCP_PORT)
mainServerThread.run()

lsClientThreads_unmatched: list
lsMatchThreads_inProgress: list

while True:
    if mainServerThread.isAlive():
        lsClientThreads_unmatched = mainServerThread.get_clientThreads()

    if len(lsClientThreads_unmatched) >= 2:
        iFirstClientThread = int(random.randrange(0, len(lsClientThreads_unmatched)))
        iSecondClientThread = int(random.choice(range(0, iFirstClientThread) + range(iFirstClientThread,
                                                                                     len(lsClientThreads_unmatched))))
        firstClientThread: ClientThread
        secondClientThread: ClientThread
        firstClientThread = lsClientThreads_unmatched[iFirstClientThread]
        secondClientThread = lsClientThreads_unmatched[iSecondClientThread]

        matchThread = MatchThread(firstClientThread, secondClientThread)
        firstClientThread.set_matchThread(matchThread)
        secondClientThread.set_matchThread(matchThread)
        matchThread.run()

        mainServerThread.remove_clientThread(firstClientThread)
        mainServerThread.remove_clientThread(secondClientThread)

        lsClientThreads_unmatched.remove(firstClientThread)
        lsClientThreads_unmatched.remove(secondClientThread)

        lsMatchThreads_inProgress.append(matchThread)
