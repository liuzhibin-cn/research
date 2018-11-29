import React from 'react';
import './SKUListItem.css';

class SKUListItem extends React.Component {
    render() {
        return (
            <div className="sku-list-item" key={this.props.id}>
                <img className="sku-list-image" alt="" src={this.props.imageUrl} />
                <div className="sku-list-info">
                    <div className="sku-list-title"><span>{this.props.title}</span></div>
                    <div className="sku-list-price"><span className="price">￥{this.props.price}</span></div>
                </div>
            </div>
        );
    }
}

export default SKUListItem;