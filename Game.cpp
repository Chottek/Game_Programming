#include <sstream>
#include "Game.h"
#include "field/Field.h"
#include "./utils/TextureLoader.h"
#include "./utils/FontUtils.h"
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


bool startShadingDirection = true;
SDL_Texture * bcgTexture;
SDL_Texture * startBlackScreen;
SDL_Rect startBlackScreenRect;
int startBlackScreenOpacity = 10;

SDL_Texture * logo;
SDL_Rect logoBounds;
int logoOpacity = 255;

SDL_Texture* gameOverLogo;
SDL_Rect gameOverLogoBounds;
int gameOverLogoOpacity = 0;

ParticleSystem * particleSys;

TTF_Font* alienStart;

bool isFieldInited = false;

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

    bcgTexture = TextureLoader::loadTexture("assets/bcg.jpeg", renderer);

    logo = TextureLoader::loadTexture("assets/logo.png", renderer);
    logoBounds.w = 490;
    logoBounds.h = 50;
    logoBounds.x = width / 2 - (logoBounds.w / 2);
    logoBounds.y = height / 2 - logoBounds.h * 2;

    gameOverLogo = TextureLoader::loadTexture("assets/gameover.png", renderer);
    gameOverLogoBounds.w = 490;
    gameOverLogoBounds.h = 50;
    gameOverLogoBounds.x = width / 2 - (gameOverLogoBounds.w / 2);
    gameOverLogoBounds.y = height / 2 - gameOverLogoBounds.h * 2;

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

    startBlackScreen = TextureLoader::loadTexture("assets/black_bcg.png", renderer);
    startBlackScreenRect.x = 0;
    startBlackScreenRect.y = 0;
    startBlackScreenRect.w = width;
    startBlackScreenRect.h = height;

    alienStart = FontUtils::loadFont("assets/CAlien.ttf", 22);
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

    if(!hasStarted || startBlackScreenOpacity <= 255){
        particleSys -> update();
    }

    if(!field->getPlayerAlive() && gameOverLogoOpacity < 255){
        gameOverLogoOpacity++;
    }

    if(!hasStarted){
        randomizeParticlesOnPause();
    }

    if(startBlackScreenOpacity > 0){
        return;
    }

    if(!isFieldInited){
        isFieldInited = field->init();
    }

    if(hasStarted){
        if(!isPaused && startBlackScreenOpacity <= 0){
            field -> update();
        }else{
            particleSys->update();
            randomizeParticlesOnPause();
        }
    }
}

void Game::render(){
    SDL_RenderClear(renderer);

    field -> render();

    if(!field->getPlayerAlive()){
        SDL_SetTextureAlphaMod(gameOverLogo, gameOverLogoOpacity);
        SDL_RenderCopy(renderer, gameOverLogo, nullptr, &gameOverLogoBounds);
        blinkSpaceStart(false);
    }

    if(startBlackScreenOpacity > 0){
        SDL_RenderCopy(renderer, bcgTexture, nullptr, &pauseBlackScreenRect);
        SDL_SetTextureAlphaMod(startBlackScreen, startBlackScreenOpacity);
        SDL_RenderCopy(renderer, startBlackScreen, nullptr, &startBlackScreenRect);
        particleSys -> render();
        SDL_SetTextureAlphaMod(logo, logoOpacity);
        SDL_RenderCopy(renderer, logo, nullptr, &logoBounds);
        if(!hasStarted){
            blinkSpaceStart(true);
        }
    }else{
        if(isPaused){
            particleSys->render();
            SDL_SetTextureAlphaMod(pauseBlackScreen, pauseBlackScreenOpacity);
            SDL_RenderCopy(renderer, pauseBlackScreen, nullptr, &pauseBlackScreenRect);
            SDL_RenderCopy(renderer, pauseTexture, nullptr, &pauseRect);
        }

        if(!isPaused && !particleSys->getParticles().empty()){
            particleSys->getParticles().clear();
        }
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

    if(!hasStarted){
        if(key_state[SDL_SCANCODE_SPACE]){
            hasStarted = true;
            startBlackScreenOpacity = 10;
            startShadingDirection = true;
            for(auto p: particleSys->getParticles()){
                p->setLife(-1);
            }
        }
        if(key_state[SDL_SCANCODE_ESCAPE]){
            clean();
            exit(0);
        }
    }

    if(hasStarted && logoOpacity >= 5){
        logoOpacity -= 2;
    }

    if(hasStarted && startBlackScreenOpacity > 0 && !startShadingDirection){
        startBlackScreenOpacity -= 4;
    }

    if(hasStarted && startBlackScreenOpacity < 255 && startShadingDirection){
        startBlackScreenOpacity += 4;
    }

    if(hasStarted && startBlackScreenOpacity >= 255){
        startShadingDirection = false;
    }

    if(!wasPaused){
        if(field->getPlayerAlive()){
            if(key_state[SDL_SCANCODE_ESCAPE]){
                isPaused = !isPaused;
                wasPaused = true;
                pauseClickCounter = 0;
                pauseBlackScreenOpacity = 0;
            }
        }else{
            if(key_state[SDL_SCANCODE_SPACE]){
                //retry <- reinit everything here
                field->init();
            }
            if(key_state[SDL_SCANCODE_ESCAPE]){
                clean();
                exit(0);
            }
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

int startVisibility = 245;
bool direction = false;

void Game::blinkSpaceStart(bool startOrRetry) {
    const char* txt = startOrRetry ? "Press SPACE to Start" : "Press SPACE to Retry";
    FontUtils::drawString(alienStart, renderer, {255, 255, 0, (Uint8) startVisibility}, txt, 240, 400);
    if(!direction && startVisibility >= 10){
        startVisibility -= 7;
    }

    if(direction && startVisibility <= 245){
        startVisibility += 7;
    }

    if(startVisibility <= 10 && !direction){
        startVisibility = 10;
        direction = true;
    }

    if(startVisibility >= 245 && direction){
        startVisibility = 245;
        direction = false;
    }
}


