import React from 'react';
import './SKUListItem.css';

class SKUListItem extends React.Component {
    render() {
        return (
            <div className="sku-list-item" key={this.props.id}>
                <img className="sku-image" alt="" src={this.props.imageUrl} />
                <div className="sku-info">
                    <div className="sku-title"><span>{this.props.title}</span></div>
                    <div className="sku-price"><span>￥{this.props.price}</span></div>
                </div>
            </div>
        );
    }
}

export default SKUListItem;