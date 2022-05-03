//
// Created by Blake's Here on 2022/5/1.
//
#ifndef UNTITLED_SNAKEAPP_H
#define UNTITLED_SNAKEAPP_H
#include <cinder/app/App.h>
#include <cinder/gl/gl.h>
#include "../snake.h"
#include "../game_console.h"

class SnakeApp : public cinder::app::App {

    public:
    //SnakeApp(int width, int height, int length);
    void setup() override;
    void update() override;
    void draw() override;
    void DrawSnake();

private:
    GameConsole * gameConsole_;
    int width_;
    int height_;

};


#endif //UNTITLED_SNAKEAPP_H
