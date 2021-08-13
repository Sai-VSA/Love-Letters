# My Personal Project

## Love Letters: Card Game For Two

The aim of this project proposal is to put forth the idea of bringing *Love Letters* to a virtual environment,
albiet with only two players.

**What will this virtual Love Letters do?** <br />
  This version of love letters will aim to emulate playing its real life counterpart, consisiting of:
- 2 Players
- A Rule book
- 8 unique types of cards with differing abilities
- A deck of 16 cards

**Who will use it?**<br />
  Virtual Love Letters removes the burdensome physical elements from the cardgame, making its fun
  more portable and conveniant. Thus, it mainly targets students and people interested in recreational activities. 

**Why choose this project?**<br />
  Well, my school's Board Game Club was an increadibly memorable and important part of my adolescence, and it
  helped me nurture a passion for board games. I remember Love Letters to be one of the first games introduced 
  to the club and argubaly most popular, so I wanted to try to reintrodce it in a different form, as a way to spread
  the joy and share my experience. 

  Either way, I hope ***you***, the reader, enjoy this game and have as much fun playing as I did designing it! 



## User Stories: Love Letters Edition

- As a user, I want to start and end the game.
- As a user, I want to start and end my turn. 
- As a user, I want to be able to look at the rules and list of cards. 
- As a user, I want to be able to draw cards from the deck. 
- As a user, I want to see and play cards from my hand. 
- As a user, I want to activate and respond to card effects.
- As a user, I want to see the discard pile.  
- As a user, I want to be able to add cards from hand to the discard pile.
- As a user, I want to see the amount of cards left in deck.  
- As a user, I want to be able save game state to the file.
- As a user, I want to be able to continue the game again from file.

## Phase 4: Task 2
- Implemented bi-directional relationship between Player and Deck. 
- Player uses its drawCard method to draw a card from Deck's deck and add it to playerHand.
- Deck uses its drawCard method to draw a card from its deck and add it to a playerHand based on playerTurn. 
- Player's drawCard is used for cardEffects like Hero to draw for a player regardless of playerTurn or drewCard. 
- Deck's drawCard is used to draw a card at the beginning of turn, which also flips drewCard.

## Phase 4: Task 3
- If I had more time, I would have made both playerHand and discardPile their own classes.
- I feel that currently, the Deck and Player have too many responsibilie, leading to low cohesion. 
- Furthermore, I would transfer features such as gameState or boardState from UI to a different class called gameBoard. 
- This way, I can test the interactions between the players, deck and cards without having to manually test UI. 
- It would also make it easier for UI to focus completely on user interaction, while gameBoard could focus on inteeraction between classes


