#include <SoftwareSerial.h>
SoftwareSerial softSerial(12, 13); // RX, TX
String command = ""; // Stores response of bluetooth device
int baudrate = 9600;
//Define Pins
//Motor A
int enableA = 10;
int pinA1 = 2;
int pinA2 = 3;

//Motor B
int enableB = 9;
int pinB1 = 4;
int pinB2 = 5;
//define time for run
// in milliseconds
int running = 5000; //10 secons
boolean play;
String carDirection;

void setup() {
  Serial.begin(baudrate);
  softSerial.begin(baudrate);
  Serial.println("Car ready!");
  //configure pin modes
  pinMode (enableA, OUTPUT);
  pinMode (pinA1, OUTPUT);
  pinMode (pinA2, OUTPUT);

  pinMode (enableB, OUTPUT);
  pinMode (pinB1, OUTPUT);
  pinMode (pinB2, OUTPUT);

  play = false;
  carDirection = "up";
}

void loop() {


  // Read Bluetooth device output if available.
  if (Serial.available()) softSerial.write(Serial.read());
  while (softSerial.available() > 0) {
    command += (char)softSerial.read();
    delay(10);
  }
  if (command != "") {
    updateCommand(command);
  }
  command = "";
  robotTick();
}// END loop()

void updateCommand (String command) {
  if (command == "stop") {
    play = false;
  } else if (command == "0" || command == "1" || command == "2" || command == "3") {
    carDirection = command;
    play = true;
  }
  /*if (play == true) {
    Serial.println("Robot is active");
  } else {
     Serial.println("Robot is inactive"); 
  }
  Serial.println("Direction: " + carDirection);*/
}
void robotTick() {
  enableMotors();
  if (play == true) {
    if (carDirection == "0") {
      forward(50);
    } else if (carDirection == "1") {
      left(50);
    } else if (carDirection == "2") {
      backward(50);
    } else if (carDirection == "3") {
      right(50);
    }
  }
  disableMotors();
}
// and also underneath all that lets make a very simple routine to send stuff over the bluetooth link:
void send(String s) {
  softSerial.println(s);
  Serial.println("> " + s);
}


//motor functions
void motorAforward() {
  digitalWrite (pinA1, HIGH);
  digitalWrite (pinA2, LOW);
}
void motorBforward() {
  digitalWrite (pinB1, LOW);
  digitalWrite (pinB2, HIGH);
}
void motorAbackward() {
  digitalWrite (pinA1, LOW);
  digitalWrite (pinA2, HIGH);
}
void motorBbackward() {
  digitalWrite (pinB1, HIGH);
  digitalWrite (pinB2, LOW);
}
void motorAstop() {
  digitalWrite (pinA1, HIGH);
  digitalWrite (pinA2, HIGH);
}
void motorBstop() {
  digitalWrite (pinB1, HIGH);
  digitalWrite (pinB2, HIGH);
}
void motorAcoast() {
  digitalWrite (pinA1, LOW);
  digitalWrite (pinA2, LOW);
}
void motorBcoast() {
  digitalWrite (pinB1, LOW);
  digitalWrite (pinB2, LOW);
}
void motorAon() {
  digitalWrite (enableA, HIGH);
}
void motorBon() {
  digitalWrite (enableB, HIGH);
}
void motorAoff() {
  digitalWrite (enableA, LOW);
}
void motorBoff() {
  digitalWrite (enableB, LOW);
}
// Movement functions
void forward (int duration) {
  motorAforward();
  motorBforward();
  delay (duration);
}
void backward (int duration) {
  motorAbackward();
  motorBbackward();
  delay (duration);
}
void left (int duration) {
  motorAbackward();
  motorBforward();
  delay (duration);
}
void right (int duration) {
  motorAforward();
  motorBbackward();
  delay (duration);
}
void coast (int duration) {
  motorAcoast();
  motorBcoast();
  delay (duration);
}
void breakRobot (int duration) {
  motorAstop();
  motorBstop();
  delay (duration);
}
void disableMotors() {
  motorAoff();
  motorBoff();
}
void enableMotors() {
  motorAon();
  motorBon();
}
