#include <sstream>
#include "Game.h"
#include "field/Field.h"
#include "./utils/TextureLoader.h"
#include "gfx/ParticleSystem.h"

Field* field;
SDL_Event Game::event;


bool wasPaused = false;
int pauseClickCounter = 0;
SDL_Texture * pauseTexture;
SDL_Texture * pauseBlackScreen;
SDL_Rect pauseRect;
SDL_Rect pauseBlackScreenRect;
int pauseBlackScreenOpacity = 0;

ParticleSystem * particleSys;

void Game::init(const char *title, int xPos, int yPos, int width, int height, bool isFullScreen){
    const int flags = 0;

    if(SDL_Init(SDL_INIT_EVERYTHING) == 0){
        std::cout << "SDL Initialized..." << std::endl;

        if(TTF_Init() == 0){std::cout << "TTF Fonts library initialized..." << std::endl; }  //Initializing TTF font library

        SDL_SetHint( SDL_HINT_RENDER_SCALE_QUALITY, "1"); //set "antialiasing" on -> smoother edges

        window = SDL_CreateWindow(title, xPos, yPos, width, height, flags);

        if(window){
            std::cout << "Window initialized..." << std::endl;
        }

        renderer = SDL_CreateRenderer(window, -1, 0); //-1 -> index, 0 -> renderer flags
        if(renderer){
            SDL_SetRenderDrawColor(renderer, 0, 0, 0, 255); //setting drawing color to RGB(255,255,255)
            std::cout << "Renderer initialized..." << std::endl;
        }
        isRunning = true;
    }

    particleSys = new ParticleSystem(renderer);

    field = new Field(renderer);

    pauseTexture = TextureLoader::loadTexture("assets/pause.png", renderer);
    pauseRect.w = 128;
    pauseRect.h = 128;
    pauseRect.x = 800 / 2 - pauseRect.w / 2;
    pauseRect.y = 600 / 2 - pauseRect.h / 2;

    pauseBlackScreen = TextureLoader::loadTexture("assets/black_bcg.png", renderer);
    pauseBlackScreenRect.x = 0;
    pauseBlackScreenRect.y = 0;
    pauseBlackScreenRect.w = width;
    pauseBlackScreenRect.h = height;

}

void Game::handleEvents(){

    SDL_PollEvent(&event);
    switch(event.type){
        case SDL_QUIT:
            isRunning = false; break;

        default: break;
    }
}

void Game::update(){
    handleInput();

    if(!isPaused){
        field -> update();
    }else{
        particleSys->update();
        randomizeParticlesOnPause();
    }
}

void Game::render(){
    SDL_RenderClear(renderer);

    field -> render();

    if(isPaused){
        particleSys->render();
        SDL_SetTextureAlphaMod(pauseBlackScreen, pauseBlackScreenOpacity);
        SDL_RenderCopy(renderer, pauseBlackScreen, nullptr, &pauseBlackScreenRect);
        SDL_RenderCopy(renderer, pauseTexture, nullptr, &pauseRect);
    }

    if(!isPaused && !particleSys->getParticles().empty()){
        particleSys->getParticles().clear();
    }


    SDL_RenderPresent(renderer);
}

void Game::clean(){
    SDL_DestroyWindow(window);
    SDL_DestroyRenderer(renderer);
    TTF_Quit();
    SDL_Quit();
    delete field;
    std::cout << "Game cleaned" << std::endl;
}

void Game::handleInput() {
    const Uint8* key_state = SDL_GetKeyboardState(nullptr);

    if(!wasPaused){
        if(key_state[SDL_SCANCODE_ESCAPE]){
            isPaused = !isPaused;
            wasPaused = true;
            pauseClickCounter = 0;
            pauseBlackScreenOpacity = 0;
        }
    }

    pauseClickCounter++;
    pauseBlackScreenOpacity += 4;

    if(pauseClickCounter > 10){
        pauseClickCounter = 0;
        wasPaused = false;
    }

    if(pauseBlackScreenOpacity > 160){
        pauseBlackScreenOpacity = 160;
    }
}

int lastParticleX;
int lastParticleY;

void Game::randomizeParticlesOnPause() {
    srand(time(nullptr));

    int count = rand() % 15 + 8;
    int x = rand() % 800;
    int y = rand() % 600;

    if(x != lastParticleX || y != lastParticleY){
        lastParticleX = x;
        lastParticleY = y;
        particleSys->generate(count, x, y, 0, true);
    }
}



