import React, { Component } from 'react';
import { findDOMNode } from 'react-dom';
import { TransitionGroup, CSSTransition } from 'react-transition-group';
import { withRouter } from 'react-router-dom';
// import { Switch } from 'react-router-dom';
import PropTypes from 'prop-types';
import CacheSwitch from '../components/react-router-cache-route/components/CacheSwitch';

let lastLocation = { isPush: true };
const REACT_HISTORIES_KEY = 'REACT_HISTORIES_KEY';
const histories = (sessionStorage.getItem(REACT_HISTORIES_KEY) || '').split(',').filter(Boolean);
const isHistoryPush = (location, update, history) => {
    const key = location.key || location.pathname + location.search;
    if (update && key !== lastLocation.key) {
        const index = histories.lastIndexOf(key);

        if (index > -1) {
            histories.splice(index + 1);
        } else {
            histories.push(key);
        }

        sessionStorage.setItem(REACT_HISTORIES_KEY, histories.join(','));

        lastLocation = {
            isPush: index < 0,
            key
        };
    }
    return lastLocation.isPush;
};

/**
 * @desc 路由动画组件
 * @author qiqiboy
 *
 *  需要动画样式文件配合，所以如果使用默认的动画效果，则需要一并将项目中的animated.css导入项目
 *  import AnimatedRouter from 'react-animated-router';
 *  import 'react-animated-router/animate.css';
 */
class AnimatedRouterInner extends Component {
    static propTypes = {
        className: PropTypes.string,
        transitionKey: PropTypes.any,
        timeout: PropTypes.number,
        prefix: PropTypes.string,
        appear: PropTypes.bool,
        enter: PropTypes.bool,
        exit: PropTypes.bool,
        component: PropTypes.any
    };

    static defaultProps = {
        prefix: 'animated-router'
    };

    inTransition = false;

    setInTransition(isAdd) {
        if (this.rootNode) {
            const inName = this.props.prefix + '-in-transition';
            this.rootNode.className = this.rootNode.className
                .split(/\s+/)
                .filter(name => name !== inName)
                .concat(isAdd ? inName : [])
                .join(' ');
        }
    }

    onEnter = node => {
        this.inTransition || this.setInTransition((this.inTransition = true));
        this.lastTransitionNode = node;
    };

    onEntered = node => {
        if (this.lastTransitionNode === node) {
            this.inTransition && this.setInTransition((this.inTransition = false));
        }

        if (node) {
            //remove all transition classNames
            node.className = node.className
                .split(/\s+/)
                .filter(name => !/-(?:forward|backward)-(?:enter|exit)(?:-active)?$/.test(name))
                .join(' ');
        }
    };

    componentDidMount() {
        this.rootNode = findDOMNode(this);
    }

    render() {
        console.log('>> AnimatedRouter.render');
        const { className, location, history, match, children, timeout, prefix, appear, enter, exit, component } = this.props;
        const groupProps = {
            appear,
            enter,
            exit,
            component
        };
        const cssProps = {
            onExit: this.onEnter,
            onExited: this.onEntered,
            onEnter: this.onEnter,
            onEntered: this.onEntered
        };
        const cls = [prefix + '-container', 'react-animated-router', className];
        return (
            <TransitionGroup
                className={cls.filter(Boolean).join(' ')}
                childFactory={child => {
                    const classNames =
                        prefix + '-' + (isHistoryPush(location, child.props.in, history) ? 'forward' : 'backward');

                    return React.cloneElement(child, {
                        classNames
                    });
                }}
                {...groupProps}>
                <CSSTransition
                    key={this.props.transitionKey || location.pathname}
                    addEndListener={(node, done) => {
                        node.addEventListener(
                            'transitionend',
                            function(e) {
                                //确保动画来自于目标节点
                                if (e.target === node) {
                                    done();
                                }
                            },
                            false
                        );
                    }}
                    unmountOnExit={false}
                    timeout={timeout}
                    {...cssProps}>
                    {/* CacheSwitch必须放在这里，不能放在children中，否则转场效果有问题 */}
                    <CacheSwitch location={location} history={history} match={match}>{children}</CacheSwitch>
                    {/* <Switch location={location} history={history} match={match}>{children}</Switch> */}
                </CSSTransition>
            </TransitionGroup>
        );
    }
}

const AnimatedRouter = withRouter(AnimatedRouterInner);

export default AnimatedRouter;