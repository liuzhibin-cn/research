import React from 'react';
import { Carousel, Grid } from 'antd-mobile';
import { Link } from "react-router-dom";
//https://liuzhibin-cn.github.io/research/react-js/build
// react-smooth-scrollbar，参考：https://idiotwu.github.io/smooth-scrollbar/ 和 https://www.ctolib.com/article/wiki/16148
// 问题描述：
//   tab-bar全屏后，tab-item内容滚动不平滑，将tab-item的内容包装在react-smooth-scrollbar中解决这个问题；
//   react-smooth-scrollbar使用translate3d模拟滚动效果；
// 注意：必须将Scrollbar高度设置为100%，否则Scrollbar不会起作用。
import Scrollbar from 'react-smooth-scrollbar';
// overscroll插件：
//   react-smooth-scrollbar滚动到内容的顶部或底部后，不再响应滚动事件。
//   overscroll则允许继续响应滚动事件，视觉效果是在顶部或底部拉出一个空白区域，用户停止滚动操作后空白区域逐渐消失，可用于实现下拉或上拉刷新的视觉效果。
//   这里不需要这个效果，所以保留代码，仅将其注释掉。
// import SmoothScrollbar from 'smooth-scrollbar';
// import OverscrollPlugin from 'smooth-scrollbar/plugins/overscroll';

import SKUListItem from '../item/SKUListItem';
import './Home.css';
import StaticResource from '../StaticResource';

// SmoothScrollbar.use(OverscrollPlugin);

const URL = process.env.PUBLIC_URL ? process.env.PUBLIC_URL : '';

class Home extends React.Component {
    static refreshing = false
    constructor(props) {
        super(props);
        this.state = {
            refreshing: false, 
            columnData: [
                { icon: StaticResource.ShortcutIcons[0], title:'拼购', url:'#fe1'},
                { icon: StaticResource.ShortcutIcons[1], title:'充值', url:'#fe2'},
                { icon: StaticResource.ShortcutIcons[2], title:'领券', url:'#fe3'},
                { icon: StaticResource.ShortcutIcons[3], title:'更多', url:'#fe4'}
            ], 
            carouselData: StaticResource.Demo.IndexCarouselImages.slice(0)
        };
    }

    render() {
      return (
        <Scrollbar className="home-container">
            <Carousel autoplay={true} autoplayInterval={4000} swipeSpeed={300} infinite slideWidth={1} className="carousel">
                {this.state.carouselData.map((val, index) => (
                    <a key={index} href="#a" className="carousel-link">
                        <img alt="" src={val} className="carousel-img" />
                    </a>
                ))}
            </Carousel>
            <Grid data={this.state.columnData} columnNum={4} hasLine={false}
                renderItem={item => (
                    <a href={item.url} style={{ display:'inline-block' }}>
                        <img src={item.icon} style={{ width: '40px', height: '40px' }} alt="" />
                        <div style={{ color: '#888', fontSize: '14px', marginTop: '6px' }}>
                            <span>{item.title}</span>
                        </div>
                    </a>
                )}
            />
            {StaticResource.Demo.Items.map(item => (
                <Link key={item.id} to={`${URL}/item/${item.id}`}>
                    <SKUListItem id={item.id} price={item.price} title={item.title} imageUrl={item.listImage} />
                </Link>
            ))}
        </Scrollbar>
      );
    }
  }
  
  export default Home;