"use strict";

import Vue from 'vue';

const BOOTSTRAP_NEED_VALIDATION_CLASS = 'needs-validation';

const BOOTSTRAP_VALIDATION_CLASS = 'was-validated';

const BOOTSTRAP_IS_INVALID = 'is-invalid';

const BOOTSTRAP_IS_VALID = 'is-valid';

const validator = Vue.directive('validator', {
  bind: function (el, binding) {
  },
  inserted: function (el, binding) {
    bindValidator(el, binding);
  },
  unbind: function (el, binding) {
  }
});

const bindValidator = function (_el, _binding) {
  // register submit handler
  _el.addEventListener('submit', function (_evt) {
    _evt.preventDefault();
    onSubmitHandler(_evt, _el, _binding);
  });
  // register validation listener
  validationListener(_el, _binding);
};

const onSubmitHandler = function (_evt, _el, _binding) {
  const _result = validationForm(_el, _binding);
  let _class = _el.getAttribute('class');
  if (_result) {
    _class = _class.replace(new RegExp(' ' + BOOTSTRAP_VALIDATION_CLASS, 'g'), '');
    _el.setAttribute('class', _class + ' ' + BOOTSTRAP_VALIDATION_CLASS);
    _binding.value.submitHandler();
  } else {
    _class = _class.replace(new RegExp(' ' + BOOTSTRAP_VALIDATION_CLASS, 'g'), '');
    _el.setAttribute('class', _class);
  }
};

const validationForm = function (_el, _binding) {
  const _rules = _binding.value.rules || {};
  let hasInvalid = false;
  for (let _controller in _rules) {
    let _node = document.getElementById(_controller);
    if (_node) {
      const result = controllerValidation(_el, _binding, _controller, _rules[_controller], _node);
      if (result === false) {
        hasInvalid = true;
      }
    }
  }
  console.log('validate form has invalid : ', hasInvalid);
  return !hasInvalid;
};

const validationListener = function (_el, _binding) {
  const _rules = _binding.value.rules || {};
  for (let _controller in _rules) {
    const _node = document.getElementById(_controller);
    if (_node) {
      document.getElementById(_controller).addEventListener('keyup', function () {
        const _nodeName = this.nodeName;
        const _nodeType = this.getAttribute('type');
        if (_nodeName.toLowerCase() === 'select' || _nodeType === 'file' || _nodeType === 'checkbox') {
          return;
        }
        console.log('Directive validator listener > keyup', _nodeName, _nodeType);
        controllerValidation(_el, _binding, _controller, _rules[_controller], this);
      });
      document.getElementById(_controller).addEventListener('blur', function () {
        const _nodeName = this.nodeName;
        const _nodeType = this.getAttribute('type');
        if (_nodeType === 'checkbox') {
          return;
        }
        console.log('Directive validator listener > blur', _nodeName, _nodeType);
        controllerValidation(_el, _binding, _controller, _rules[_controller], this);
      });
      document.getElementById(_controller).addEventListener('change', function () {
        const _nodeName = this.nodeName;
        const _nodeType = this.getAttribute('type');
        if (_nodeName.toLowerCase() === 'select' || _nodeType === 'file' || _nodeType === 'checkbox') {
          console.log('Directive validator listener > change', _nodeName, _nodeType);
          controllerValidation(_el, _binding, _controller, _rules[_controller], this);
        }
      });
    }
  }
};

