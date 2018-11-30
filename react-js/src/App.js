import React from 'react';
import { TabBar } from 'antd-mobile';
import Home from './home/Home';
import './App.css';
import StaticResource from './StaticResource';

const TabItem = TabBar.Item;

class App extends React.Component {
  static TopNav = { 
    Home: 'home',
    Category: 'category',
    Cart: 'cart',
    My: 'my'
  }

  constructor(props) {
    super(props);
    this.state = {
      id: new Date().getTime(),
      tab: this.props.tab ? this.props.tab : App.TopNav.Home
    }
    if(props.cacheLifecycles) {
      props.cacheLifecycles.didCache(this.componentDidCache);
      props.cacheLifecycles.didRecover(this.componentDidRecover);
    }
    console.log('>> [' + this.state.id + '] App.constructor');
  }

  componentDidCache = () => {
    // console.log('>> [' + this.state.id + '] Cached')
  }
  componentDidRecover = () => {
    // console.log('>> [' + this.state.id + '] Recovered')
  }

  render() {
    return (
        <TabBar unselectedTintColor="#949494" tintColor="#33A3F4" barTintColor="white">
            <TabItem title="首页" key="top-nav-home" selected={this.state.tab === App.TopNav.Home}
              icon={StaticResource.NavIcons.Home} selectedIcon={StaticResource.NavIcons.HomeActive}
              onPress={() => {
                this.setState({ tab: App.TopNav.Home });
              }}
            >
              <Home />
            </TabItem>

            <TabItem title="分类" key="top-nav-cat" selected={this.state.tab === App.TopNav.Category}
              icon={StaticResource.NavIcons.Cat} selectedIcon={StaticResource.NavIcons.CatActive}
              onPress={() => {
                this.setState({ tab: App.TopNav.Category });
              }}
            >
              <div style={{ height: '100%', textAlign: 'center', paddingTop: '250px' }}>
                <span>You clicked </span><span style={{ fontSize:'16px', fontWeight:'600', margin:'0 15px' }}>分类</span>
              </div>
            </TabItem>

            <TabItem title="购物车" key="top-nav-cart" selected={this.state.tab === App.TopNav.Cart} badge={3}
              icon={StaticResource.NavIcons.Cart} selectedIcon={StaticResource.NavIcons.CartActive}
              onPress={() => {
                this.setState({ tab: App.TopNav.Cart });
              }}
            >
              <div style={{ height: '100%', textAlign: 'center', paddingTop: '250px' }}>
                <span>You clicked </span><span style={{ fontSize:'16px', fontWeight:'600', margin:'0 15px' }}>购物车</span>
              </div>
            </TabItem>

            <TabItem title="我的" key="top-nav-my" selected={this.state.tab === App.TopNav.My}
              icon={StaticResource.NavIcons.My} selectedIcon={StaticResource.NavIcons.MyActive}
              onPress={() => {
                this.setState({ tab: App.TopNav.My });
              }}
            >
              <div style={{ height: '100%', textAlign: 'center', paddingTop: '250px' }}>
                <span>You clicked </span><span style={{ fontSize:'16px', fontWeight:'600', margin:'0 15px' }}>我的</span>
              </div>
            </TabItem>

        </TabBar>
    );
  }
}

export default App;