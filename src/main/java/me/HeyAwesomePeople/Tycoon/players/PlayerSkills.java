package me.HeyAwesomePeople.Tycoon.players;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.HeyAwesomePeople.Tycoon.datamanaging.Statistics;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author HeyAwesomePeople
 * @since Thursday, September 21 2017
 */
public class PlayerSkills {

    @Getter private TycoonPlayer tycoonPlayer;
    @Getter private HashMap<Skill, Integer> skills = new HashMap<>();

    private Statistics stats;

    public PlayerSkills(TycoonPlayer tycoonPlayer) {
        this.tycoonPlayer = tycoonPlayer;
        this.stats = tycoonPlayer.getDataManager().getStats();

        for (Skill s : Skill.values()) {
            if (stats.hasKey(s.getSkillString())) {
                skills.put(s, stats.getInt(s.getSkillString()));
            } else {
                skills.put(s, 0);
            }
        }
    }

    public void addSkillValue(Skill skill, Integer value) {
        this.skills.put(skill, this.skills.get(skill) + value);
    }

    public void setSkillValue(Skill skill, Integer value) {
        this.skills.put(skill, value);
    }

    public int getSkill(Skill skill) {
        return skills.get(skill);
    }

    public void saveSkillsData() {
        for (Skill s : Skill.values()) {
            stats.setInt(s.getSkillString(), skills.getOrDefault(s, 0));
        }
    }

    @RequiredArgsConstructor public enum Skill {
        SCIENCE("scienceskill"),
        TECH("techskill"),
        ENGINEER("engineerskill"),
        MEDICAL("medicalskill");

        @Getter private final String skillString;
    }

}