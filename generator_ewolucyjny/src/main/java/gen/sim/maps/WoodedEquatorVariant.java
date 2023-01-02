package gen.sim.maps;

import gen.sim.Grass;
import gen.sim.PositionMechanics;
import gen.sim.Vector2d;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class WoodedEquatorVariant implements IGrowthVariant{
    int thickness;
    int minY;   // Y od którego zaczynamy nasz równik (włącznie)
    MapCore map;

    List<Vector2d> preferred = new ArrayList<>();
    List<Vector2d> unpreferred = new ArrayList<>();     // do przemyślenia
    public WoodedEquatorVariant(MapCore map) {    // wiemy, że w tym wypadku height % 5 = 0
        this.map = map;
        thickness = map.config.height/5;
        if (thickness%2==0) {
            minY = (map.config.height/2) - (thickness/2);
        } else {
            minY = (int) (Math.floor((double) map.config.height/2) - thickness/2);
        }
        addFields();
    }

    private void addFields() {
        for (int y=0; y<minY; y++) {
            for (int x = 0; x < map.config.width; x++){
                Vector2d prefPos = new Vector2d(x,y);
                unpreferred.add(prefPos);
            }
        }
        for (int y = minY; y < minY+thickness; y++){
            for (int x = 0; x < map.config.width; x++){
                Vector2d prefPos = new Vector2d(x,y);
                preferred.add(prefPos);
            }
        }
        for (int y=minY+thickness; y<map.config.height; y++) {
            for (int x = 0; x < map.config.width; x++){
                Vector2d prefPos = new Vector2d(x,y);
                unpreferred.add(prefPos);
            }
        }
    }

    @Override
    public void addGrass(int n) {
        int inPreferred = (int) (n * 0.8);
        Random rand = new Random();
        // Jungle grass
        for (int i=0; i<inPreferred; i++){
            if (preferred.isEmpty()) {
                break;
            } else {
                Vector2d pos = preferred.get(rand.nextInt(preferred.size()));
                if (freeFromGrass(pos)) {    // to be sure
                    map.elements.get(pos).grass = new Grass(map.config.grassEnergy, map.grassObserver, pos, this);
                    preferred.remove(pos);
                } else {
                    System.out.println("Wrong grass preferred position????");
                }
            }
        }
        // Other grass
        for (int i = inPreferred; i<n; i++) {
            if (unpreferred.isEmpty()) {
                break;
            } else {
                Vector2d pos = unpreferred.get(rand.nextInt(unpreferred.size()));
                if (freeFromGrass(pos)) {    // to be sure
                    map.elements.get(pos).grass = new Grass(map.config.grassEnergy, map.grassObserver, pos, this);
                    unpreferred.remove(pos);
                } else {
                    System.out.println("Wrong grass unpreferred position????");
                }
            }
        }
    }

    private boolean freeFromGrass(Vector2d pos) {
        PositionMechanics positionMechanics = map.elements.get(pos);
        if (positionMechanics == null) {
            PositionMechanics element = new PositionMechanics(map);
            map.elements.put(pos, element);
            return true;
        } else return positionMechanics.grass == null;
    }

    @Override
    public List<Vector2d> getPreferredGrassPositions() {
        List<Vector2d> list = new LinkedList<>();
        for (int y = minY; y < minY+thickness; y++){
            for (int x = 0; x < map.config.width; x++){
                list.add(new Vector2d(x,y));
            }
        }
        return list;
    }

    @Override
    public void freePosition(Vector2d pos) {
        if (pos.y >= minY && pos.y < minY+thickness) {
            preferred.add(pos);
        } else {
            unpreferred.add(pos);
        }
    }
}
