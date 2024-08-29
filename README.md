# Wordle Solver

This is a Java-based Wordle Solver that attempts to guess the correct word in a Wordle game within six tries. The solver uses a predefined list of words and user feedback to narrow down the possible correct words until it finds the target.

Uses the sgb-word list.

## Features

- **Interactive Feedback**: The solver prompts the user to input the status of each guessed letter (green for correct position, yellow for wrong position, gray for not in the word).
- **Efficient Word Filtering**: Based on the feedback, the solver removes invalid words from its list and picks the next best guess.
- **Random Guessing**: After filtering, a word is randomly chosen from the remaining valid options.

### Prerequisites

- Ensure you have [Java](https://www.java.com/en/download/) installed on your system.
- Ensure you have a list of valid words stored in a file named `sgb-words.txt`. This file should be placed in the root directory of your project.

