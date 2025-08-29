#!/bin/bash

# Quick run script for Tetris Game

cd "$(dirname "$0")"

echo "Starting Tetris Game..."

# Build if build directory doesn't exist
if [ ! -d "build" ]; then
    echo "Build directory not found. Building game..."
    ./build.sh
fi

# Run the game
echo "Launching game..."
java -cp "build:com/google/gson/*" Main