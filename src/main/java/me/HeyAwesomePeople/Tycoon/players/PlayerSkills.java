package me.HeyAwesomePeople.Tycoon.players;

import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author HeyAwesomePeople
 * @since Thursday, September 21 2017
 */
public class PlayerSkills {

    @Getter private UUID playerID;
    @Getter private HashMap<Skill, Integer> skills = new HashMap<Skill, Integer>();

    public PlayerSkills(UUID playerID) {
        this.playerID = playerID;
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

    public enum Skill {
        SCIENCE,
        TECH,
        ENGINEER,
        MEDICAL;
    }

}