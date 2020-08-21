# My Personal Project

## Scrabble! 

### What am I making?
#### What will the application do?
The application will allow at least one user to play a game of Scrabble, according to the official rules: 
https://www.scrabblepages.com/scrabble/rules/. The number of users, as well as the game specifications, will be based 
off of the official rules. 

#### Who will use it?
Anyone who would like to play a game of Scrabble for fun will be able to use it! This project is done for entertainment 
purposes.
- *Me!* 
- My friends!
- People who fit within the Scrabble demographic (anyone!)

#### Why is this project of interest to me?
I wanted to make a game for this personal project. I've always loved words: learning word etymologies, learning new 
words, and holistically, writing and the English language in general. I used to play Scrabble a lot when I was younger
and competed in Spelling Bee competitions. </p> 
I want to be able to work on an application that doesn't require me to create a whole new set of rules (i.e., not get 
bogged down with creating an entirely new game) but will nonetheless allow me to branch my hobbies/interests (words!) 
in some shape or form. In essence, I love words, I love games, and Scrabble is a game about words! 

### User Stories
- **As a user**, I want to be able to start a new game of Scrabble.
- **As a user**, I want my new game of Scrabble to start with 7 random letters.
- **As a user**, I want to be able to view what letters I have in my inventory. 
- **As a user**, I want to be able to select letters from my letter bank and be able to place them down on the game 
board.
- **As a user**, I want to be able to save the status of my game: like the number of players, letters allocated, and letters placed. 
- **As a user**, I want to be able to access saved games from file when the program begins. 

### Instructors for Graders
- **Start the game from playing "App"!**
- **You can generate the first required event by** clicking a letter and adding it to the board. You must directly click the letter and then click where on the board you would like to place it. Note: it may be a bit tricky to select letters and place them -- be careful to select them directly by clicking on them! However, if you successfully selected a letter, you'll be validated by a ding!
- **You can generate the second required event by** clicking the Next Player button at the bottom of the right corner when a new game has begun. This switches to another letter hand allocated to the number of players that have been decided upon in the start menu. 
- **You can trigger my audio component by** starting a game (choosing the number of needed characters) and then clicking on something in your letter hand. It makes a 'ding' sound to indicate that you've successfully selected a letter.
- **You can save the state of my application by** clicking the save button at the bottom-right of the screen. This saves it to the *scrabble.json* file.
- **You can reload the state of my application by** selecting the 'Load saved game' in the home screen, and choosing the scrabble.json file from wherever it was saved to. I would suggest saving it to Desktop for the purposes of testing this application, to make it easier for you! 

#### Phase 4: Task 2
I chose to implement the first option: testing and designing a class that is robust.
The classes that were affected in this change were the GameBoard class (found in the model package) and the subsequent NumPlayerException that was created for robustness. 
All constructors of the GameBoard were affected by this -- a try-catch had to be written to account for this change. 

#### Phase 4: Task 3
**Problem 1** -- improved cohesion by moving some methods from the GameBoard class that referred to player functionality to another class: PlayerActions.
The GameBoard represents the implementation of the game: the specifications for the board. Having methods that referred to the player functionality in this class was poor cohesion. 
Classes affected by this change: GameBoard and PlayerActions. Methods affected by this change: placeLetter() and clearLetters(), which were originally in the GameBoard class.

**Problem 2** -- improved cohesion by removing instances of the LetterBank from the GameBoard class. There was no point in having a LetterBank in this class as it had nothing to do with the GameBoard specifications.
The only reason it was there in the first place was to have a getter method. However, having an instance of the LetterBank is sufficient for distributing letters.
Classes affected by this change: GameBoard, Board (ui). Methods affected by this change: getLetterBank() -- this was removed; constructors for GameBoard and Board; nextPlayer() save() in Board were changed to refer to this instance.