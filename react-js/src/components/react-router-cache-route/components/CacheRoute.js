import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { Route } from 'react-router-dom'

import CacheComponent from '../core/CacheComponent'
import Updatable from '../core/Updatable'
import { run } from '../helpers/try'

const isEmptyChildren = children => React.Children.count(children) === 0

/**
 * 转用`Route`，虚拟DOM：`Route -> CacheComponent -> Updatable`。
 * - `Route.children`设为`CacheComponent`，所有`props`都丢给了它；
 * - 为每个`CacheComponent.cacheLifecycles`生成1个`Updatable`；
 * 
 * `Updatable`负责创建`CacheRoute`对应的组件，支持通过`component`、`render`、`children`三种
 * 方式指定组件。
 */
export default class CacheRoute extends Component {
  static componentName = 'CacheRoute'

  static propTypes = {
    component: PropTypes.func,
    render: PropTypes.func,
    children: PropTypes.oneOfType([PropTypes.func, PropTypes.node]),
    className: PropTypes.string,
    when: PropTypes.oneOf(['forward', 'back', 'always']),
    behavior: PropTypes.func
  }

  static defaultProps = {
    when: 'forward'
  }
  
  render() {
    let {
      children,
      render,
      component,
      className,
      when,
      behavior,
      cacheKey,
      id,
      ...__rest__props
    } = this.props;

    /**
     * Note:
     * If children prop is a React Element, define the corresponding wrapper component for supporting multiple children
     *
     * 说明：如果 children 属性是 React Element 则定义对应的包裹组件以支持多个子组件
     */
    if (React.isValidElement(children) || !isEmptyChildren(children)) render = () => children;
    
    /**
     * Only children prop of Route can help to control rendering behavior
     * 只有 Router 的 children 属性有助于主动控制渲染行为
     * 
     * 主要执行过程：
     * 虚拟DOM：Route -> CacheComponent -> Updatable，按照这个虚拟节点顺序从左至右处理
     * 1. React创建Route，执行Route.render()；
     * 2. Route.children={props => ( ... )}：
     *    children是一个函数，由React框架在Route.render()中调用，参数为RouteComponentProps，由React框架提供；
     *    作用域范围内的props(即后面所有props)全是引用这个参数；
     * 3. React创建CacheComponent，执行CacheComponent.render()；
     *    CacheComponent.props = Route.children.props + 部分CacheRoute.props；
     * 4. CacheComponent.children为一个函数，在CacheComponent.render()方法中调用的，参数为CacheComponent.cacheLifecycles；
     * 5. CacheComponent.children函数的执行，导致React创建Updatable，执行Updatable.render()；
     * 6. Updatable本身不处理任何render逻辑，通过Updatable.props.render属性来完成；
     * 7. Updatable.props.render负责创建CacheRoute指定的component（支持component属性、render函数、子元素等多种方式）；
     * 
     * 实现路由组件缓存的原理：
     * react-router-dom.Switch：仅展示第1个匹配本次请求路径的Route组件，未匹配的会从DOM中删除掉；
     * CacheSwitch：会展示所有的CacheRoute，未匹配本次请求路径的设为style.display: none; 匹配本次请求路径的正常显示；
     */
    let wrapped = <Route
      {...__rest__props}
      children={props => (
        <CacheComponent
          {...props}
          {...{ when, className, behavior, cacheKey, id }}
        >
          {cacheLifecycles => (
            <Updatable
              match={props.match}
              render={() => {
                Object.assign(props, { cacheLifecycles });
                // console.log('>> Updatable.render in CacheRoute');
                if (component) {
                  return React.createElement(component, props)
                }

                return run(render || children, undefined, props)
              }}
            />
          )}
        </CacheComponent>
      )}
    />;

    return wrapped;
  }
}
