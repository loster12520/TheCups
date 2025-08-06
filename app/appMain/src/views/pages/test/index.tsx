import {useEffect, useRef} from "react";
import styles from "./styles.module.scss"

const Test = () => {
    
    const draw = useRef<HTMLCanvasElement | null>(null);
    useEffect(() => {
        const ctx = draw.current?.getContext("2d") || null
        if (!ctx)
            return;
        else
            ctx.clearRect(0, 0, 150, 150);
        
        ctx.beginPath();
        ctx.moveTo(75, 25);
        ctx.quadraticCurveTo(25, 25, 25, 62.5);
        ctx.quadraticCurveTo(25, 100, 50, 100);
        ctx.quadraticCurveTo(50, 120, 30, 125);
        ctx.quadraticCurveTo(60, 120, 65, 100);
        ctx.quadraticCurveTo(125, 100, 125, 62.5);
        ctx.quadraticCurveTo(125, 25, 75, 25);
        ctx.stroke();
    }, [])
    
    return (
        <div>
            <h1>Test Page</h1>
            <p>This is a test page.</p>
            <canvas ref={draw} width="150" height="150"></canvas>
        </div>
    );
}

export default Test;