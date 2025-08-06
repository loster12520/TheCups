import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react'
import * as path from 'path'

export default defineConfig({
    base: './', // 设置基础路径为当前目录
    plugins: [react({
        babel: {
            plugins: [
                ['@babel/plugin-proposal-decorators', { legacy: true }],
                ['@babel/plugin-proposal-class-properties', { loose: true }],
            ]
        }
    })],
    server: {
        port: 7878, // 设置开发服务器端口
    },
    resolve: {
        alias: {
            '@': path.resolve(__dirname, './src')
        }
    }
})