#include <DHT.h>

#define DHTPIN 2
#define DHTTYPE DHT11

DHT dht(DHTPIN, DHTTYPE);

#define num_readings 10 //tính trung bình cho dht22 (lọc trung bình)
float tempReadings[num_readings]; 
float humReadings[num_readings];  
int readIndex = 0;  
float last_temp, last_humi =0;

void setup() {
  Serial.begin(9600);
  dht.begin();
  for (int i = 0; i < num_readings; i++) {
    tempReadings[i] = 0;
    humReadings[i] = 0;
  }
}

void loop() {
 
  float humi = dht.readHumidity();
  float temp = dht.readTemperature();

  if (isnan(humi) || isnan(temp)) {
    Serial.println("Không thể đọc từ cảm biến DHT22!");
    return;
  }

 
  tempReadings[readIndex] = temp;
  humReadings[readIndex] = humi;

  
  readIndex = (readIndex + 1) % num_readings;

  // Tính trung bình để lọc nhiễu (bộ lọc trung bình)
  float tempSum = 0, humSum = 0;
  for (int i = 0; i < num_readings; i++) {
    tempSum += tempReadings[i];
    humSum += humReadings[i];
  }
  float avgTemp = tempSum / num_readings;
  float avgHum = humSum / num_readings;

  if ((last_humi != avgHum) || (last_temp != avgTemp) ){
    last_humi = avgHum;
    last_temp = avgTemp;
    Serial.print("Temp:");
    Serial.print(avgTemp);
    Serial.print(",Hum:");
    Serial.println(avgHum);
  }

}