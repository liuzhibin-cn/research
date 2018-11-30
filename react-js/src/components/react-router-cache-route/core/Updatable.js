import { Component } from 'react'
import PropTypes from 'prop-types'

import { get } from '../helpers/try'
import { isExist } from '../helpers/is'

/**
 * 1. 规定`render`是一个函数，放在`props.render`中；
 * 2. 组件是否需要刷新(`shouldComponentUpdate`)：
 *    - `nextProps.match`是有效的`computedMatch`；
 *    - 即非`{ __CacheRoute__computedMatch__null: true }`；
 */
export default class Updatable extends Component {
  static propsTypes = {
    render: PropTypes.func.isRequired,
    match: PropTypes.object.isRequired
  }

  render = () => this.props.render()

  shouldComponentUpdate(nextProps) {
    return (
      isExist(nextProps.match) &&
      get(nextProps, 'match.__CacheRoute__computedMatch__null') !== true
    )
  }
}