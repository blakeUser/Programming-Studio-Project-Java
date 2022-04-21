//
// Created by Blake's Here on 2022/4/12.
//
#include "game_console.h"
#include "snake.h"
GameConsole::GameConsole(int width, int height, int length) {
    width_ = width;
    height_ = height;
    SetRandomFoodLocation();
    the_first_snake_ = new Snake(length);
}

void GameConsole::SetRandomFoodLocation() {
    int random_width = rand() / width_;
    int random_height = rand() / height_;
    std::pair<int, int> tmp(random_width, random_height);
    while (std::count(the_first_snake_->body_positions_.begin(),
                      the_first_snake_->body_positions_.end(), tmp)) { //while this position can be found, re-locate
        tmp = std::make_pair(rand() / width_, rand() / height_);
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
    if (current_food_location_ == the_first_snake_->GetCurrentPosition()) { //if hit the food
        std::pair<int, int> last_postion = the_first_snake_->body_positions_[the_first_snake_->length_];
        last_postion.first--;
        the_first_snake_->body_positions_.push_back(last_postion);
        the_first_snake_->length_++;
        for (int i = 0; i < the_first_snake_->length_; i++) {
            if (i == 0) {
                continue;
            }
        }
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