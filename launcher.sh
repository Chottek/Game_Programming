g++ `pkg-config --cflags sdl2` main.cpp Game.cpp GameObj.cpp TextureLoader.cpp Bullet.cpp Player.cpp MathUtils.cpp `pkg-config --libs sdl2` -lSDL2_image -std=c++14

