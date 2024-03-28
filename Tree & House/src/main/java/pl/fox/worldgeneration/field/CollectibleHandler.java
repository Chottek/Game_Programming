package pl.fox.worldgeneration.field;

import pl.fox.worldgeneration.Handler;
import pl.fox.worldgeneration.field.objects.Collectible;
import pl.fox.worldgeneration.field.objects.Rock;
import pl.fox.worldgeneration.field.objects.Wood;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CollectibleHandler {

    private static final int MAX_COLLECTIBLES_AROUND = 20;
    private static final double MAX_COLLECTIVE_LIFE = 100;
    private static final double COLLECTIVE_VISIBLE_DEGRADATION_RATE = 0.05D;
    private static final double COLLECTIVE_OUT_OF_SIGHT_DEGRADATION_RATE = 0.07D;
    private static final int OUT_OF_SCREEN_GENERATION_DISTANCE = 50;

    private final Handler handler;

    public CollectibleHandler(Handler handler) {
        this.handler = handler;
    }

    //TODO: Add weights / chances to specific collectible types
    //      Implement feature that specific places can have specific collectibles
    //      Calculate maximum distance from player that allows the collectible to be destroyed
    //      Make sure that collectibles are generated off-screen
    //      Make collectibles disappear over time

    public void update() {
      //  generate();
        applyDegradation();
       // remove();
    }

    public void generate() {
       // System.out.println(collectibles.size() + " / " + MAX_COLLECTIBLES_AROUND);

        if (handler.getEntityManager().getCollectibleEntities().size() >= MAX_COLLECTIBLES_AROUND) {
            return;
        }

        var boundPart = new Random().nextInt(4);
        var boundRect = getOutOfScreenGenerationBounds().get(boundPart);
        var x = new Random().nextFloat(boundRect.width) - boundRect.x;
        var y = new Random().nextFloat(boundRect.height) - boundRect.y;

        //TODO: Regenerate if coordinates overlap with existing ones

        var type = new Random().nextInt(2);
        var quantity = new Random().nextInt(4) + 1; //Cannot be 0!
        Collectible collectible;
        switch (type) {
            case 0 -> collectible = new Wood(handler, x, y, quantity, MAX_COLLECTIVE_LIFE);
            case 1 -> collectible = new Rock(handler, x, y, quantity, MAX_COLLECTIVE_LIFE);
            default -> collectible = new Wood(handler, x, y, quantity, MAX_COLLECTIVE_LIFE);
        }

        System.out.println("Generated [" + collectible + "] at x=" + x + ", y=" + y + ", boundPart: " + boundPart);
        handler.getEntityManager().getCollectibleEntities().add(collectible);
    }

    private void applyDegradation() {
        for (var collectible : handler.getEntityManager().getCollectibleEntities()) {
            double degradedLifeValue;
            if (canPlayerSee(handler.getPlayer(), collectible)) {
                degradedLifeValue = collectible.getLife() - COLLECTIVE_VISIBLE_DEGRADATION_RATE;
            } else {
                degradedLifeValue = collectible.getLife() - COLLECTIVE_OUT_OF_SIGHT_DEGRADATION_RATE;
            }
            collectible.setLife(degradedLifeValue);
        }
    }

//    public void remove() {
//        var expiredCollectibles = .keySet().stream()
//                .filter(c -> !canPlayerSee(handler.getPlayer(), c)) //Check if collectible is out of player sight
//                .filter(c -> collectibles.get(c) <= 0) //Check if collectible has expired
//                .toList();
//
//        expiredCollectibles.forEach(collectibles::remove);
//    }

    private boolean isOutOfScreenWithinPlaygroundBounds(Collectible c) {
        return getOutOfScreenGenerationBounds().stream().anyMatch(outOfScreenBound -> c.getBounds().intersects(outOfScreenBound));
    }

    private boolean canPlayerSee(Player p, Collectible c) {
        return p.getSightBounds().intersects(c.getBounds());
    }

    private java.util.List<Rectangle> getOutOfScreenGenerationBounds() {
        var playerSightBounds = handler.getPlayer().getSightBounds();

        var offsetPlayerBoundsX = playerSightBounds.x - handler.getOffsetX() / 2;
        var offsetPlayerBoundsY = playerSightBounds.y - handler.getOffsetY() / 4 * 3;

        System.out.println("OffsetBoundsX: " + offsetPlayerBoundsX + ", OffsetBoundsY: " + offsetPlayerBoundsY);

        return java.util.List.of(
                new Rectangle((int) (offsetPlayerBoundsX - OUT_OF_SCREEN_GENERATION_DISTANCE), (int) offsetPlayerBoundsY, OUT_OF_SCREEN_GENERATION_DISTANCE, playerSightBounds.height),
                new Rectangle((int) (offsetPlayerBoundsX + playerSightBounds.width), (int) offsetPlayerBoundsY, OUT_OF_SCREEN_GENERATION_DISTANCE, playerSightBounds.height),
                new Rectangle((int) (offsetPlayerBoundsX - OUT_OF_SCREEN_GENERATION_DISTANCE), (int) (offsetPlayerBoundsY - OUT_OF_SCREEN_GENERATION_DISTANCE), 2 * OUT_OF_SCREEN_GENERATION_DISTANCE + playerSightBounds.width , OUT_OF_SCREEN_GENERATION_DISTANCE),
                new Rectangle((int) (offsetPlayerBoundsX - OUT_OF_SCREEN_GENERATION_DISTANCE), handler.getGameHeight(), 2 * OUT_OF_SCREEN_GENERATION_DISTANCE + playerSightBounds.width, OUT_OF_SCREEN_GENERATION_DISTANCE)
        );
    }

    public void checkCollectiblesGather(Rectangle playerBounds) {
        var gathered = handler.getEntityManager().getCollectibleEntities().stream()
                .filter(collectible -> canPlayerSee(handler.getPlayer(), collectible))
                .filter(collectible -> collectible.getBounds().intersects(playerBounds))
                .toList();

        gathered.forEach(c -> {
                handler.getPlayer().handleCollectibleGather(c);
                handler.getEntityManager().removeEntity(c);
        });
    }

}
