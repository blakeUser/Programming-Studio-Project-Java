//
// Created by Blake's Here on 2022/4/12.
//

#ifndef UNTITLED_SNAKE_H
#define UNTITLED_SNAKE_H
#include <iostream>
#include <vector>
class Snake {
public:
    Snake(int length);
    std::pair<int, int> GetCurrentPosition();
    int length_;
    std::vector<std::pair<int, int>> body_positions_;
    std::pair<int, int> current_position_;
};
#endif //UNTITLED_SNAKE_H