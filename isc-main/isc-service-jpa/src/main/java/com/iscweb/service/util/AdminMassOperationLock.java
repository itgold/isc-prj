package com.iscweb.service.util;

import com.iscweb.common.exception.ServiceException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A singleton instance that restricts the amount of batch operations that can run at the same time.
 */
@Slf4j
public class AdminMassOperationLock {

    private static final AdminMassOperationLock INSTANCE = new AdminMassOperationLock();

    @Getter
    private String message;

    @Getter
    private AtomicBoolean massOperationLocked = new AtomicBoolean(false);

    private AdminMassOperationLock() {
    }

    private static AdminMassOperationLock getInstance() {
        return INSTANCE;
    }

    /**
     * Convenience method to obtain a lock on the singleton instance of this class.
     * See {@link #lockOrFail(String)} for details. It is synchronized method.
     */
    public static void lockMassOperationOrFail(String operationDetail) throws LockDisallowedException {
        getInstance().lockOrFail(operationDetail);
    }

    /**
     * Convenience method to release a lock.
     * See {@link #unlock()} for details. It is synchronized.
     */
    public static void unlockMassOperation() {
        getInstance().unlock();
    }

    /**
     * Checks if a lock is available in order to obtain a lock. If it is not available,
     * an exception is thrown.
     *
     * @param operationDetail a Message with the operation details.
     * @throws LockDisallowedException if a lock cannot be obtained.
     */
    private synchronized void lockOrFail(String operationDetail) throws LockDisallowedException {
        if (getMassOperationLocked().get()) {
            throw new LockDisallowedException("Mass operation is underway already: " + getMessage());
        } else {
            lockMassOperation(operationDetail);
        }
    }

    private synchronized void lockMassOperation(String currentMessage) {
        if (getMassOperationLocked().get()) {
            log.warn("Disallowed locking with message {}", currentMessage);
            throw new IllegalStateException("Can't lock mass operation. Previous call set was: " + message);
        }
        getMassOperationLocked().set(true);
        this.message = currentMessage;
    }

    /**
     * Enables further mass operations.
     */
    private synchronized void unlock() {
        if (!getMassOperationLocked().get()) {
            log.info("Trying to unlock mass operation that is already unlocked");
        }
        log.info("Unlocking mass operation. Previous set message was: {}", message);
        getMassOperationLocked().set(false);
    }

    /**
     * A specific exception to signal that locking failed because other mass operations
     * were taking place.
     */
    public static class LockDisallowedException extends ServiceException {
        public LockDisallowedException(String message) {
            super(message);
        }
    }
}
