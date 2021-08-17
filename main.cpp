//
//  main.cpp
//  TicTacToe
//
//  Created by Perry Zhu  on 2021/7/29.
//
//  Created for 2021 College Prep Program of UC Berkeley final project.
//

#include <iostream>
#include <string>
using namespace std;

//  function prototypes.
void printGrid ();
void initializeSelected ();
void user1Input ();
void user2Input ();
bool isFinished ();
bool deterHorizontal ();
bool deterVertical ();
bool deterDiagonal ();
bool deterAllFilled ();
bool isThreeSame (char, char, char);
void congraPlayer1 ();
void congraPlayer2 ();

//  global variable declarations.

//  represents whether a position is selected.
bool selected[9];

//  represents characters on each position.
char cellValues[9] = {'1', '2', '3','4', '5', '6', '7', '8', '9'};

//  represents the turn. 0 for player 1 and 1 for player 2.
bool playerTurn = 0;

//  global variables used to determine whether the game is finish.
bool isHoriComplete;
bool isVertComplete;
bool isDiagComplete;
bool isAllFilled;


int main () {
    
//    initializes the grid and print it out.
    initializeSelected();
    printGrid();
    
//    begin of the game.
    while (true) {
        user1Input();
        printGrid();
        if (isFinished()) {
            break;
        }
        user2Input();
        printGrid();
        if (isFinished()) {
            break;
        }
    }
    
//    sends congratulation announcements if there is a winner.
    if (isHoriComplete || isVertComplete || isDiagComplete) {
        if (playerTurn == 0) {
            congraPlayer1();
        }
        else if (playerTurn == 1) {
            congraPlayer2();
        }
    }
    
//    circumstance that all positions are filled and no winner exists.
    else {
        cout << "All positions are filled. No one wins. " << endl;
    }
    
    return 0;
}

//  prints out the current stage of the grid.
void printGrid () {
    cout << "     |     |     " << endl
    << "  " << cellValues[0] << "  |  " << cellValues[1] << "  |  " << cellValues[2] << "  " << endl
    << "_____|_____|_____" << endl
    << "     |     |     " << endl
    << "  " << cellValues[3] << "  |  " << cellValues[4] << "  |  " << cellValues[5] << "  " << endl
    << "_____|_____|_____" << endl
    << "     |     |     " << endl
    << "  " << cellValues[6] << "  |  " << cellValues[7] << "  |  " << cellValues[8] << "  " << endl
    << "     |     |     " << endl;
}

//  initializes all values in the array to false.
void initializeSelected () {
    for (int i = 0; i < 9; i ++) {
        selected[i] = false;
    }
}


//  asks user one to input and modify the cellValues array.
void user1Input () {
    int choose;
    int choosePosition;
    playerTurn = 0;
    while (true) {
        cout << "Player 1 input: ";
        cin >> choose;
        choosePosition = choose - 1;
        
        if (selected[choosePosition] == false) {
            cellValues[choosePosition] = 'X';
            selected[choosePosition] = true;
            break;
        }
        else {
            cout << "The position has been used. Please choose another one. \n";
        }
    }
}

//  asks user two to input and modify the cellValues array.
void user2Input () {
    int choose;
    int choosePosition;
    playerTurn = 1;
    while (true) {
        cout << "Player 2 input: ";
        cin >> choose;
        choosePosition = choose - 1;
        
//        ask the player to choose another position if this has been chosen.
        if (selected[choosePosition] == false) {
            cellValues[choosePosition] = 'O';
            selected[choosePosition] = true;
            break;
        }
        else {
            cout << "The position has been used. Please choose another one. \n";
        }
    }
}

//  return true if the game is finished.
bool isFinished () {
    isHoriComplete = deterHorizontal();
    isVertComplete = deterVertical();
    isDiagComplete = deterDiagonal();
    isAllFilled = deterAllFilled();
    if (isHoriComplete || isVertComplete || isDiagComplete || isAllFilled) {
        return true;
    }
    else {
        return false;
    }
}

//  return true if there are three consecutive marks on one row.
bool deterHorizontal () {
    int index1 = 0;
    int index2 = 1;
    int index3 = 2;
    
    for (int i = 0; i < 3; i ++, index1 += 3, index2 += 3, index3 += 3) {
        if (isThreeSame(cellValues[index1], cellValues[index2], cellValues[index3])) {
            return true;
        }
    }
    
    return false;
}

//  return true if there are three consecutive marks on one column.
bool deterVertical () {
    int index1 = 0;
    int index2 = 3;
    int index3 = 6;
    
    for (int i = 0; i < 3; i ++, index1 += 1, index2 += 1, index3 += 1) {
        if (isThreeSame(cellValues[index1], cellValues[index2], cellValues[index3])) {
            return true;
        }
    }
    
    return false;
}

//  return true if there are three consecutive marks on one diagonal.
bool deterDiagonal () {
    int index1 = 0;
    int index2 = 4;
    int index3 = 8;
    
    if (isThreeSame(cellValues[index1], cellValues[index2], cellValues[index3])) {
        return true;
    }
    
    index1 = 2;
    index2 = 4;
    index3 = 6;
    
    if (isThreeSame(cellValues[index1], cellValues[index2], cellValues[index3])) {
        return true;
    }
    
    return false;
}

//  returns true if all positions are filled.
bool deterAllFilled () {
    for (int i = 0; i < 9; i ++) {
        if (selected[i] == false) {
            return false;
        }
    }
    return true;
}

//  returns true if three parameter characters are the same.
bool isThreeSame (char c1, char c2, char c3) {
    if (c1 == c2) {
        if (c1 == c3) {
            return true;
        }
        else {
            return false;
        }
    }
    else {
        return false;
    }
}

//  announces winning information if player 1 wins.
void congraPlayer1 () {
    string winningMethod;
    if (isHoriComplete) {
        winningMethod = "horizontally";
    }
    else if (isVertComplete) {
        winningMethod = "vertically";
    }
    else if (isDiagComplete) {
        winningMethod = "on the diagonal";
    }
    cout << "Congratulations!" << endl << "Player 1 wins the game. " << endl << "The player wins by placing three consecutive marks " << winningMethod << " . " << endl;
}

//  announces winning information if player 2 wins.
void congraPlayer2 () {
    string winningMethod;
    if (isHoriComplete) {
        winningMethod = "horizontally";
    }
    else if (isVertComplete) {
        winningMethod = "vertically";
    }
    else if (isDiagComplete) {
        winningMethod = "on the diagonal";
    }
    cout << "Congratulations!" << endl << "Player 2 wins the game. " << endl << "The player wins by placing three consecutive marks " << winningMethod << " . " << endl;
}
