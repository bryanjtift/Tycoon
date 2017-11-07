package me.HeyAwesomePeople.Tycoon.mongodb;

public enum Collection {

    USERDATA("userdata"),
    TYCOON("tycoondata"),
    PLOTDATA("plotdata");

    private String name;

    Collection(String name) {
        this.name = name;
    }

    public String getCollName() {
        return this.name;
    }

}
