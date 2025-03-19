# 🎮 Tile-Matching Game Environment (TMGE)

## 📌 Overview
This project is an **extensible Tile-Matching Game Environment (TMGE)** designed to accommodate multiple tile-matching games such as **2048, Minesweeper, Tetris, Bejeweled, Candy Crush**, and more.  

The TMGE provides a **common framework** to make it easy to implement and extend new games while ensuring **modularity, scalability, and reusability**.

## 🚀 Features
✅ **Object-Oriented Design (OOP) using Java**  
✅ **Extensible framework** for creating new tile-matching games  
✅ **Pre-implemented games:**  
   - 🧩 **2048**  
   - 💣 **Minesweeper**  
✅ **Player Profiles** for tracking scores  
✅ **Turn-based multiplayer (2 players)**  
--
## 🛠️ Installation & Setup
download or clone the github repository

### **🔹 Clone the Repository**
https://github.com/SushiApril/INF122.git

Run jar file: TMGE_Implemenation.jar

🔧 Extensibility & Design
🔹 TMGE Core
At the heart of this project is the TMGE framework, which provides:

Abstract classes (GameGrid, GameTile) to define game mechanics.
A game launcher that allows players to select and play different games.
🔹 How to Add a New Game
To create a new game in TMGE, follow these steps:

Create a new package in TMGE.YourGameName
Extend GameGrid<T extends GameTile> to create a grid-based game board.
Extend GameTile to define individual tiles used in the game.
