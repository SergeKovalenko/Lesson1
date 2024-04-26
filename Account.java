package Lesson1;

import lombok.ToString;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;


@ToString
class Account {
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
        saves.push(() -> Account.this.name = tmp);
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
        Integer oldAmount = Account.this.currenc.get(currency);
        if (oldAmount != null) {
            saves.push(() -> {
                Account.this.currenc.put(currency, oldAmount);
            });
        } else {
            saves.push(() -> {
                Account.this.currenc.remove(currency);
            });
        }
        currenc.put(currency, amount);
    }

    public void undo() {
        if (saves.isEmpty()) throw new RuntimeException("Стек пустой. Нечего откатывать");
        saves.pop().make();
    }

    public Saveable save() {
        return new AccSave();
    }

    private class AccSave implements Saveable {
        private String name;
        private final Map<Currency, Integer> currenc;

        public AccSave() {
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

interface Command {
    void make();
}
