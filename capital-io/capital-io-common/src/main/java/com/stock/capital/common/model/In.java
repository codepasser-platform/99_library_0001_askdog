package com.stock.capital.common.model;

import java.io.Serializable;

/**
 * [In].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public interface In<E> extends Serializable {
  E convert();
}
