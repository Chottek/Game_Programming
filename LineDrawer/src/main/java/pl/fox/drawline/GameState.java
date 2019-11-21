package pl.fox.drawline;

import pl.fox.drawline.field.PlayerOne;
import pl.fox.drawline.field.PlayerTwo;
import pl.fox.drawline.graphics.DeathCircle;
import pl.fox.drawline.input.KeyManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameState {

    protected Handler handler;
    private PlayerOne playerOne;
    private PlayerTwo playerTwo;
    private DeathCircle deathCircle;
    private boolean isStarted, pOneReady, pTwoReady, isNextRound;
    private int startCountdown;
    private int winState;

    public GameState(Handler handler) {
        this.handler = handler;
        playerOne = new PlayerOne(handler, 100, 20);
        playerTwo = new PlayerTwo(handler, Launcher.width - 100, 20);
        deathCircle = new DeathCircle();

        isStarted = false;
        isNextRound = false;
        startCountdown = 5;
        winState = 0;
    }

    private void checkWinner() {
        if (!isNextRound && handler.getPlayerOne().isDead() && handler.getPlayerTwo().isDead() && handler.getPlayerOne().getModules_X().size() == 0 && handler.getPlayerTwo().getModules_X().size() == 0) {
            handler.getPlayerOne().setX(0);
            handler.getPlayerTwo().setX(Launcher.width);

            if (handler.getPlayerOne().getScore() > handler.getPlayerTwo().getScore()) {
                winState = 1;
                handler.getPlayerOne().setPoints(handler.getPlayerOne().getPoints() + 1);
            } else if (handler.getPlayerTwo().getScore() > handler.getPlayerOne().getScore()) {
                winState = 2;
                handler.getPlayerTwo().setPoints(handler.getPlayerTwo().getPoints() + 1);
            } else
                winState = 0;

            isNextRound = true;
            isStarted = false;
        }
    }

    private void checkStart() {
        if (pOneReady && pTwoReady && handler.getGame().getTicks() % 60 == 0)
            startCountdown--;
        if (startCountdown == 0)
            isStarted = true;

        if (isStarted)
            return;
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_Q))
            pOneReady = true;
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SHIFT))
            pTwoReady = true;
    }

    private void checkNextRound(){
        if (isNextRound) {
            if (KeyManager.space){
                playerOne.reinit();
                playerTwo.reinit();
                isNextRound = false;
                isStarted = false;
                winState = 0;
                startCountdown = 3;
                return;
            }
        }
    }

    public void update() {
        deathCircle.update();
        checkNextRound();

        if (!isStarted) {
            checkStart();
            return;
        }

        playerOne.update();
        playerTwo.update();
        checkWinner();
    }

    public void render(Graphics g) {
        deathCircle.render(g);

        g.setFont(new Font("Arial", Font.BOLD, 40));

        g.drawString(handler.getPlayerOne().getPoints() + " | " + handler.getPlayerTwo().getPoints(), (Launcher.width - 80) / 2, 50);

        if (!isStarted && handler.getGame().getTicks() / 20 % 2 == 0) {
            if (!pOneReady) {
                g.setColor(Color.RED);
                g.drawString("Q to get ready", 350, 100);
            }
            if (!pTwoReady) {
                g.setColor(Color.GREEN);
                g.drawString("SHIFT to get ready", 350, 200);
            }
        }

        if (pOneReady && pTwoReady && !isStarted) {
            g.setColor(Color.YELLOW);
            g.drawString(startCountdown + "", Launcher.width / 2, Launcher.height / 2);
        }

        if (handler.getPlayerOne().isDead() && handler.getPlayerTwo().isDead() && handler.getPlayerOne().getModules_X().size() == 0 && handler.getPlayerTwo().getModules_X().size() == 0) {
            String s = "";
            switch (winState) {
                case 0:
                    s = "TIE!";
                    g.setColor(Color.YELLOW);
                    break;
                case 1:
                    s = "Player ONE won!";
                    g.setColor(Color.RED);
                    break;
                case 2:
                    s = "Player TWO won!";
                    g.setColor(Color.GREEN);
                    break;
            }
            g.drawString(s, (Launcher.width - 300) / 2, Launcher.height / 2);

            g.drawString("Press space to continue", (Launcher.width - 400) / 2, Launcher.height - 200);
        }

        g.setFont(new Font("Arial", Font.ITALIC, 14));

        playerOne.render(g);
        playerTwo.render(g);
    }

    public PlayerOne getPlayerOne() {
        return playerOne;
    }

    public PlayerTwo getPlayerTwo() {
        return playerTwo;
    }

    public DeathCircle getDeathCircle() {
        return deathCircle;
    }

}
