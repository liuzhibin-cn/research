const path = require('path');
const { injectBabelPlugin } = require('react-app-rewired');

function resolve(dir) {
    return path.join(__dirname, '.', dir)
}

module.exports = function override(config, env) {
    config.resolve.alias = {
        '@': resolve('src')
    }
    config = injectBabelPlugin(['import', { libraryName: 'antd-mobile', style: 'css' }], config);
    return config;
};