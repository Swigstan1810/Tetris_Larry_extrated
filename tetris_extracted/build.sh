#!/bin/bash

# Tetris Game Build Script

echo "Building Tetris Game..."

# Create build directories
mkdir -p build
mkdir -p server_build

# Copy GSON library files
echo "Copying GSON library..."
cp -r com build/
cp -r com server_build/

# Compile client game
echo "Compiling client game..."
find src -name "*.java" -not -path "src/server/*" | xargs javac -cp ".:com/google/gson/*" -d build

# Compile server
echo "Compiling server..."
find src/server -name "*.java" | xargs javac -cp ".:com/google/gson/*" -d server_build

# Copy resources
echo "Copying resources..."
if [ -d "resources" ]; then
    cp -r resources build/
fi

echo "Build complete!"
echo ""
echo "To run the game client:"
echo "  java -cp build Main"
echo ""
echo "To run the AI server:"
echo "  java -cp server_build:com/google/gson/* server.Main"