package com.github.signed.sandboxes.jee.data;

import java.util.Map;
import java.util.UUID;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import org.joda.time.LocalDate;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@javax.ejb.Lock(LockType.READ)
public class DayLockBean {

    private final Map<LockToken, LockMetadata> heldLocks = Maps.newHashMap();

    public synchronized Optional<LockToken> acquireLockFor(final LocalDate day) {
        boolean dayNotAlreadyLocked = FluentIterable.from(heldLocks.values()).filter(new Predicate<LockMetadata>() {
            @Override
            public boolean apply(LockMetadata input) {
                return day.equals(input.day());
            }
        }).isEmpty();

        if (dayNotAlreadyLocked) {
            LockToken token = new LockToken(UUID.randomUUID().toString());
            LockMetadata metadata = new LockMetadata(day);
            heldLocks.put(token, metadata);
            return Optional.of(token);
        }
        return Optional.absent();
    }

    public synchronized void release(LockToken token) {
        heldLocks.remove(token);
    }
}
