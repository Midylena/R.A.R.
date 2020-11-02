#include <Ultrasonic.h>
#include "Wire.h"

#define slaveAdress 0x08

#define ledAm1 2
#define ledAm2 3
#define ledVerd 4
#define ledVerm 5
#define pinoSensor1 6
#define pinoSensor2 7
#define pino_trigger 8
#define pino_echo 9

int comando;


Ultrasonic ultrasonic(pino_trigger, pino_echo);

void setup() {
  Wire.begin();  
  
  Serial.begin(9600);
  Serial.println("Lendo dados do sensor...");
  pinMode(pinoSensor1, INPUT); 
  pinMode(pinoSensor2, INPUT); 

  pinMode(ledAm1, OUTPUT); 
  pinMode(ledAm2, OUTPUT); 
  pinMode(ledVerd, OUTPUT); 
  pinMode(ledVerm, OUTPUT); 

  digitalWrite(ledVerd, LOW);
  digitalWrite(ledVerm, LOW);
}

void loop() {
        float cmMsec;
        long microsec = ultrasonic.timing();
        cmMsec = ultrasonic.convert(microsec, Ultrasonic::CM);
        //Exibe informacoes no serial monitor
        Serial.print("Distancia em cm: ");
        Serial.println(cmMsec);
        delay(500);
      
      if (cmMsec <= 20){
        digitalWrite(ledAm1, LOW); 
        digitalWrite(ledAm2, LOW);
        digitalWrite(ledVerm, HIGH);
        digitalWrite(ledVerd, LOW);
    
        comando = 0;
    
        Wire.beginTransmission(slaveAdress);
        Wire.write(comando); // envia um byte contendo o estado do LED
        Wire.endTransmission();
        }
      else if(cmMsec > 20) {
         digitalWrite(ledVerm, LOW);
         digitalWrite(ledVerd, HIGH);
         
         if (digitalRead(pinoSensor1) == HIGH and digitalRead(pinoSensor2) == LOW){
              digitalWrite(ledAm1, LOW); 
              digitalWrite(ledAm2, HIGH);
              
               comando = 2;
               Wire.beginTransmission(slaveAdress);
               Wire.write(comando); // envia um byte contendo o estado do LED
               Wire.endTransmission();
               
        }else if (digitalRead(pinoSensor1) == LOW and digitalRead(pinoSensor2) == HIGH){
              digitalWrite(ledAm1, HIGH); 
              digitalWrite(ledAm2, LOW);
              
               comando = 3;
               Wire.beginTransmission(slaveAdress);
               Wire.write(comando); // envia um byte contendo o estado do LED
               Wire.endTransmission();
              
        }else if (digitalRead(pinoSensor1) == LOW and digitalRead(pinoSensor2) == LOW){
              digitalWrite(ledAm1, HIGH); 
              digitalWrite(ledAm2, HIGH);
    
              
               comando = 1;
               Wire.beginTransmission(slaveAdress);
               Wire.write(comando); // envia um byte contendo o estado do LED
               Wire.endTransmission();
               
        }else if (digitalRead(pinoSensor1) == HIGH and digitalRead(pinoSensor2) == HIGH){
              digitalWrite(ledAm1, LOW); 
              digitalWrite(ledAm2, LOW);
    
              
               comando = 0;
               Wire.beginTransmission(slaveAdress);
               Wire.write(comando); // envia um byte contendo o estado do LED
               Wire.endTransmission();
        
      }   
    else{
        digitalWrite(ledAm1, LOW); 
        digitalWrite(ledAm2, LOW);
        digitalWrite(ledVerm, LOW);
        digitalWrite(ledVerd, LOW);
        comando = 0;
        Wire.beginTransmission(slaveAdress);
        Wire.write(comando); // envia um byte contendo o estado do LED
        Wire.endTransmission();
      }
      }
  }
