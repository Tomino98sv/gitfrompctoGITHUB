package persons;

public class Card {
    private int id;
    private String pin;
    private boolean active;
    private int expireM;
    private int expireY;
    private int ida;

    public Card(int id, String pin, boolean active, int expireM, int expireY, int ida) {
        this.id = id;
        this.pin = pin;
        this.active = active;
        this.expireM = expireM;
        this.expireY = expireY;
        this.ida = ida;
    }

    public int getId() {
        return id;
    }

    public String getPin() {
        return pin;
    }

    public boolean isActive() {
        return active;
    }

    public int getExpireM() {
        return expireM;
    }

    public int getExpireY() {
        return expireY;
    }

    public int getIda() {
        return ida;
    }
}
