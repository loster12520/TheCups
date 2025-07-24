import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
    plugins: [react()],
    server: {
        port: 7878, // 设置开发服务器端口
        open: true, // 启动时自动打开浏览器
    }
})