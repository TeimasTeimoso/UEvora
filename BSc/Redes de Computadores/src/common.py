import pickle

HOST = "10.2.14.57"
PORT = 8080
RECV_BUFFER = 4096

class SampleQueue:
    def __init__(self, size):
        self.queue = []
        self.occupation = -1
        self.qSize = size

    def pushToQueue(self, sample):
        if self.occupation < self.qSize-1:
            self.queue.append(sample)
        else:
            self.queue[self.occupation%self.qSize-1] = sample
        
        self.occupation = self.occupation+1

    def getLastEntry(self):
        if self.occupation < self.qSize-1:
            return self.queue[self.occupation]

        return self.queue[self.occupation%self.qSize]

class Sensor:
    # Inicializa um objeto do tipo Sensor
    def __init__(self, sensorID, sensorType, sensorLocation, firmwareVersion, port):
        self.id = sensorID
        self.sType = sensorType
        self.location = sensorLocation
        self.version = firmwareVersion
        # Queue de tamanho 10
        self.sampleList = SampleQueue(10)
        self.connectionPort = port

class SensorData:
    ## Inicializa um objeto SensorData onde estão os parametros a ser enviados pelo sensor ao broker
    def __init__(self, sensorID, sampleDate, sampleValue, sampleUnit, firmwareVersion):
        self.id = sensorID
        self.date = sampleDate
        self.value = sampleValue
        self.unit = sampleUnit
        self.version = firmwareVersion

class subscribedLocation:
    def __init__(self, sample):
        self.sample = sample
        self.sList = []
        # Estado inicial em que "comeca" com a leitura mais recente
        self.updated = True



# Descodifica a informação recebida
def decodeData(data):
    data = pickle.loads(data)
    return data
