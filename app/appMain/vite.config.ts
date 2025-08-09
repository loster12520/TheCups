import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react'
import * as path from 'path'  // 添加这行

export default defineConfig({
    base: './', // 设置基础路径为当前目录
    plugins: [react()],
    server: {
        port: 7878, // 设置开发服务器端口
        open: true, // 启动时自动打开浏览器
    },
    resolve: {
        alias: {
            '@': path.resolve(__dirname, './src')
        }
    }
})