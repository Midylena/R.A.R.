#include <ESP8266WiFi.h>
#include<SPI.h>

char buff[]="1\n";

#ifndef STASSID
#define STASSID "MILENA"
#define STAPSK  "fa17186770"
#endif

const char* ssid = STASSID;
const char* password = STAPSK;

WiFiServer server(50);

void setup() {
  Serial.begin(9600);
  SPI.begin();

  pinMode(4, OUTPUT);
  digitalWrite(4, 0);


  Serial.println();
  Serial.println();
  Serial.print(F("Connecting to "));
  Serial.println(ssid);

  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(F("."));
  }
  Serial.println();
  Serial.println(F("WiFi connected"));

  server.begin();
  Serial.println(F("Server started"));

  Serial.println(WiFi.localIP());
}

void loop() {
  WiFiClient client = server.available();
  if (!client) {
    return;
  }
  Serial.println(F("new client"));

  client.setTimeout(5000); 

  String req = client.readStringUntil('\r');
  Serial.println(F("request: "));
  Serial.println(req);


  int val;
  if (req.indexOf(F("/gpio/0")) != -1) {
    val = 0;
  } else if (req.indexOf(F("/gpio/1")) != -1) {
    val = 1;
    for(int i=0; i<sizeof buff; i++)  
    SPI.transfer(buff[i]);
    Serial.println(buff);
    delay(1000); 
  } else {
    Serial.println(F("invalid request"));
    val = digitalRead(4);
  }


  digitalWrite(4, val);

  while (client.available()) {
    client.read();
  }

  client.print(F("HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n<!DOCTYPE HTML>\r\n<html>\r\nGPIO is now "));
  client.print((val) ? F("high") : F("low"));
  client.print(F("<br><br>Click <a href='http://"));
  client.print(WiFi.localIP());
  client.print(F(":50/gpio/1'>here</a> to switch LED GPIO on, or <a href='http://"));
  client.print(WiFi.localIP());
  client.print(F(":50/gpio/0'>here</a> to switch LED GPIO off.</html>"));

  Serial.println(F("Disconnecting from client"));
}
