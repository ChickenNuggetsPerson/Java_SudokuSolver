## Java Sudoku Solver

This is a java project that can solve Sudoku puzles. You can enter the puzzle manually with code or by importing a csv file with the board information.

The puzzle solver uses a recursive system for finding the correct answer to the puzzle. 




## Stretch Goals

- Puzzle Maker (Have a seed input so the results are repeatable)
- Keep note of multiple solutions
    - Command line arg that says, "solve one" or "solve all"
    - For solve all, instead of stopping once the board is finished... save the board to an array and return false. 
    - By doing this, the board will keep traveling down the the branches and find other boards.