//
// Created by Blake's Here on 2022/4/12.
//

#ifndef UNTITLED_GAME_CONSOLE_H
#define UNTITLED_GAME_CONSOLE_H
#include "snake.h"

#include <iostream>
#include <vector>

class GameConsole {
    public:
    GameConsole(int width, int height, int length);
    int moveNextStep(std::string direction);
    void EndGame();
    void CheckHitTheWall();
    void SetRandomFoodLocation();

    private:

    std::pair<int, int> current_food_location_;

    std::string moving_status_; //If not doing anything, what is the snake currently doing?

    std::vector<std::pair<int, int>> body_positions_;

    Snake * the_first_snake_;

    int game_status = 0;

    int width_;

    int height_;
};


#endif //UNTITLED_GAME_CONSOLE_H
