package habit.tracker.habittracker.adapter.habitsuggestion;

public class Customer {

    private int id;
    private String name = "";

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}