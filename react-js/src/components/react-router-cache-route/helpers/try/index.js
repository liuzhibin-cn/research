import { isString, isExist, isUndefined, isFunction } from '../is'

/**
 * 从`obj`获取属性值
 * 
 * 解决从中间某一级开始为null时的繁琐判断
 * 
 * @param {*} obj 
 * @param {*} keys 属性名，获取`obj.prop1.prop2.prop3`有2种写法：
 * - `"prop1.prop2.prop3"`
 * - `[prop1, prop2, prop3]`
 * @param {*} defaultValue 
 */
export const get = (obj, keys = [], defaultValue) => {
  keys = isString(keys) ? keys.split('.') : keys

  let result
  let res = obj
  let idx = 0

  for (; idx < keys.length; idx++) {
    let key = keys[idx]

    if (isExist(res)) {
      res = res[key]
    } else {
      break
    }
  }

  if (idx === keys.length) {
    result = res
  }

  return isUndefined(result) ? defaultValue : result
}

/**
 * 执行`obj`方法
 * 
 * 如果`obj.prop1.func`不是函数，直接返回该属性，作用同`get`方法；
 * 1. 解决从中间某一级开始为`null`时的繁琐判断；
 * 2. 解决`render`可以针对`Component`元素，也可以针对`function`方法；
 * @param {*} obj 
 * @param {*} keys 方法名，调用`obj.prop1.func`有2种写法：`"prop1.func"`, `[prop1, func]`
 * @param  {...any} args 参数
 */
export const run = (obj, keys = [], ...args) => {
  keys = isString(keys) ? keys.split('.') : keys

  const func = get(obj, keys)
  const context = get(obj, keys.slice(0, -1))

  return isFunction(func) ? func.call(context, ...args) : func
}

/**
 * 返回第1个不等于undefined的元素
 * @param  {...any} values 
 */
export const value = (...values) =>
  values.reduce(
    (value, nextValue) => (isUndefined(value) ? run(nextValue) : run(value)),
    undefined
  )
