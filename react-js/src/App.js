import React from 'react';
import { TabBar } from 'antd-mobile';
// import { Redirect } from "react-router-dom";
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

  appContext = {
    action: ''
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
    console.log('>> [' + this.state.id + '] constructor');
  }

  componentDidCache = () => {
    this.appContext.action = 'cached';
    // console.log('>> [' + this.state.id + '] Cached')
  }
  componentDidRecover = () => {
    this.appContext.action = 'recoverd';
    // console.log('>> [' + this.state.id + '] Recovered')
  }

  render() {
    // if(!this.state.redirecting && this.props.location && '/' + this.state.tab !== this.props.location.pathname) {
    //   // TabItem点击事件中更新了state.tab，这将触发视图刷新，从而执行App.render()方法。
    //   // 如果不做任何处理，当前路由状态(浏览器地址栏)不会发生任何变化，页面直接切换到新的TabItem，这给整体路由带来麻烦。
    //   // 下面Redirect组件会先刷新路由状态，浏览器地址栏会变成与TabItem对应的地址，
    //   //    路由状态刷新后会重新进入App.render()方法，此时state.tab与location.pathname匹配，不会再次执行Redirect，
    //   //    而是执行后面的return语句，页面直接切换到新的TabItem，以此实现TabItem与路由状态的联动。
    //   console.log('>> [' + this.state.id + '] [redirect] tab:' + this.state.tab + ', path:' + (this.props.location ? this.props.location.pathname : '') );
    //   this.state.redirecting = true;
    //   return <Redirect to={'/' + this.state.tab} />;
    // }
    // console.log('>> [' + this.state.id + '] [render] tab:' + this.state.tab + ', path:' + (this.props.location ? this.props.location.pathname : ''));
    return (
        <TabBar unselectedTintColor="#949494" tintColor="#33A3F4" barTintColor="white">
            <TabItem title="首页" key="top-nav-home" selected={this.state.tab === App.TopNav.Home}
              icon={StaticResource.NavIcons.Home} selectedIcon={StaticResource.NavIcons.HomeActive}
              onPress={() => {
                this.setState({ tab: App.TopNav.Home });
              }}
            >
              <Home appContext={this.appContext} />
            </TabItem>

            <TabItem title="分类" key="top-nav-cat" selected={this.state.tab === App.TopNav.Category}
              icon={StaticResource.NavIcons.Cat} selectedIcon={StaticResource.NavIcons.CatActive}
              onPress={() => {
                this.setState({ tab: App.TopNav.Category });
              }}
            >
              <div>
                <div style={{ backgroundColor:'#ff8888', height:'400px' }}></div>
                <div style={{ backgroundColor:'#88ff88', height:'400px' }}></div>
                <div style={{ backgroundColor:'#8888ff', height:'400px' }}></div>
              </div>
            </TabItem>

            <TabItem title="购物车" key="top-nav-cart" selected={this.state.tab === App.TopNav.Cart} badge={3}
              icon={StaticResource.NavIcons.Cart} selectedIcon={StaticResource.NavIcons.CartActive}
              onPress={() => {
                this.setState({ tab: App.TopNav.Cart });
              }}
            >
              <div style={{ backgroundColor: 'white', height: '100%', textAlign: 'center' }}>
                <div style={{ paddingTop: 60 }}>Clicked 我的 tab</div>
              </div>
            </TabItem>

            <TabItem title="我的" key="top-nav-my" selected={this.state.tab === App.TopNav.My}
              icon={StaticResource.NavIcons.My} selectedIcon={StaticResource.NavIcons.MyActive}
              onPress={() => {
                this.setState({ tab: App.TopNav.My });
              }}
            >
              <div style={{ backgroundColor: 'white', height: '100%', textAlign: 'center' }}>
                <div style={{ paddingTop: 60 }}>Clicked 我的 tab</div>
              </div>
            </TabItem>

        </TabBar>
    );
  }
}

export default App;