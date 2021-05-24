g++ `pkg-config --cflags sdl2` main.cpp Game.cpp field/GameObj.cpp utils/TextureLoader.cpp field/Bullet.cpp field/Player.cpp utils/MathUtils.cpp utils/FontUtils.cpp GameHandler.cpp `pkg-config --libs sdl2` -lSDL2_image -lSDL2_ttf -std=c++14

