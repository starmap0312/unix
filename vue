# build webpack project:
  install brew: 
    /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
  install nodjs:
    brew install node
  npm install -g @vue/cli-init
  vue init webpack <proj_name>
  npm install
    install packages defined in package.json to node_modules/
  npm install --save axios vue-axios (install axios package for http requests)
  npm run dev (run the dev-server)

# other npm packages 
  install vue-cli: 
    npm install -g @vue/cli
  install vue/cli-service:
    npm install -g @vue/cli-service

# with @vue/cli-service, we can run the service or build the production package:
1) vue-cli-service serve
   starting development server (which is based on webpack-dev-server) with Hot-Module-Replacement
2) vue-cli-service build
   create production package under dist/ for deployment
   ref: check out deployment instructions at https://cli.vuejs.org/guide/deployment.html

# set up vue PROXY at development env (local)

CLIENT <---> PROXY (local, dev server) <---> SERVER (remote)

ref: https://kuro.tw/posts/2017/06/07/%E5%A6%82%E4%BD%95%E5%9C%A8-Vue-CLI-%E5%BB%BA%E7%AB%8B%E7%9A%84%E9%96%8B%E7%99%BC%E7%92%B0%E5%A2%83%E5%91%BC%E5%8F%AB%E8%B7%A8%E5%9F%9F%E9%81%A0%E7%AB%AF-RESTful-APIs/

(not working example during development)
  axios.get('http://SERVER/api').then((response) => {
    console.log(response.data)
  })
  # note: if the local service (PROXY) does not allow cross-site requests, the browser will not send out the request (raise "No Access-Control-Allow-Origin" error)
  # i.e. we have Request URL: "http://SERVER/api" but Referer: "http://PROXY/"

(solution for development)
use vue-cli's http-proxy-middleware module creates a local proxy layer, so that we can make a remote cross-site call by calling locally (PROXY)

edit: config/index.js
  proxyTable: {
    '/data': {
      target: 'http://SERVER/api/', // map all local (PROXY) calls to "/data" to remote (SERVER) calls "http://SERVER/api/"
      changeOrigin: true,           // the ensures that the backend server 'http://SERVER/api/' does not need to serve CORS headers
      pathRewrite: {
        '^/data': ''
      }
    }
  }

once the prxoy is set, we can then treat remote (SERVER) calls as local (PROXY) calls when developing the frontend js code
ex.
axios.get('/data/1').then((response) => {
  console.log(response.data)
})

when deploying the frontend js & css to the production, make sure that the production server also serves as a reverse proxy server (ex. a apache reverse proxy sever)
