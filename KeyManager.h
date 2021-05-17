#pragma once

#include "Game.h"


class KeyManager: public Component{

public:
    
    void update() override{
    	if(Game::event.type == SDL_KEYDOWN){
    		switch(Game::event.key.keysym.sym){
    			case SDLK_LEFT:
    				
    		
    		}
    	}
    	
    	if(Game::event.type == SDL_KEYUP){
    	
    	
    	}
    	
    	
    
    }

}

