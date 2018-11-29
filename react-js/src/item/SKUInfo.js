import React from 'react';
import { Link } from "react-router-dom";
import Scrollbar from 'react-smooth-scrollbar';
// import SmoothScrollbar from 'smooth-scrollbar';
// import OverscrollPlugin from 'smooth-scrollbar/plugins/overscroll';
import StaticResource from '../StaticResource';
import { Carousel } from 'antd-mobile';
import './SKUInfo.css';

// SmoothScrollbar.use(OverscrollPlugin);

const URL = process.env.PUBLIC_URL ? process.env.PUBLIC_URL : '';

class SKUInfo extends React.Component {
    render() {
        const id = Number(this.props.match.params.id);
        const item = StaticResource.Demo.Items.filter(item => item.id === id)[0];
        return (
            <div className="sku-page-wrapper">
                <Link to={`${URL}/`} className="sku-top-bar"><div className="icon-back"></div></Link>
                <Scrollbar style={{height:'100%'}} className="sku-scrollbar-wrapper">
                    <Carousel autoplay={true} autoplayInterval={4000} infinite swipeSpeed={300} slideWidth={1} className="sku-main-img-container">
                        {item.mainImages.map((url, index) => (
                            <img key={index} alt="" src={url} className="sku-main-img" />
                        ))}
                    </Carousel>
                    <div className="sku-info">
                        <div className="price-wrapper"><span>￥</span><span className="price">{item.price}</span></div>
                        <label className="title">{item.title}</label>
                    </div>

                    <div className="sku-spec">
                        <div className="l"><span className="small light">送至</span></div>
                        <div className="r">
                            <div style={{padding: '0 35px'}}><span>山东省青岛市李沧区巨峰路1723号</span></div>
                            <div style={{padding: '0 35px'}}>
                                <span className="red small">现货 </span><span className="small light">23:00前下单，预计明天(11月30日)送达，受天气影响，您的订单可能会延迟配送，我们会全力为您服务，请您耐心等待。</span>
                            </div>
                        </div>
                    </div>

                    <div className="sku-content">
                        {item.detailImages ? item.detailImages.map((url, index) => (
                            <img alt="" key={index} src={url} className="sku-detail-img" />
                        )) : (
                            <div style={{textAlign:'center', margin: '10px 0', padding: '50px 0', backgroundColor: 'white'}}>
                                No detail info
                            </div>
                        ) }
                    </div>
                </Scrollbar>
                <div className="sku-bottom-bar">
                    <div className="icon-btn">
                        <i className="icon-shop"></i>
                        <span>店铺</span>
                    </div>
                    <div className="icon-btn">
                        <i className="icon-srv"></i>
                        <span>客服</span>
                    </div>
                    <span className="icon-cart">加入购物车</span>
                    <span className="icon-buy">立即购买</span>
                </div>
            </div>
        );
    }
}

export default SKUInfo;