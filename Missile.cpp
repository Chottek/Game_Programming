class Missile {

public:
     float x, float y;
     float speed;
     double angle;
     Missile(float x, float y, double angle){
	x = x;
        y = y;
	angle = angle;
     }
}

void Missile::move(){
   x += speed * cos(angle);
   y += speed * sin(angle); 
}

void Missile::update(){

}

void Missile::render(){

}