#include <SPI.h>
#include <MFRC522.h>
#include <ESP8266WiFi.h>
#include <FirebaseESP8266.h>

// RFID
#define SS_PIN 2     // D4
#define RST_PIN 0    // D3
MFRC522 rfid(SS_PIN, RST_PIN);

// WiFi
const char* ssid = "Thai Dat";
const char* password = "24683579";

// Firebase config
#define FIREBASE_HOST "rfid-bai-giu-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH "AIzaSyCxpbysr5wgEWVQ-Pf69XDVblb78jbtZvM"

FirebaseData firebaseData;
FirebaseAuth auth;
FirebaseConfig config;

#define BUTTON_PIN 4   // D2
#define LED_PIN    5    // D1

bool isEntryMode = true;           // true = entry, false = exit
bool lastButtonState = HIGH;
unsigned long lastDebounceTime = 0;
const unsigned long debounceDelay = 50;

void setup() {
  Serial.begin(115200);
  SPI.begin();
  rfid.PCD_Init();

  pinMode(LED_PIN, OUTPUT);
  pinMode(BUTTON_PIN, INPUT_PULLUP);

  digitalWrite(LED_PIN, HIGH); // chế độ mặc định là "entry"

  // Kết nối Wi-Fi
  Serial.println("Đang kết nối Wi-Fi...");
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.print(".");
  }
  Serial.println("\nĐã kết nối Wi-Fi!");

  // Kết nối Firebase
  config.host = FIREBASE_HOST;
  config.signer.tokens.legacy_token = FIREBASE_AUTH;
  Firebase.begin(&config, &auth);
  Firebase.reconnectWiFi(true);
}

void loop() {
  // Đọc trạng thái công tắc
 if (digitalRead(BUTTON_PIN) == LOW) {  // Nếu nút nhấn được nhấn
    delay(30);  // Chống dội nút

    if (digitalRead(BUTTON_PIN) == LOW) {  // Kiểm tra lại
      isEntryMode = !isEntryMode;  // Đảo trạng thái

      // Bật/tắt LED dựa vào trạng thái
      digitalWrite(LED_PIN, isEntryMode ? HIGH : LOW);

      // In ra trạng thái mới
      Serial.println(isEntryMode ? "Entry Mode: ON" : "Entry Mode: OFF");
    }

    // Đợi thả nút
    while (digitalRead(BUTTON_PIN) == LOW);
  }


  // ==== Kiểm tra có quét thẻ không ====
  if (!rfid.PICC_IsNewCardPresent() || !rfid.PICC_ReadCardSerial()) {
    return;
  }

  // ==== Đọc UID ====
  String uid = "";
  for (byte i = 0; i < rfid.uid.size; i++) {
    if (rfid.uid.uidByte[i] < 0x10) uid += "0";
    uid += String(rfid.uid.uidByte[i], HEX);
  }
  uid.toUpperCase();
  Serial.print("UID: ");
  Serial.println(uid);

  String actionText = isEntryMode ? "entry" : "exit";

  // ==== Gửi lên Firebase ====
  if (Firebase.deleteNode(firebaseData, "/notifications")) {
    //Serial.println("🗑️ Đã xoá toàn bộ node /notifications cũ.");
  } else {
    Serial.print("Lỗi xoá /notifications: ");
    Serial.println(firebaseData.errorReason());
  }

  String notifyPath = "/notifications/" + uid;

  bool ok1 = Firebase.setString(firebaseData, notifyPath + "/action", actionText);
  bool ok2 = Firebase.setString(firebaseData, notifyPath + "/cardId", uid);

  if (ok1 && ok2) {
    Serial.print("Gửi thành công: ");
    Serial.println(actionText == "entry" ? "Xe vào" : "Xe ra");
  } else {
    Serial.print("Lỗi gửi Firebase: ");
    Serial.println(firebaseData.errorReason());
  }

  delay(2000); // chờ để tránh quét lại quá nhanh
}
