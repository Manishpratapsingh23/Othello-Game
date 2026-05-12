# 🟢 Othello (Reversi) Game — Java

A fully functional **two-player Othello (Reversi) game** developed in **Java**, featuring complete game logic, move validation, scoring, and support for both **console-based** and **GUI-based** gameplay.  
The project follows **Object-Oriented Programming (OOP)** principles and demonstrates effective use of **data structures** and **game state management**.

---

## 🎮 Features

- ✔ Complete implementation of official Othello (Reversi) rules
- ✔ Turn-based two-player gameplay (White vs Black)
- ✔ Valid move detection in all 8 directions
- ✔ Automatic flipping of opponent pieces
- ✔ Real-time score calculation
- ✔ Win, loss, and draw detection
- ✔ Turn skipping when no valid moves are available
- ✔ Restart game functionality (GUI version)
- ✔ Highlighting / blinking of valid moves (GUI)

---
<img width="1920" height="1034" alt="image" src="https://github.com/user-attachments/assets/39266f66-fe06-42b8-9bbb-68b05d336a3b" />
<img width="1920" height="1028" alt="image" src="https://github.com/user-attachments/assets/32ab8bb5-96e1-47db-805b-ad10002fd56d" />
<img width="1920" height="1028" alt="image" src="https://github.com/user-attachments/assets/e5be83b4-1c92-4e2c-918f-d222998825d5" />
<img width="1920" height="1030" alt="image" src="https://github.com/user-attachments/assets/5d6fb5db-f0e8-4cde-9ce3-8b83673d1a41" />


## 🖥 Game Modes

### 1️⃣ Console-Based Version

The console version allows two players to play Othello using terminal input. Players enter board coordinates for each move, with input validation and board state display after every turn.

**Main Class:**  
`Othello.java`

---

### 2️⃣ GUI-Based Version (Java Swing)

The GUI version provides an interactive graphical board built using Java Swing. Players make moves via mouse clicks, with visual feedback through images and blinking indicators for valid moves.

**Main Class:**  
`OthelloGUIself.java`

---

## 🛠 Tech Stack

### Technologies
- **Language:** Java
- **GUI:** Java Swing

### Concepts Used
- Object-Oriented Programming (OOP)
- 2D Arrays
- Lists & Data Structures
- Event Handling
- Game State Management

---

## 📁 Project Structure

java programmes<br>
 └── game<br>
     └── othello<br>
         ├── Board.java<br>
         ├── Othello.java<br>
         ├── OthelloGUIself.java<br>
         ├── Player.java<br>
         └── image<br>
             ├── WhiteImage.png<br>
             ├── BlackImage.png<br>
             └── BlinkImage.png<br>

---

## ▶ How to Run

### 🔹 Compile
```bash
javac game/othello/*.java
java game.othello.Othello
java game.othello.OthelloGUIself



