g++ `pkg-config --cflags sdl2` main.cpp Game.cpp field/Field.cpp field/Bullet.cpp field/Enemy.cpp field/Player.cpp field/Camera.cpp utils/MathUtils.cpp utils/TextureLoader.cpp utils/FontUtils.cpp `pkg-config --libs sdl2` -lSDL2_image -lSDL2_ttf -std=c++14

