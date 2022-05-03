//
// Created by Blake's Here on 2022/5/1.
//

#include "SnakeApp.h"
#include <cinder/gl/draw.h>
#include <iostream>
#include <cinder/gl/gl.h>
using cinder::Rectf;
using cinder::Color;
using cinder::ColorA;
using namespace cinder;
using namespace std;
int i = 0;

void SnakeApp::draw() {
    cinder::gl::enableAlphaBlending();
    cinder::gl::clear();

   /* const int w = getWindowWidth();
    const int h = getWindowHeight();*/
    /*gl::color ( Color (1, 1, 0) );
    gl::lineWidth(5);
    gl::drawLine(vec2(0, 0), vec2(w, h) );*/
    DrawSnake();
}

void SnakeApp::DrawSnake() {
    const int w = width_;
    const int h = height_;
    gl::color ( Color (1, 1, 0) );
    for (vector<int> segment : gameConsole_->the_first_snake_->body_positions_) {
        cinder::gl::drawSolidRect(Rectf(segment[0], segment[1], segment[2], segment[3]));
    }
}

void SnakeApp::setup() {
    cinder::gl::enableDepthWrite();
    cinder::gl::enableDepthRead();
    gameConsole_ = new GameConsole(getWindowWidth(),  getWindowHeight(), 1);
    width_ = getWindowWidth();
    height_ = getWindowHeight();
}

void SnakeApp::update() {
    gameConsole_->moveNextStep("DOWN");
}
