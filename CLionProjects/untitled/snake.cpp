//
// Created by Blake's Here on 2022/4/12.
//
#include "snake.h"
#include <iostream>
Snake::Snake(int length) {
    length_ = length;
    current_position_ = std::pair<int, int>(0, 0);
}

std::pair<int, int> Snake::GetCurrentPosition() {return current_position_; }
