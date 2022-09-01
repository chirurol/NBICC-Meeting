// vue.config.js
module.exports = {
    chainWebpack: config => {
        config
            .plugin('html')
            .tap(args => {
                args[0].title= '宁波文化广场业务系统'
                return args
            })
    }
}
