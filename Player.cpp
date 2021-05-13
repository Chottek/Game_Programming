class Player {

public: 
   float x, float y;
   float speed;
   boolean isDead;
   int score;
   double angle;
   Player(){
      init();
   }


}

void Player::init(){
   angle = 0.1D;
   score = 0;
   isDead = false;
   speed = 4.0F;
}

void Player::move(){
    if(isDead){
 	return;     
    }

    x += speed * cos(angle);
    y += speed * sin(angle);
}

void Player::turn(){
   angle += 0.15D;
}


void Player::update(){
     Player::turn();
     Player::move();

}

void Player::render(){


}