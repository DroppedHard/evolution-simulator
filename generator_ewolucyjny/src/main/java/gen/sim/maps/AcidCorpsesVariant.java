package gen.sim.maps;

import gen.sim.Grass;
import gen.sim.PositionMechanics;
import gen.sim.Vector2d;

import java.util.*;

public class AcidCorpsesVariant implements IGrowthVariant{
    List<Vector2d> preferred = new ArrayList<>();
    List<Vector2d> preferredLeft = new ArrayList<>();
    List<Vector2d> unpreferred = new ArrayList<>();
    MapCore map;
    int lastDeadIdx = 0;
    int preferredNum;
    Random rand = new Random();
    SortedSet<DeadPosition> cementary = new TreeSet<>(Comparator.comparingInt(a -> a.bodies));
    public AcidCorpsesVariant(MapCore map){
        this.map = map;

        addPreferred();
    }

    public void addPreferred() {
        for (int x=0; x<map.config.width; x++){
            for (int y=0; y<map.config.height; y++){
                unpreferred.add(new Vector2d(x, y));
            }
        }
        preferredNum = (int) Math.round(map.config.height * map.config.width*0.2);
        for (int i=0; i<preferredNum; i++){
            Vector2d pos = unpreferred.get(rand.nextInt(unpreferred.size()));
            preferred.add(pos);
            preferredLeft.add(pos);
        }
    }

    @Override
    public void addGrass(int n) {
        if (map.animalObserver.deadAnimals.size() > lastDeadIdx) {
            updateInfo();
        }
        int inPreferred = (int) (n * 0.8);
        // Jungle grass
        for (int i=0; i<inPreferred; i++){
            if (preferredLeft.isEmpty()) {
                break;
            } else {
                Vector2d pos = preferredLeft.get(rand.nextInt(preferredLeft.size()));
                if (freeFromGrass(pos)) {    // to be sure
                    map.elements.get(pos).grass = new Grass(map.config.grassEnergy, map.grassObserver, pos, this);
                }
                preferredLeft.remove(pos);
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
                }
                unpreferred.remove(pos);
            }
        }
    }

    private void updateInfo() {
        while (map.animalObserver.deadAnimals.size() > lastDeadIdx){
            Vector2d pos = map.animalObserver.deadAnimals.get(lastDeadIdx).position;
            DeadPosition info = getDeadPos(pos);
            if (info == null) {
                info = new DeadPosition();
                info.pos = pos;
                info.bodies = 1;
            } else {
                cementary.remove(info);
                info.bodies++;
            }
            cementary.add(info);
            lastDeadIdx++;
        }
        updatePref();
//        while (!deadPosQ.isEmpty()) {
//            Vector2d pos = deadPosQ.poll();
//            if (preferred.contains(pos)) {
//                preferred.remove(pos);
//
//            }
//        }
    }

    private void updatePref() {
        for (DeadPosition next : cementary) {
            preferred.remove(next.pos);
        }
        Iterator<DeadPosition> iter = cementary.iterator();
        for (int i=0; i<preferredNum-preferred.size(); i++){
            preferred.add(iter.next().pos);
            if (!preferredLeft.contains(iter.next().pos)){
                preferredLeft.add(iter.next().pos);
            }
        }
    }

    private DeadPosition getDeadPos(Vector2d pos) {
        for (DeadPosition next : cementary) {
            if (next.pos == pos) {
                return next;
            }
        }
        return null;
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
        return preferred;
    }

    @Override
    public void freePosition(Vector2d pos) {
        if (preferred.contains(pos)) {
            preferredLeft.add(pos);
        } else {
            unpreferred.add(pos);
        }
    }
}

class DeadPosition {
    Vector2d pos;
    int bodies = 0;
}
