const { createProxyMiddleware } = require('http-proxy-middleware');
// const proxy = require('http-proxy-middleware');

// https://facebook.github.io/create-react-app/docs/proxying-api-requests-in-development#configuring-the-proxy-manually

const URI = process.env.REVERSE_PROXY_URI || 'http://localhost:9090';
const WS = process.env.REVERSE_PROXY_WS || 'ws://localhost:9090';

module.exports = app => {
    app.use(
        createProxyMiddleware('/api', {
            target: URI,
            changeOrigin: true,
        })
    );
    app.use(
        createProxyMiddleware('/rest', {
            target: URI,
            changeOrigin: true,
        })
    );
    app.use(
        createProxyMiddleware('/ws', {
            ws: true,
            target: WS,
        })
    );
};
