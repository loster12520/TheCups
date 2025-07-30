import {useEffect, useState} from "react"
import styles from "./styles.module.scss"

const keyboardKeys: string[][] = [
    ["esc", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "-", "="],
    ["tab", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "[", "]", "\\"],
    ["Capslocks", "A", "S", "D", "F", "G", "H", "J", "K", "L", ";", "'", "enter"],
    ["shift", "Z", "X", "C", "V", "B", "N", "M", ",", ".", "/", "shift"]
]

const KeyboardPanel = () => {
    const [activeKey, setActiveKey] = useState<string | null>(null)
    const handleKeyDown = (e: KeyboardEvent) => {
        setActiveKey(e.key.toUpperCase())
    }
    const handleKeyUp = () => {
        setActiveKey(null)
    }
    
    // Handle keydown and keyup events to update the active key
    useEffect(() => {
        window.addEventListener("keydown", handleKeyDown)
        window.addEventListener("keyup", handleKeyUp)
        return () => {
            window.removeEventListener("keydown", handleKeyDown)
            window.removeEventListener("keyup", handleKeyUp)
        }
    }, [])
    
    return (
        <div className={styles.keyboardPanel}>
            {keyboardKeys.map((row, rowIndex) => (
                <div key={rowIndex} className={styles.keyboardRow}>
                    {row.map((key) => (
                        <div
                            key={key}
                            className={
                                activeKey === key
                                    ? styles.activeKey
                                    : styles.key
                            }
                        >
                            {key}
                        </div>
                    ))}
                </div>
            ))}
        </div>
    )
}

export {KeyboardPanel}