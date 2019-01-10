package com.stock.capital.common.utils;

import java.util.function.Supplier;

/**
 * [Conditions].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public class Conditions {

  /**
   * 条件判断.
   *
   * @param expression 表达式
   * @param exceptionSupplier 异常类型
   * @param <T> 异常类型
   * @throws T 异常
   */
  public static <T extends Throwable> void checkState(
      boolean expression, Supplier<? extends T> exceptionSupplier) throws T {
    if (!expression) {
      throw exceptionSupplier.get();
    }
  }
}