const controllerValidation = function (_el, _binding, _controller, _rule, _node) {
  const _nodeType = _node.getAttribute('type');
  const _readonlyNode = !!(_node && _node.getAttribute('readonly'));
  const _disabledNode = !!(_node && _node.getAttribute('disabled'));
  if (_readonlyNode || _disabledNode) {
    console.log('skip validation', _readonlyNode, _disabledNode);
    return true;
  }
  let _result = false;
  if (_nodeType) {
    if (_nodeType.toLowerCase() === 'text'
        || _nodeType.toLowerCase() === 'password'
        || _nodeType.toLowerCase() === 'email'
        || _nodeType.toLowerCase() === 'tel'
        || _nodeType.toLowerCase() === 'phone'
        || _nodeType.toLowerCase() === 'week'
        || _nodeType.toLowerCase() === 'month'
        || _nodeType.toLowerCase() === 'time'
        || _nodeType.toLowerCase() === 'date'
        || _nodeType.toLowerCase() === 'datetime'
        || _nodeType.toLowerCase() === 'datetime-local'
        || _nodeType.toLowerCase() === 'color'
        || _nodeType.toLowerCase() === 'number'
        || _nodeType.toLowerCase() === 'search'
        || _nodeType.toLowerCase() === 'range'
        || _nodeType.toLowerCase() === 'file'
        || _nodeType.toLowerCase() === 'checkbox'
    ) {
      _result = validationNormal(_el, _binding, _controller, _rule, _node);
    }
  } else {
    const _nodeName = _node.nodeName;
    if (_nodeName.toLowerCase() === 'select'
        || _nodeName.toLowerCase() === 'textarea') {
      _result = validationNormal(_el, _binding, _controller, _rule, _node);
    }
  }
  console.log('Directive validator > controllerValidation result ', _result);
  return _result;
};

const validationNormal = function (_el, _binding, _controller, _rule, _node) {
  let _value = _node.value;
  const _nodeType = _node.getAttribute('type');
  if (_nodeType && _nodeType.toLowerCase() === 'checkbox') {
    _value = _node.checked;
  }
  for (let _prop in _rule) {
    console.log('Directive validator > validationNormal', _controller, _rule, _prop, _rule[_prop], _value);
    // required
    if (_prop === 'required') {
      if (!_rule[_prop] || (_rule[_prop] && _value)) {
        validationTip(_el, _binding, _controller, _rule, _node, _prop);
      } else {
        validationTipError(_el, _binding, _controller, _rule, _node, _prop);
        return false;
      }
    }
    // reg
    if (_prop === 'regex') {
      if (!_rule[_prop] || (_rule[_prop] && _rule[_prop].test(_value)) || (!_rule['required'] && _value === '')) {
        validationTip(_el, _binding, _controller, _rule, _node, _prop);
      } else {
        validationTipError(_el, _binding, _controller, _rule, _node, _prop);
        return false;
      }
    }

    // TODO
    // custom validator
  }
  return true;
};

const validationTipError = function (_el, _binding, _controller, _rule, _node, _prop) {
  const _parentNode = _node.parentNode;
  let _class = _parentNode.getAttribute('class');

  // fixed bug bootstrap-vue checkbox validator
  _class = _class.replace(new RegExp(' ' + 'custom-control-inline', 'g'), '');

  _class = _class.replace(new RegExp(' ' + BOOTSTRAP_IS_INVALID, 'g'), '');
  _class = _class.replace(new RegExp(' ' + BOOTSTRAP_IS_VALID, 'g'), '');
  _parentNode.setAttribute('class', _class + ' ' + BOOTSTRAP_IS_INVALID);
  if (_binding.value.message && _binding.value.message[_controller] && _binding.value.message[_controller][_prop]) {
    const _lastChild = _parentNode.lastChild;
    if (_lastChild.getAttribute && _lastChild.getAttribute('class') === 'invalid-feedback') {
      _parentNode.removeChild(_lastChild);
    }
    let _tipError = document.createElement('div');
    _parentNode.appendChild(_tipError);
    _tipError.setAttribute('class', 'invalid-feedback');
    _tipError.innerText = _binding.value.message[_controller][_prop];
  }
};

const validationTip = function (_el, _binding, _controller, _rule, _node) {
  const _parentNode = _node.parentNode;
  let _class = _parentNode.getAttribute('class');
  // fixed bug bootstrap-vue checkbox validator
  _class = _class.replace(new RegExp(' ' + 'custom-control-inline', 'g'), '');

  _class = _class.replace(new RegExp(' ' + BOOTSTRAP_IS_INVALID, 'g'), '');
  _class = _class.replace(new RegExp(' ' + BOOTSTRAP_IS_VALID, 'g'), '');
  _parentNode.setAttribute('class', _class + ' ' + BOOTSTRAP_IS_VALID);
};

export default validator;