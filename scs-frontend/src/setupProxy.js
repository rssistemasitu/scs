const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function (app) {
  app.use(
    '/api/v1', // O caminho base da API que você deseja encaminhar (altere para o caminho correto)
    createProxyMiddleware({
      target: 'http://localhost:8989', // URL da sua API Spring Boot
      changeOrigin: true,
    })
  );
};
