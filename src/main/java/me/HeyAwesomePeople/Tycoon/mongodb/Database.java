package me.HeyAwesomePeople.Tycoon.mongodb;

public enum Database {

    MONGO_DATABASE("tycoon");

    private String name;

    Database(String name) {
        this.name = name;
    }

    public String getDBName() {
        return this.name;
    }

}
