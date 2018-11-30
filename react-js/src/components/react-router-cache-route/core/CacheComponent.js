import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { run, get, value } from '../helpers/try'
import { register } from './manager'

const __new__lifecycles = Number(get(run(React, 'version.match', /^\d*\.\d*/), [0])) >= 16.3;

  /**
   * 根据前后动作获取、决定本`CacheComponent`是否需要缓存、是否匹配
   * @param {*} nextProps 
   * @param {*} prevState 
   */
const getDerivedStateFromProps = (nextProps, prevState) => {
  let { match: nextMatch, when = 'forward' } = nextProps
  if (get(nextMatch, '__CacheRoute__computedMatch__null')) nextMatch = null;

  if(nextMatch) return { cached:true, matched:true };

  const nextAction = get(nextProps, 'history.action');
  let cached = true;
  switch (when) {
    //任何情况都缓存
    case 'always': break;
    //后退时缓存
    case 'back':
      if (['PUSH', 'REPLACE'].includes(nextAction))  cached = false;
      break;
    //前进时缓存
    case 'forward':
    default: if (nextAction === 'POP')  cached = false;
  }

  return { cached:cached, matched:!!nextMatch };

  // //之前未缓存，本次路由匹配
  // if (!prevState.cached && nextMatch) return { cached: true, matched: true };
  // //之前路由匹配，本次操作路由不匹配，根据缓存策略决定是否缓存
  // if (prevState.matched && !nextMatch) {
  //   const nextAction = get(nextProps, 'history.action');
  //   let __cancel__cache = false;
  //   switch (when) {
  //     case 'always': break
  //     case 'back':
  //       if (['PUSH', 'REPLACE'].includes(nextAction))  __cancel__cache = true;
  //       break;
  //     case 'forward':
  //     default: if (nextAction === 'POP')  __cancel__cache = true;
  //   }

  //   if (__cancel__cache) return { cached: false, matched: false };
  // }

  // return { matched: !!nextMatch }
}

export default class CacheComponent extends Component {
  static propsTypes = {
    history: PropTypes.object.isRequired,
    match: PropTypes.object.isRequired,
    children: PropTypes.func.isRequired,
    className: PropTypes.string,
    when: PropTypes.oneOf(['forward', 'back', 'always']),
    behavior: PropTypes.func
  }

  static defaultProps = {
    when: 'forward',
    behavior: cached => cached ? { style: { display: 'none' } } : undefined
  }

  render() {
    // console.log('>> CacheComponent.render');
    const { className: behavior__className = '', ...behaviorProps } = value(
      this.props.behavior(!this.state.matched),
      {}
    );
    const { className: props__className = '' } = this.props
    const className = run(`${props__className} ${behavior__className}`, 'trim')
    const hasClassName = className !== ''

    // cached输出Updatable，否则什么都不输出
    const wrapper = this.state.cached ? (
      <div className={hasClassName ? className : undefined} {...behaviorProps} id={this.props.id}>
        {run(this.props, 'children', this.cacheLifecycles)}
      </div>
    ) : null;
    return wrapper;
  }

  constructor(props, ...args) {
    super(props, ...args);
    console.log('>> CacheComponent.constructor');    
    if (props.cacheKey) register(props.cacheKey, this);
    /**
     * - `matched`: 是否显示该组件，根据`CacheRoute`是否匹配本次请求路径得到；
     *    - `true`: 组件正常显示；
     *    - `false`: 输出组件，但组件隐藏：`style.display: none`；
     * - `cached`: 是否输出该组件的DOM节点
     *    - `true`: 输出该组件，是否隐藏由`matched`控制；
     *    - `false`: 不输出该组件(从DOM中删除)；
     */
    this.state = getDerivedStateFromProps(props, {
      cached: false,
      matched: false
    });
  }

  /**
   * New lifecycle for replacing the `componentWillReceiveProps` in React 16.3 +
   * React 16.3 + 版本中替代 componentWillReceiveProps 的新生命周期
   */
  static getDerivedStateFromProps = __new__lifecycles ? getDerivedStateFromProps : undefined

  /**
   * Compatible React 16.3 -
   * 兼容 React 16.3 - 版本
   */
  // componentWillReceiveProps = !__new__lifecycles
  //   ? nextProps => {
  //       this.setState(
  //         getDerivedStateFromProps(nextProps, this.state)
  //       )
  //     }
  //   : undefined

  componentDidUpdate(prevProps, prevState) {
    if (!prevState.cached || !this.state.cached) return;
    if (prevState.matched === true && this.state.matched === false) return run(this, 'cacheLifecycles.__listener.didCache');
    if (prevState.matched === false && this.state.matched === true) return run(this, 'cacheLifecycles.__listener.didRecover');
  }

  shouldComponentUpdate(nextProps, nextState) {
    return (
      this.state.matched ||
      nextState.matched ||
      this.state.cached !== nextState.cached
    )
  }

  cacheLifecycles = {
    __listener: null,
    didCache: listener => {
      if(!this.cacheLifecycles.__listener) this.cacheLifecycles.__listener = {};
      this.cacheLifecycles.__listener['didCache'] = listener
    },
    didRecover: listener => {
      this.cacheLifecycles.__listener['didRecover'] = listener
    }
  }
}
