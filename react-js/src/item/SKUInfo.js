import React from 'react';
import Scrollbar from 'react-smooth-scrollbar';

class SKUInfo extends React.Component {
    render() {
        // console.log('skuId: ' + this.props.match.params.id);
        return (
            <Scrollbar style={{height:'100%'}} className="sku-page">
              <div style={{ backgroundColor:'#ff8888', height:'400px' }}></div>
              <div style={{ backgroundColor:'#88ff88', height:'400px' }}></div>
              <div style={{ backgroundColor:'#8888ff', height:'400px' }}></div>
            </Scrollbar>
        );
    }
}

export default SKUInfo;