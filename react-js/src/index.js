import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Route } from "react-router-dom";
import AnimatedRouter from './components/AnimatedRouter';
import './components/AnimatedRouter.css';
import CacheRoute from './components/react-router-cache-route/components/CacheRoute';

import * as serviceWorker from './serviceWorker';
import App from './App';
import SKUInfo from './item/SKUInfo';
import './index.css';
// import CacheSwitch from './components/react-router-cache-route/components/CacheSwitch';

const URL = process.env.PUBLIC_URL ? process.env.PUBLIC_URL : '';

ReactDOM.render( 
    <Router>
        <AnimatedRouter className="router-container" appear={false}>
        {/* <CacheSwitch> */}
            <CacheRoute exact path={`${URL}/`} component={App} className="cache-route-wrapper" cacheKey="/" when="forward" />
            <Route path={`${URL}/item/:id`} component={SKUInfo} />
        {/* </CacheSwitch> */}
        </AnimatedRouter>
    </Router>, document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister(); 