package com.stock.capital.common.model;

import java.io.Serializable;

/**
 * [Out].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public interface Out<V, E> extends Serializable {
  V from(E entity);
}
