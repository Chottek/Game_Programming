class Player {

public: 
   float x, float y;
   float speed;
   boolean isDead;
   int score;
   double angle;
   Player(){

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

    x += speed * Math.cos(angle);
    y += speed * Math.sin(angle);
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