import {useEffect, useState, useRef} from "react";
import styles from "./styles.module.scss"

const Test = () => {
    const width = 500
    const draw = useRef<HTMLCanvasElement | null>(null);
    const [mousePos, setMousePos] = useState({x: width / 2, y: width / 2});
    
    // 监听鼠标移动
    useEffect(() => {
        const handleMouseMove = (e: MouseEvent) => {
            const canvas = draw.current;
            if (!canvas) return;
            
            const rect = canvas.getBoundingClientRect();
            setMousePos({
                x: e.clientX - rect.left,
                y: e.clientY - rect.top
            });
        };
        
        window.addEventListener('mousemove', handleMouseMove);
        return () => window.removeEventListener('mousemove', handleMouseMove);
    }, []);
    
    // 绘制函数
    useEffect(() => {
        const ctx = draw.current?.getContext("2d") || null;
        if (!ctx) return;
        
        // 清除画布
        ctx.clearRect(0, 0, width, width);
        
        // 绘制黄色脸部
        ctx.beginPath();
        ctx.arc(width / 2, width / 2, 100, 0, Math.PI * 2);
        ctx.fillStyle = '#FFE87C';
        ctx.fill();
        
        // 绘制白色眼睛
        ctx.beginPath();
        ctx.arc(width / 2 - 30, width / 2 - 20, 20, 0, Math.PI * 2);
        ctx.arc(width / 2 + 30, width / 2 - 20, 20, 0, Math.PI * 2);
        ctx.fillStyle = 'white';
        ctx.fill();
        
        // 计算眼球位置
        const leftEyeCenter = {x: width / 2 - 30, y: width / 2 - 20};
        const rightEyeCenter = {x: width / 2 + 30, y: width / 2 - 20};
        const maxDistance = 8; // 眼球最大移动距离
        
        // 计算左眼球位置
        const leftAngle = Math.atan2(mousePos.y - leftEyeCenter.y, mousePos.x - leftEyeCenter.x);
        const leftEyeX = leftEyeCenter.x + Math.cos(leftAngle) * Math.min(maxDistance,
            Math.hypot(mousePos.x - leftEyeCenter.x, mousePos.y - leftEyeCenter.y) * 0.1);
        const leftEyeY = leftEyeCenter.y + Math.sin(leftAngle) * Math.min(maxDistance,
            Math.hypot(mousePos.x - leftEyeCenter.x, mousePos.y - leftEyeCenter.y) * 0.1);
        
        // 计算右眼球位置
        const rightAngle = Math.atan2(mousePos.y - rightEyeCenter.y, mousePos.x - rightEyeCenter.x);
        const rightEyeX = rightEyeCenter.x + Math.cos(rightAngle) * Math.min(maxDistance,
            Math.hypot(mousePos.x - rightEyeCenter.x, mousePos.y - rightEyeCenter.y) * 0.1);
        const rightEyeY = rightEyeCenter.y + Math.sin(rightAngle) * Math.min(maxDistance,
            Math.hypot(mousePos.x - rightEyeCenter.x, mousePos.y - rightEyeCenter.y) * 0.1);
        
        // 绘制黑色眼球
        ctx.beginPath();
        ctx.arc(leftEyeX, leftEyeY, 8, 0, Math.PI * 2);
        ctx.arc(rightEyeX, rightEyeY, 8, 0, Math.PI * 2);
        ctx.fillStyle = 'black';
        ctx.fill();
        
        // 绘制棕色微笑
        ctx.beginPath();
        ctx.arc(width / 2, width / 2, 60, 0.2 * Math.PI, 0.8 * Math.PI);
        ctx.strokeStyle = '#8B4513';
        ctx.lineWidth = 3;
        ctx.stroke();
    }, [mousePos]);
    
    return (
        <div>
            <h1>Test Page</h1>
            <p>This is a test page.</p>
            <canvas ref={draw} width={`${width}`} height={`${width}`}></canvas>
        </div>
    );
}

export default Test;