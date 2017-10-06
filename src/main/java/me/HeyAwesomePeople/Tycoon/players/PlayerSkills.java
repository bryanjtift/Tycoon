package me.HeyAwesomePeople.Tycoon.players;

import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author HeyAwesomePeople
 * @since Thursday, September 21 2017
 */
public class PlayerSkills {

    @Getter private TycoonPlayer tycoonPlayer;
    @Getter private HashMap<Skill, Integer> skills = new HashMap<>();

    public PlayerSkills(TycoonPlayer tycoonPlayer) {
        this.tycoonPlayer = tycoonPlayer;
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

    public void updateSkills() {

    }

    public enum Skill {
        SCIENCE,
        TECH,
        ENGINEER,
        MEDICAL;
    }

}