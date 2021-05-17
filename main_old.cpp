#include <SDL2/SDL.h>
#include <chrono>
#include <cstdint>
#include <iostream>
#include <memory>
#include <set>
#include <stdexcept>
#include <string>
#include <thread>
#include <tuple>
#include <vector>

//g++ `pkg-config --cflags sdl2` main.cpp `pkg-config --libs sdl2` -std=c++14

// check for errors
#define errcheck(e)                                                            \
  {                                                                            \
    if (e) {                                                                   \
      cout << SDL_GetError() << endl;                                          \
      SDL_Quit();                                                              \
      return -1;                                                               \
    }                                                                          \
  }

int main(int , char **) {
  using namespace std;
  using namespace std::chrono;
  const int width = 800;
  const int height = 600;

  errcheck(SDL_Init(SDL_INIT_VIDEO) != 0);

  SDL_Window *window = SDL_CreateWindow(
      "SpaceShooter", SDL_WINDOWPOS_UNDEFINED,
      SDL_WINDOWPOS_UNDEFINED, width, height, SDL_WINDOW_SHOWN);
  errcheck(window == nullptr);

  SDL_Renderer *renderer = SDL_CreateRenderer(
      window, -1, SDL_RENDERER_ACCELERATED); // SDL_RENDERER_PRESENTVSYNC
  errcheck(renderer == nullptr);

  //auto dt = 15ms;
  milliseconds dt(15);

  steady_clock::time_point current_time = steady_clock::now(); // remember current time
  for (bool game_active = true; game_active;) {
    SDL_Event event;
    while (SDL_PollEvent(&event)) { // check if there are some events
      if (event.type == SDL_QUIT)
        game_active = false;
    }

    SDL_RenderPresent(renderer); // draw frame to screen

    this_thread::sleep_until(current_time = current_time + dt);
  }
  SDL_DestroyRenderer(renderer);
  SDL_DestroyWindow(window);
  SDL_Quit();
  return 0;
}
