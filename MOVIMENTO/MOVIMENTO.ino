
#define pinCLK   4
#define pinEN    7
#define pinSER   8
#define pinLATCH 12

#define pinPWM1 11
#define pinPWM2 3
#define pinPWM3 5
#define pinPWM4 6

#define pinSensorDir 9
#define pinSensorEsq 10

#define MOTOR1 0
#define MOTOR2 1
#define MOTOR3 2
#define MOTOR4 3

#define PARADO  0
#define FRENTE  1
#define REVERSO 2

bool sensorDirAnt, sensorEsqAnt;

#include "Wire.h"
#define myAdress 0x08



void setup() {
  pinMode(pinCLK,   OUTPUT);  
  pinMode(pinEN,    OUTPUT);  
  pinMode(pinSER,   OUTPUT);  
  pinMode(pinLATCH, OUTPUT); 
  
  pinMode(pinPWM1, OUTPUT); 
  pinMode(pinPWM2, OUTPUT); 
  pinMode(pinPWM3, OUTPUT); 
  pinMode(pinPWM4, OUTPUT); 
  
  pinMode(pinSensorDir, INPUT);
  pinMode(pinSensorEsq, INPUT);
  
  digitalWrite(pinEN, LOW);  
  velocidade(70);
  
  sensorDirAnt = !digitalRead(pinSensorDir); 
  sensorEsqAnt = !digitalRead(pinSensorEsq); 

  Serial.begin(9600);
  Wire.begin(myAdress);
Wire.onReceive(receiveEvent);
}

void loop() {  
  
 //sentido(FRENTE);
}

void receiveEvent(int howMany) {
  if (Wire.available()) {
    char received = Wire.read();

    switch(received)
    {
      case 0:
        sentido(PARADO);
        break;

      case 1:
        sentido(FRENTE);
        break;

      case 2:
        sentidoMotorDir(FRENTE);
        sentidoMotorEsq(PARADO);
        break;

      case 3:
        sentidoMotorEsq(FRENTE);
        sentidoMotorDir(PARADO);
        break;

      default:
        sentido(PARADO);      
    }
    
    delay(20);
    }
  }



void sentido(byte sentido) {
  sentidoMotor(MOTOR1, sentido, false);
  sentidoMotor(MOTOR2, sentido, false);
  sentidoMotor(MOTOR3, sentido, false);
  sentidoMotor(MOTOR4, sentido, true);  
}

void sentidoMotorDir(byte sentido) {
  sentidoMotor(MOTOR3, sentido, false);
  sentidoMotor(MOTOR4, sentido, true);  
}

void sentidoMotorEsq(byte sentido) {
  sentidoMotor(MOTOR1, sentido, false);
  sentidoMotor(MOTOR2, sentido, true);
}

void sentidoMotor(byte motor, byte sentido, bool envia) {
static byte enviar;
  
static byte bitMotorF[4] = {5, 3, 7, 0};
static byte bitMotorR[4] = {4, 6, 1, 2};

  bool bitF = (sentido == FRENTE);
  bool bitR = (sentido == REVERSO);  
  
  bitWrite(enviar, bitMotorF[motor], bitF );
  bitWrite(enviar, bitMotorR[motor], bitR );
  
  if (envia) {
  digitalWrite(pinLATCH, LOW);
    shiftOut(pinSER, pinCLK, LSBFIRST, enviar);
    digitalWrite(pinLATCH, HIGH);
  }
}

void velocidade(byte velocidade) {
  velocidadeMotor(MOTOR1, velocidade); 
  velocidadeMotor(MOTOR2, velocidade); 
  velocidadeMotor(MOTOR3, velocidade); 
  velocidadeMotor(MOTOR4, velocidade); 
}

void velocidadeMotor(byte motor, byte velocidade) {
static byte pinPWM[4] = {pinPWM1, pinPWM2, pinPWM3, pinPWM4};

  analogWrite(pinPWM[motor], velocidade); 
}
