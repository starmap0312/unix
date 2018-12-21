# requirement
  install brew: 
    /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
  install nodjs:
    brew install node
  install npm packages:
    npm install -g @vue/cli
    npm install -g @vue/cli-service
  or inside a vue project, i.e. npm init, install packages defined in package.json
    npm install

# @vue/cli-service
1) vue-cli-service serve
   tarting development server (which is based on webpack-dev-server) with Hot-Module-Replacement
2) vue-cli-service build
   create production package under dist/ for deployment
   ref: check out deployment instructions at https://cli.vuejs.org/guide/deployment.html
