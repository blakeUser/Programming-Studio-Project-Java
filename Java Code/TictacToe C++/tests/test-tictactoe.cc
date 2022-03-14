#include <string>

#include "catch.hpp"

#include <tictactoe/tictactoe.h>

using tictactoe::Board;
using tictactoe::BoardState;

TEST_CASE("Invalid string provided to constructor") {
  SECTION("String is too short") {
    REQUIRE_THROWS_AS(Board("xxooo"), std::invalid_argument);
  }

  SECTION("placeholder") {

  }
}

TEST_CASE("Boards with no winner") {
  SECTION("Full board with no winner") {
    REQUIRE(Board("xxoooxxxo").EvaluateBoard() == BoardState::NoWinner);
  }

  SECTION("placeholder") {
  }
}
TEST_CASE("Empty_InvalidInput_Board") {
	REQUIRE(CheckTicTacToeBoard("") == InvalidInput);
}



