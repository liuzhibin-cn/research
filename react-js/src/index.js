import React from 'react';
import ReactDOM from 'react-dom';
// https://segmentfault.com/a/1190000014294604
import { BrowserRouter as Router, Route } from "react-router-dom";
// react-animated-router: 
//    1. https://github.com/qiqiboy/react-animated-router
//    2. http://www.qiqiboy.com/post/111
import AnimatedRouter from './components/AnimatedRouter';
import './components/AnimatedRouter.css';
// react-router-cache-route: https://github.com/CJY0208/react-router-cache-route/blob/master/README_CN.md
// import CacheRoute from './components/react-router-cache-route/components/CacheRoute';

import * as serviceWorker from './serviceWorker';
import App from './App';
import SKUInfo from './item/SKUInfo';
import './index.css';
// import CacheSwitch from './components/react-router-cache-route/components/CacheSwitch';

ReactDOM.render( 
    <Router>
        <AnimatedRouter className="router-container" appear={false}>
        {/* <CacheSwitch> */}
            <Route exact path="/" component={App} className="cache-route-wrapper" cacheKey="/" when="forward" />
            <Route path="/item/:id" component={SKUInfo} />
        {/* </CacheSwitch> */}
        </AnimatedRouter>
    </Router>, document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister(); 