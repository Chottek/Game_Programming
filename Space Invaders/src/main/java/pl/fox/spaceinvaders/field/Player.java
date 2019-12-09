package pl.fox.spaceinvaders.field;

import pl.fox.spaceinvaders.Launcher;
import pl.fox.spaceinvaders.graphics.EnemyDeath;
import pl.fox.spaceinvaders.graphics.PlayerDeath;
import pl.fox.spaceinvaders.graphics.PlayerMissileFuel;
import pl.fox.spaceinvaders.input.KeyManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player {

    private float x;
    private float y;

    private final float SPEED = 4.0f;

    public static final int boundsX = 50;

    private boolean isDead;
    private boolean hasWon;

    private boolean isReloading;
    private int reloadTimer, reloadCooldown;

    private int life = 3;

    private int level;
    private int score;

    private int addOn;
    private int addOnCounter = 0;

    private ArrayList<Integer> shotX;
    private ArrayList<Integer> shotY;

    public Player() {
        shotX = new ArrayList<>();
        shotY = new ArrayList<>();
        init();
    }


    public void init() {
        addOnCounter = 0;
        hasWon = false;
        addOn = -1;
        score = 0;
        life = 3;
        level = 1;
        isDead = false;
        isReloading = false;
        x = (Launcher.width - boundsX) / 2;
        y = Launcher.width - 50;

        reloadCooldown = 40;

    }

    private void getInput() {
        if (KeyManager.left || KeyManager.arrLeft)
            x -= SPEED;

        if (KeyManager.right || KeyManager.arrRight)
            x += SPEED;

        if (!isReloading && (KeyManager.keyJustPressed(KeyEvent.VK_SPACE) || KeyManager.keyJustPressed(KeyEvent.VK_UP))) {
            shotX.add((int) x + (boundsX / 2));
            shotY.add((int) y);
            isReloading = true;
            reloadTimer = 0;
        }
    }

    private void checkReloadingTimer() {
        if (isReloading) {
            reloadTimer++;
            if (reloadTimer >= reloadCooldown) {
                reloadTimer = 40;
                isReloading = false;
            }
        }
    }

    private void moveShot() {
        for (int i = 0; i < shotX.size(); i++){
            shotY.set(i, shotY.get(i) - 10);
            PlayerMissileFuel.add((shotX.get(i) + 2), shotY.get(i) + 3, 7);

            if(shotY.get(i) < 0){
                shotX.remove(i);
                shotY.remove(i);
            }

        }

    }

    private void checkDeath() {
        if (life <= 0) {
            isDead = true;
            EnemyDeath.removeAll();
            PlayerDeath.removeAll();
            shotX.clear();
            shotY.clear();
        }
    }

    private void useAddOn() {
        if (addOn == 0 && life < 4) {
            life++;
            addOn = -1;
        } else if (addOn == 1) {
            if (KeyManager.keyJustPressed(KeyEvent.VK_SPACE) || (KeyManager.keyJustPressed(KeyEvent.VK_UP)))
                addOnCounter++;

            if (addOnCounter == 2) {
                addOn = -1;
                addOnCounter = 0;
            }
        } else if (addOn == 2) {
            if (KeyManager.keyJustPressed(KeyEvent.VK_SPACE) || (KeyManager.keyJustPressed(KeyEvent.VK_UP))) {
                addOnCounter++;
                reloadCooldown = 40;
                isReloading = false;
            }

            if (addOnCounter == 8) {
                addOn = -1;
                addOnCounter = 0;
            }
        }
    }

    private void checkFieldBounds() {
        if (x <= 0)
            x = 0;
        else if (x + boundsX >= Launcher.width)
            x = Launcher.width - boundsX;
    }

    public void update() {
        getInput();
        checkFieldBounds();
        moveShot();
        checkReloadingTimer();
        checkDeath();
        useAddOn();
    }

    public void render(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 14));

        g.setColor(Color.GREEN);
        g.drawString("SCORE: " + score, 10, 15);

        for (int i = 0; i < life; i++)
            g.drawString("[_]", Launcher.width - (20 * i) - 20, 15);

        g.setColor(Color.YELLOW);
        for (int i = 0; i < shotX.size(); i++)
            g.fillRect(shotX.get(i), shotY.get(i), 5, 10);


        g.setColor(Color.BLUE);
        g.fillRect((int) x, (int) y, 50, 15);

        if (addOn == 1 && addOnCounter < 2)
            g.drawString("PENETRATING MISSILE", 10, Launcher.height - 20);
        if (addOn == 2)
            g.drawString("QUICKSHOTER", 10, Launcher.height - 20);
    }

    public Rectangle getShotCollisionBounds(int index){
        return new Rectangle(shotX.get(index), shotY.get(index), 5, 10);
    }

    public Rectangle getCollisionBounds(){
        return new Rectangle((int) x, (int) y, 50, 15);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isHasWon() {
        return hasWon;
    }

    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void subLife(){
        this.life--;
    }

    public void addLife(){
        this.life++;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAddOn() {
        return addOn;
    }

    public void setAddOn(int addOn) {
        this.addOn = addOn;
    }

    public ArrayList<Integer> getShotX() {
        return shotX;
    }

    public void setShotX(ArrayList<Integer> shotX) {
        this.shotX = shotX;
    }

    public ArrayList<Integer> getShotY() {
        return shotY;
    }

    public void setShotY(ArrayList<Integer> shotY) {
        this.shotY = shotY;
    }
}
