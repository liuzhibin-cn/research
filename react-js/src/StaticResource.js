import React from 'react';

const URL = process.env.PUBLIC_URL ? process.env.PUBLIC_URL : '';

export default class StaticResource {
    static ShortcutIcons = [
        URL + '/assets/images/shortcut-icons/shortcut-01.png', 
        URL + '/assets/images/shortcut-icons/shortcut-02.png', 
        URL + '/assets/images/shortcut-icons/shortcut-03.png', 
        URL + '/assets/images/shortcut-icons/shortcut-04.png'
    ]
    static NavIcons = {
        Home: <div style={{ width:'21px', height:'21px', backgroundImage:'url(' + URL + '/assets/images/nav-icons/home.svg)', backgroundPosition:'center', backgroundSize:'21px 21px', backgroundRepeat:'no-repeat' }} />,
        HomeActive: <div style={{ width:'21px', height:'21px', backgroundImage:'url(' + URL + '/assets/images/nav-icons/home-active.svg)', backgroundPosition:'center', backgroundSize:'21px 21px', backgroundRepeat:'no-repeat' }} />,
        Cat: <div style={{ width:'21px', height:'21px', backgroundImage:'url(' + URL + '/assets/images/nav-icons/category.svg)', backgroundPosition:'center', backgroundSize:'21px 21px', backgroundRepeat:'no-repeat' }} />,
        CatActive: <div style={{ width:'21px', height:'21px', backgroundImage:'url(' + URL + '/assets/images/nav-icons/category-active.svg)', backgroundPosition:'center', backgroundSize:'21px 21px', backgroundRepeat:'no-repeat' }} />,
        Cart: <div style={{ width:'21px', height:'21px', backgroundImage:'url(' + URL + '/assets/images/nav-icons/cart.svg)', backgroundPosition:'center', backgroundSize:'21px 21px', backgroundRepeat:'no-repeat' }} />,
        CartActive: <div style={{ width:'21px', height:'21px', backgroundImage:'url(' + URL + '/assets/images/nav-icons/cart-active.svg)', backgroundPosition:'center', backgroundSize:'21px 21px', backgroundRepeat:'no-repeat' }} />,
        My: <div style={{ width:'21px', height:'21px', backgroundImage:'url(' + URL + '/assets/images/nav-icons/my.svg)', backgroundPosition:'center', backgroundSize:'21px 21px', backgroundRepeat:'no-repeat' }} />,
        MyActive: <div style={{ width:'21px', height:'21px', backgroundImage:'url(' + URL + '/assets/images/nav-icons/my-active.svg)', backgroundPosition:'center', backgroundSize:'21px 21px', backgroundRepeat:'no-repeat' }} />
    }
    static Demo = {
        IndexCarouselImages: [
            URL + '/assets/images/demo/index-carousel-01.jpg', 
            URL + '/assets/images/demo/index-carousel-02.jpg', 
            URL + '/assets/images/demo/index-carousel-03.jpg', 
            URL + '/assets/images/demo/index-carousel-04.jpg'
        ], 
        Items: [
            {
                id: 224679,
                price: 13488.00,
                title: 'Apple MacBook Pro 13.3英寸笔记本电脑 银色 2018新款（四核八代i5 8G 256G固态硬盘 MR9U2CH/A）',
                listImage: URL + '/assets/images/demo/item/list-224679.jpg',
                mainImages: [URL + '/assets/images/demo/item/main-224679-01.jpg',URL + '/assets/images/demo/item/main-224679-02.jpg'],
                detailImages: [
                    URL + '/assets/images/demo/item/d-224679-01.jpg',
                    URL + '/assets/images/demo/item/d-224679-02.jpg',
                    URL + '/assets/images/demo/item/d-224679-03.jpg',
                    URL + '/assets/images/demo/item/d-224679-04.jpg',
                    URL + '/assets/images/demo/item/d-224679-05.jpg',
                    URL + '/assets/images/demo/item/d-224679-06.jpg',
                    URL + '/assets/images/demo/item/d-224679-07.jpg',
                    URL + '/assets/images/demo/item/d-224679-08.jpg'
                ]
            }, {
                id: 224680,
                price: 6299.00,
                title: '三星（SAMSUNG） UA65MUC30SJXXZ 65英寸4K智能HDR曲面液晶电视 官方正品',
                listImage: URL + '/assets/images/demo/item/list-224680.jpg',
                mainImages: [URL + '/assets/images/demo/item/main-224680-01.jpg',URL + '/assets/images/demo/item/main-224680-02.jpg'],
                detailImages: [
                    URL + '/assets/images/demo/item/d-224680-01.jpg',
                    URL + '/assets/images/demo/item/d-224680-02.jpg',
                    URL + '/assets/images/demo/item/d-224680-03.jpg',
                    URL + '/assets/images/demo/item/d-224680-04.jpg',
                    URL + '/assets/images/demo/item/d-224680-06.jpg',
                    URL + '/assets/images/demo/item/d-224680-07.jpg',
                    URL + '/assets/images/demo/item/d-224680-08.jpg'
                ]
            }, { 
                id: 224681, 
                price: 3699.00,
                title: 'MOOKA/模卡65英寸4K超高清人工智能网络语音液晶电视60 香槟金', 
                listImage: URL + '/assets/images/demo/item/list-224681.jpg' ,
                mainImages: [URL + '/assets/images/demo/item/main-224681-01.jpg',URL + '/assets/images/demo/item/main-224681-02.jpg'],
                detailImages: [
                    URL + '/assets/images/demo/item/d-224679-01.jpg',
                    URL + '/assets/images/demo/item/d-224679-02.jpg',
                    URL + '/assets/images/demo/item/d-224679-03.jpg',
                    URL + '/assets/images/demo/item/d-224679-04.jpg',
                    URL + '/assets/images/demo/item/d-224679-05.jpg',
                    URL + '/assets/images/demo/item/d-224679-06.jpg',
                    URL + '/assets/images/demo/item/d-224679-07.jpg',
                    URL + '/assets/images/demo/item/d-224679-08.jpg'
                ]
            }, {
                id: 224682,
                title: '海尔滚筒洗衣机全自动 高温蒸汽除螨 10KG纤维级防皱洗烘一体变频',
                price: 4099,
                listImage: URL + '/assets/images/demo/item/list-224682.jpg',
                mainImages: [URL + '/assets/images/demo/item/main-224682-01.jpg',URL + '/assets/images/demo/item/main-224682-02.jpg'],
                detailImages: [
                    URL + '/assets/images/demo/item/d-224680-01.jpg',
                    URL + '/assets/images/demo/item/d-224680-02.jpg',
                    URL + '/assets/images/demo/item/d-224680-03.jpg',
                    URL + '/assets/images/demo/item/d-224680-04.jpg',
                    URL + '/assets/images/demo/item/d-224680-06.jpg',
                    URL + '/assets/images/demo/item/d-224680-07.jpg',
                    URL + '/assets/images/demo/item/d-224680-08.jpg'
                ]
            }, {
                id: 224683,
                price: 3499,
                title: '海尔十字对开门冰箱多门双门超薄机身静音电冰箱 482升匀冷保鲜低温净味 速冻 金色新品',
                listImage: URL + '/assets/images/demo/item/list-224683.jpg',
                mainImages: [URL + '/assets/images/demo/item/main-224683-01.jpg',URL + '/assets/images/demo/item/main-224683-02.jpg'],
                detailImages: [
                    URL + '/assets/images/demo/item/d-224679-01.jpg',
                    URL + '/assets/images/demo/item/d-224679-02.jpg',
                    URL + '/assets/images/demo/item/d-224679-03.jpg',
                    URL + '/assets/images/demo/item/d-224679-04.jpg',
                    URL + '/assets/images/demo/item/d-224679-05.jpg',
                    URL + '/assets/images/demo/item/d-224679-06.jpg',
                    URL + '/assets/images/demo/item/d-224679-07.jpg',
                    URL + '/assets/images/demo/item/d-224679-08.jpg'
                ]
            }, {
                id: 224684,
                price: 13488.00,
                title: 'Apple MacBook Pro 13.3英寸笔记本电脑 银色 2018新款（四核八代i5 8G 256G固态硬盘 MR9U2CH/A）',
                listImage: URL + '/assets/images/demo/item/list-224679.jpg',
                mainImages: [URL + '/assets/images/demo/item/main-224679-01.jpg',URL + '/assets/images/demo/item/main-224679-02.jpg'],
                detailImages: [
                    URL + '/assets/images/demo/item/d-224679-01.jpg',
                    URL + '/assets/images/demo/item/d-224679-02.jpg',
                    URL + '/assets/images/demo/item/d-224679-03.jpg',
                    URL + '/assets/images/demo/item/d-224679-04.jpg',
                    URL + '/assets/images/demo/item/d-224679-05.jpg',
                    URL + '/assets/images/demo/item/d-224679-06.jpg',
                    URL + '/assets/images/demo/item/d-224679-07.jpg',
                    URL + '/assets/images/demo/item/d-224679-08.jpg'
                ]
            }, {
                id: 224685,
                price: 6299.00,
                title: '三星（SAMSUNG） UA65MUC30SJXXZ 65英寸4K智能HDR曲面液晶电视 官方正品',
                listImage: URL + '/assets/images/demo/item/list-224680.jpg',
                mainImages: [URL + '/assets/images/demo/item/main-224680-01.jpg',URL + '/assets/images/demo/item/main-224680-02.jpg'],
                detailImages: [
                    URL + '/assets/images/demo/item/d-224680-01.jpg',
                    URL + '/assets/images/demo/item/d-224680-02.jpg',
                    URL + '/assets/images/demo/item/d-224680-03.jpg',
                    URL + '/assets/images/demo/item/d-224680-04.jpg',
                    URL + '/assets/images/demo/item/d-224680-06.jpg',
                    URL + '/assets/images/demo/item/d-224680-07.jpg',
                    URL + '/assets/images/demo/item/d-224680-08.jpg'
                ]
            }, { 
                id: 224686, 
                price: 3699.00,
                title: 'MOOKA/模卡65英寸4K超高清人工智能网络语音液晶电视60 香槟金', 
                listImage: URL + '/assets/images/demo/item/list-224681.jpg',
                mainImages: [URL + '/assets/images/demo/item/main-224681-01.jpg',URL + '/assets/images/demo/item/main-224681-02.jpg'],
                detailImages: [
                    URL + '/assets/images/demo/item/d-224680-01.jpg',
                    URL + '/assets/images/demo/item/d-224680-02.jpg',
                    URL + '/assets/images/demo/item/d-224680-03.jpg',
                    URL + '/assets/images/demo/item/d-224680-04.jpg',
                    URL + '/assets/images/demo/item/d-224680-06.jpg',
                    URL + '/assets/images/demo/item/d-224680-07.jpg',
                    URL + '/assets/images/demo/item/d-224680-08.jpg'
                ]
            }, {
                id: 224687,
                title: '海尔滚筒洗衣机全自动 高温蒸汽除螨 10KG纤维级防皱洗烘一体变频',
                price: 4099,
                listImage: URL + '/assets/images/demo/item/list-224682.jpg',
                mainImages: [URL + '/assets/images/demo/item/main-224682-01.jpg',URL + '/assets/images/demo/item/main-224682-02.jpg'],
                detailImages: [
                    URL + '/assets/images/demo/item/d-224679-01.jpg',
                    URL + '/assets/images/demo/item/d-224679-02.jpg',
                    URL + '/assets/images/demo/item/d-224679-03.jpg',
                    URL + '/assets/images/demo/item/d-224679-04.jpg',
                    URL + '/assets/images/demo/item/d-224679-05.jpg',
                    URL + '/assets/images/demo/item/d-224679-06.jpg',
                    URL + '/assets/images/demo/item/d-224679-07.jpg',
                    URL + '/assets/images/demo/item/d-224679-08.jpg'
                ]
            }, {
                id: 224688,
                price: 3499,
                title: '海尔十字对开门冰箱多门双门超薄机身静音电冰箱 482升匀冷保鲜低温净味 速冻 金色新品',
                listImage: URL + '/assets/images/demo/item/list-224683.jpg',
                mainImages: [URL + '/assets/images/demo/item/main-224683-01.jpg',URL + '/assets/images/demo/item/main-224683-02.jpg'],
                detailImages: [
                    URL + '/assets/images/demo/item/d-224679-01.jpg',
                    URL + '/assets/images/demo/item/d-224679-02.jpg',
                    URL + '/assets/images/demo/item/d-224679-03.jpg',
                    URL + '/assets/images/demo/item/d-224679-04.jpg',
                    URL + '/assets/images/demo/item/d-224679-05.jpg',
                    URL + '/assets/images/demo/item/d-224679-06.jpg',
                    URL + '/assets/images/demo/item/d-224679-07.jpg',
                    URL + '/assets/images/demo/item/d-224679-08.jpg'
                ]
            }
        ]
    }
}