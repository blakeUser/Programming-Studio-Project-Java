//
// Created by Blake's Here on 2022/4/12.
//
#include "game_console.h"
#include "snake.h"

GameConsole::GameConsole(int width, int height, int length) {
    width_ = width;
    height_ = height;
    the_first_snake_ = new Snake(length);
}

void GameConsole::SetRandomFoodLocation() {
    int random_width = rand() / width_;
    int random_height = rand() / height_;
    std::pair<int, int> tmp(random_width, random_height);
    while (std::count(body_positions_.begin(), body_positions_.end(), tmp)) { //while this position can be found, re-locate
        random_width = rand() / width_;
        random_height = rand() / height_;
        tmp = std::make_pair(random_width, random_height);
    }
    current_food_location_ = tmp;
}

int GameConsole::moveNextStep(std::string direction) {
    if (direction == "UP") {
        moving_status_ = direction;
        int second = the_first_snake_->GetCurrentPosition().second;
        second++;
        the_first_snake_->current_position_ = (std::make_pair(the_first_snake_->GetCurrentPosition().first, second));
    } else if (direction == "DOWN") {
        moving_status_ = direction;
        int second = the_first_snake_->GetCurrentPosition().second;
        second--;
        the_first_snake_->current_position_ = (std::make_pair(the_first_snake_->GetCurrentPosition().first, second));
    } else if (direction == "LEFT") {
        moving_status_ = direction;
        int first = the_first_snake_->GetCurrentPosition().first;
        first--;
        the_first_snake_->current_position_ = (std::make_pair(first, the_first_snake_->GetCurrentPosition().second));
    } else {
        moving_status_ = direction;
        int first = the_first_snake_->GetCurrentPosition().first;
        first++;
        the_first_snake_->current_position_ = (std::make_pair(first, the_first_snake_->GetCurrentPosition().second));
    }

    CheckHitTheWall();
    if (current_food_location_ == the_first_snake_->GetCurrentPosition()) {
        the_first_snake_->length_++;
        SetRandomFoodLocation();
    }
    //Implement eat the food and get longer.
}

void GameConsole::EndGame() {
    game_status = 1;
}

void GameConsole::CheckHitTheWall() {
    if (the_first_snake_->GetCurrentPosition().first == width_ || the_first_snake_->GetCurrentPosition().first < 0 ||
        the_first_snake_->GetCurrentPosition().second == height_ || the_first_snake_->GetCurrentPosition().second < 0) {
        EndGame();
    }
}