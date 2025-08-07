#include <SoftwareSerial.h>
#include <Servo.h>

SoftwareSerial sim900(7, 8); // RX, TX (nối với TX, RX SIM900A)
Servo myServo;

String incoming = "";

void setup() {
  Serial.begin(9600);
  sim900.begin(9600);
  myServo.attach(9); // Servo gắn chân D9

  delay(1000);
  sim900.println("AT");
  delay(1000);
  sim900.println("AT+CMGF=1"); // Chế độ text
  delay(1000);
  sim900.println("AT+CNMI=1,2,0,0,0"); // Hiển thị tin nhắn mới tự động
}

void loop() {
  while (sim900.available()) {
    char c = sim900.read();
    incoming += c;

    if (c == '\n') {
      incoming.trim();
      Serial.println("Tin nhắn: " + incoming);

      if (incoming.startsWith("Servo")) {
        int angle = incoming.substring(5).toInt();
        angle = constrain(angle, 0, 180);
        myServo.write(angle);
        Serial.println("Servo quay: " + String(angle) + " độ");
      }

      incoming = "";
    }
  }
}
