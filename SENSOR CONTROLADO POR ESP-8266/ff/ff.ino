#include <SPI.h>

char buff [100];
volatile byte index;
volatile bool receivedone;  /* use reception complete flag */

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
int esp;

Ultrasonic ultrasonic(pino_trigger, pino_echo);

void setup (void)
{
  Wire.begin();  
  
  pinMode(pinoSensor1, INPUT); 
  pinMode(pinoSensor2, INPUT); 

  pinMode(ledAm1, OUTPUT); 
  pinMode(ledAm2, OUTPUT); 
  pinMode(ledVerd, OUTPUT); 
  pinMode(ledVerm, OUTPUT); 

  digitalWrite(ledVerd, LOW);
  digitalWrite(ledVerm, LOW);
  
  Serial.begin (9600);
  SPCR |= bit(SPE);         /* Enable SPI */
  pinMode(MISO, OUTPUT);    /* Make MISO pin as OUTPUT */
  index = 0;
  receivedone = false;
  SPI.attachInterrupt();    /* Attach SPI interrupt */
}


void loop (void)
{
  if (receivedone)          /* Check and print received buffer if any */
  {
    buff[index] = 0;
    Serial.println(buff);
    index = 0;
    receivedone = false;
    esp = Serial.parseInt();
    int valor = esp + 1;
    
    //Serial.println(valor);

      while(valor == 1) {
      float cmMsec;
      long microsec = ultrasonic.timing();
      cmMsec = ultrasonic.convert(microsec, Ultrasonic::CM);
      //Exibe informacoes no serial monitor
      //Serial.print("Distancia em cm: ");
      //Serial.println(cmMsec);
      delay(1000);
      
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
      }   
    else{
        digitalWrite(ledAm1, LOW); 
        digitalWrite(ledAm2, LOW);
        digitalWrite(ledVerm, LOW);
        digitalWrite(ledVerd, LOW);
        comando = 0;
      
    }
    }
  }
}

// SPI interrupt routine
ISR (SPI_STC_vect)
{
  uint8_t oldsrg = SREG;
  cli();
  char c = SPDR;
  if (index <sizeof buff)
  {
    buff [index++] = c;
    if (c == '\n'){     /* Check for newline character as end of msg */
     receivedone = true;
    }
  }
  SREG = oldsrg;
}
