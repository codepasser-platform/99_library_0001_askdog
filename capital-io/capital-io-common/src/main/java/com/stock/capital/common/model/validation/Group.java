package com.stock.capital.common.model.validation;

import javax.validation.groups.Default;

/**
 * [Group].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public interface Group {

  interface Create extends Default {}

  interface CreateByApi extends Default {}

  interface Edit extends Default {}

  interface Delete extends Default {}
}
