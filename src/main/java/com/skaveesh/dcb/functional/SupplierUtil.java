package com.skaveesh.dcb.functional;

import java.util.function.Supplier;

public final class SupplierUtil {

  @FunctionalInterface
  public interface SupplierWithException<T, E extends Exception> {
    T get() throws Throwable;
  }

  public static <T, E extends Exception> Supplier<T> rethrowSupplier(SupplierWithException<T, E> supplier) {
    return () -> {
      try {
        return supplier.get();
      } catch (Throwable exception) {
        throwAsUnchecked(exception);
        return null;
      }
    };
  }

  @SuppressWarnings("unchecked")
  private static <E extends Throwable> void throwAsUnchecked(Throwable exception) throws E {
    throw (E) exception;
  }

}

