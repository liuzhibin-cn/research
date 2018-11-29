#### 已知问题
 1. 路由切换后页面状态丢失，例如在首页向下滚动，点击单品进入单品页，返回首页时，将回到首页顶部；<br />
    需要`CacheRoute`来解决，但`CacheRoute`与`AnimatedRouter`结合使用存在问题；
 2. 使用浏览器的前进后退按钮，转场效果正常；iPhone下从Safari左、右边缘开始滑动触发的前进后退，会出现2次转场效果；
 
 #### `CacheRoute`和`AnimatedRoute`r结合使用的问题：
 1. 在路由切换过程中，整个CacheSwitch下面的组件都会强制刷新，全部重新创建，包括：
    - 从首页进入单品页，会新创建一个App组件实例；
    - 从单品页返回首页，同样新创建一个App组件实例；
    即不仅缓存失效，比没有用CacheRoute还增加了组件创建动作；
 2. CacheRoute组件缓存/隐藏期间，下面包含自动刷新的子组件(主要是自动播放的动画效果，例如首页banner图自动轮播)，则路由返回后，子组件不可见了(下一次自动刷新后会展示出来)；
 3. 由于CacheRoute组件采用style.display:none方式隐藏，路由切换期间，本应该slide out的组件会突然消失不见，仅有新组件slide in；
 
 目前仅单独使用AnimatedRouter，存在的问题是，首页向下滚动，进入单品页，从单品页返回后，首页会回到顶部(重新创建的App组件实例，之前状态丢失)。


This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

## Available Scripts

In the project directory, you can run:

### `npm start`

Runs the app in the development mode.<br>
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

The page will reload if you make edits.<br>
You will also see any lint errors in the console.

### `npm test`

Launches the test runner in the interactive watch mode.<br>
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `npm run build`

Builds the app for production to the `build` folder.<br>
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.<br>
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### `npm run eject`

**Note: this is a one-way operation. Once you `eject`, you can’t go back!**

If you aren’t satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (Webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you’re on your own.

You don’t have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn’t feel obligated to use this feature. However we understand that this tool wouldn’t be useful if you couldn’t customize it when you are ready for it.

## Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).
