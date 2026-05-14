# PotionMixie ✨
## 2110215 Programming Methodology Final Project AY2025 S2

PotionMixie is a JavaFX potion-mixing game created for the 2110215 Programming Methodology final project. The player combines cute magical elements into potions, then uses the correct potion type to defeat souls before the timer runs out.

## Features

- JavaFX screen flow: main menu, level selection, how-to-play, credits, and gameplay.
- Five gameplay levels with different time limits and required defeated soul counts.
- Four mergeable elements:
  - Heart Berry
  - Star Dust
  - Spark Ember
  - Dew Drop
- Six potion and soul types:
  - Dream Mist
  - Energy Splash
  - Nova Spark
  - Passion Pop
  - Soothing Love
  - Starlove Charm
- Drag-and-drop potion delivery to target souls.
- Automated tests for constructors, game logic, potion recipes, soul damage, GUI setup, timer behavior, and resource availability.
- Javadoc comments for main classes, constructors, methods, and tests.

## Tech Stack

- Java
- JavaFX 24.0.1
- Gradle
- JUnit 5
- TestFX

## Project Structure

```text
src/main/java
├── application      # JavaFX application entry point
├── gameelement
│   ├── element      # Merge ingredients
│   ├── potion       # Potion models and recipes
│   └── soul         # Soul models and damage behavior
├── gamelogic        # Timer, soul generation, merging, and attack logic
└── gui              # JavaFX screens

src/main/resources   # Images and UML resources
src/test/java        # Unit tests and JavaFX screen tests
```

## Requirements

- JDK installed and available from the terminal.
- Gradle wrapper files included in this project.

The project uses JavaFX 24.0.1, so use a JDK version compatible with that JavaFX release.

## How to Run

From the project root:

```bash
./gradlew run
```

On Windows:

```bash
gradlew.bat run
```

## How to Play

1. Open the game and choose a level.
2. Click two element buttons to create a potion.
3. Drag the created potion to a soul on the belt.
4. A soul only takes damage from the potion type that matches its weakness.
5. Defeat the required number of souls before time reaches zero.

## Potion Recipes

| Potion | Ingredients |
| --- | --- |
| Dream Mist | Star Dust + Dew Drop |
| Energy Splash | Spark Ember + Dew Drop |
| Nova Spark | Star Dust + Spark Ember |
| Passion Pop | Heart Berry + Spark Ember |
| Soothing Love | Heart Berry + Dew Drop |
| Starlove Charm | Heart Berry + Star Dust |

Ingredient order does not matter.

## Level Rules

| Level | Required Souls | Time Limit |
| --- | ---: | ---: |
| 1 | 5 | 90 seconds |
| 2 | 10 | 180 seconds |
| 3 | 20 | 175 seconds |
| 4 | 30 | 300 seconds |
| 5 | 67 | 450 seconds |

## Testing

Run all tests:

```bash
./gradlew test
```

The test suite covers:

- Element constructor metadata.
- Potion constructor metadata and recipe matching.
- Soul constructor metadata, HP ranges, and potion damage behavior.
- GameLogic merging, attacking, level completion, timer, and belt copy behavior.
- JavaFX screen construction.
- Resource availability.

## Generate Javadoc

```bash
./gradlew javadoc
```

Generated documentation is written to:

```text
build/docs/javadoc/index.html
```

## Build JAR

```bash
./gradlew jar
```

The configured JAR task includes compiled classes and source files.

