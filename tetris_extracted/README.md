# Tetris Game - Java Implementation

This is a complete Java implementation of the classic Tetris game, reconstructed from decompiled class files.

## Features

- Classic Tetris gameplay with all 7 standard tetrominoes (I, J, L, O, S, T, Z)
- Score tracking and high score system
- Sound effects and background music support
- Pause/resume functionality
- AI server for automated play
- Multiple game modes and configurations

## Project Structure

```
src/
├── Main.java                    # Main entry point for client
├── model/                       # Game logic and data models
│   ├── Game.java               # Core game engine
│   ├── GameConfig.java         # Game configuration
│   ├── TetrisShape.java        # Tetris piece definitions
│   ├── TetrisShapeInstance.java # Individual piece instances
│   ├── Score.java              # Score data structure
│   ├── ScoreList.java          # High score management
│   ├── MetaConfig.java         # Global settings
│   └── OpMove.java             # Move operation data
├── ui/                         # User interface components
│   ├── MainFrame.java          # Main application window
│   ├── SplashScreen.java       # Loading screen
│   └── panel/                  # UI panel components
│       ├── GamePanel.java      # Main game display
│       ├── MainPanel.java      # Main menu
│       ├── PlayPanel.java      # Game play screen
│       ├── PreviewPanel.java   # Next piece preview
│       ├── SideMessagePanel.java # Game info display
│       ├── TopMessagePanel.java # Title bar
│       ├── ConfigurePanel.java # Settings panel
│       └── HighScorePanel.java # High scores display
├── util/                       # Utility classes
│   ├── CommFun.java           # Common functions
│   └── SoundEffectManager.java # Audio management
└── server/                     # AI server components
    ├── Main.java              # Server entry point
    ├── PureGame.java          # Game state for AI
    ├── Shape.java             # Server-side shape handling
    ├── Simulation.java        # AI game simulation
    ├── Situation.java         # Game situation evaluation
    └── OpMove.java            # Move operation for server
```

## How to Build and Run

### Prerequisites
- Java 17 or higher
- GSON library for JSON serialization (included in extracted JARs)

### Building the Client Game

1. Compile the source files:
```bash
# From the tetris_extracted directory
javac -cp ".:com/google/gson/*" -d build src/**/*.java
```

2. Run the game:
```bash
java -cp "build:." Main
```

### Building the AI Server

1. Compile server files:
```bash
javac -cp ".:com/google/gson/*" -d server_build src/server/*.java
```

2. Run the server:
```bash
java -cp "server_build:com/google/gson/*" server.Main
```

## Game Controls

- **Arrow Keys**: Move piece left/right/down
- **Up Arrow or L**: Rotate piece clockwise  
- **Space**: Hard drop (instant drop)
- **P**: Pause/unpause game
- **M**: Toggle background music
- **S**: Toggle sound effects

## Game Features

### Tetris Pieces
All 7 standard Tetris pieces are implemented:
- **I-piece**: Cyan, 4-block line
- **J-piece**: Blue, L-shaped (mirrored)
- **L-piece**: Orange, L-shaped  
- **O-piece**: Yellow, 2x2 square
- **S-piece**: Green, S-shaped
- **T-piece**: Pink, T-shaped
- **Z-piece**: Red, Z-shaped

### Scoring System
- 1 line: 100 points
- 2 lines: 300 points  
- 3 lines: 600 points
- 4 lines: 1000 points

### Level Progression
- Level increases every 10 lines cleared
- Higher levels increase piece drop speed

## Technical Implementation

### Core Components

1. **Game Engine (model/Game.java)**
   - Manages game state, piece movement, line clearing
   - Handles collision detection and game rules
   - Observer pattern for UI updates

2. **Shape System (model/TetrisShape.java)**
   - Enum-based piece definitions with rotation logic
   - Random piece generation with bag system
   - Color and geometry management

3. **UI Framework (ui/ package)**  
   - Swing-based graphical interface
   - Card layout for screen transitions
   - Real-time game rendering with Graphics2D

4. **AI Server (server/ package)**
   - Socket-based communication
   - Heuristic-based move evaluation
   - Game state simulation

### Key Design Patterns
- **Observer Pattern**: Game state updates to UI components
- **Enum Pattern**: Tetris piece definitions
- **Singleton Pattern**: Configuration and score management
- **MVC Pattern**: Separation of game logic, UI, and data

## Audio Support

The game supports audio through the util/SoundEffectManager class:
- Background music
- Sound effects for piece movement, line clears, level up
- Audio files should be placed in `resources/audios/`

## Extending the Game

The modular design allows for easy extensions:

1. **New Game Modes**: Extend GameConfig with new modes
2. **AI Improvements**: Enhance server/Simulation.java evaluation
3. **UI Themes**: Modify panel classes for different visual styles
4. **Network Play**: Extend server for multiplayer support

## License

This implementation is reconstructed from compiled Java bytecode for educational purposes.