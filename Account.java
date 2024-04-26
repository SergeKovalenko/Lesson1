package Lesson1;
import lombok.ToString;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
@ToString
class   Account {
    private String name;
    private Map<Currency, Integer> currenc = new HashMap<>();
    private Deque<Command> saves = new ArrayDeque<>();

    public Account(String name) {
        setName(name);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        String tmp = Account.this.name;
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Некорректное значение для имени");
        saves.push(()->Account.this.name=tmp);
        this.name = name;
    }

    public Map<Currency, Integer> getCurrenc() {
        return new HashMap<>(this.currenc);
    }

    public void setCurrenc(Map<Currency, Integer> currenc) {
        currenc = currenc;
    }

    public void addCurrenc(Currency currency, int amount) {
        if (currency == null) throw new IllegalArgumentException("Валюта не может быть пустой");
        if (amount < 0) throw new IllegalArgumentException("Количество валюты должно быть положительным числом");
        Map<Currency, Integer> tmp=new HashMap<>();
        tmp.clear();
        tmp.putAll(Account.this.currenc);
        saves.push(()->Account.this.currenc=tmp);
        currenc.put(currency, amount);
    }

    public Saveable save() {
        return new Snapshot();
    }

    public void undo() {
        if (saves.isEmpty()) throw new RuntimeException("Стек пустой. Нечего откатывать");
        saves.pop().make();
    }

    public Saveable load() {
        return new Snapshot();
    }

    private class Snapshot implements Saveable {
        private String name;
        private final Map<Currency, Integer> currenc;

        public Snapshot() {
            this.name = Account.this.name;
            this.currenc = new HashMap<>(Account.this.currenc);
        }

        public void load() {
            Account.this.name = name;
            Account.this.currenc.clear();
            Account.this.currenc.putAll(currenc);
        }

    }
}

interface Command{
    void make();
}