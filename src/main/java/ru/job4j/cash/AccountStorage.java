package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {

    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        int id = account.id();
        return accounts.replace(id, accounts.get(id), account);
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id, accounts.get(id));
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> accountFrom = getById(fromId);
        Optional<Account> accountTo = getById(toId);
        boolean result = false;
        if (accountFrom.isPresent() && accountTo.isPresent()) {
            Account sender = accountFrom.get();
            Account recipient = accountTo.get();
            result = true;
            if (sender.amount() < amount) {
                throw new IllegalArgumentException("Not enough funds");
            }
            update(new Account(sender.id(), sender.amount() - amount));
            update(new Account(recipient.id(), recipient.amount() + amount));
        }
        return result;
    }
}