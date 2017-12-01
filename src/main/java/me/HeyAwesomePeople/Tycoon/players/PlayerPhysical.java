package me.HeyAwesomePeople.Tycoon.players;

import lombok.Getter;
import lombok.Setter;
import me.HeyAwesomePeople.Tycoon.datamanaging.Statistics;

import java.util.UUID;

/**
 * @author HeyAwesomePeople
 * @since Thursday, September 21 2017
 */
public class PlayerPhysical {

    @Getter private TycoonPlayer tycoonPlayer;

    @Getter @Setter private Integer stamina;
    @Getter @Setter private Integer fitness;

    private Statistics stats;

    public PlayerPhysical(TycoonPlayer tycoonPlayer) {
        this.tycoonPlayer = tycoonPlayer;

        stats = tycoonPlayer.getDataManager().getStats();

        loadData();
    }

    public void addStamina(int value) {
        this.stamina += value;
    }

    public void addFitness(int value) {
        this.fitness += value;
    }

    private void loadData() {
        if (stats.hasKey("stamina")) {
            this.stamina = stats.getInt("stamina");
        } else {
            this.stamina = 0;
        }
        if (stats.hasKey("fitness")) {
            this.fitness = stats.getInt("fitness");
        } else {
            this.fitness = 0;
        }
    }

    public void savePhysicalData() {
        stats.setInt("stamina", stamina);
        stats.setInt("fitness", fitness);
    }

}
