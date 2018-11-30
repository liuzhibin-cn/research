import React from 'react';
import { Carousel, Grid } from 'antd-mobile';
import { Link } from "react-router-dom";
import Scrollbar from 'react-smooth-scrollbar';
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