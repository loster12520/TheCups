import {useEffect, useState} from "react"
import styles from "./styles.module.scss"
import classNames from "classnames";
import {commandStore} from "@/services/stores/command";
import {keyListenerStore} from "@/services/stores/command/key-listener";
import { observer } from "mobx-react-lite";

/**
 * 键盘按键的类型定义
 *
 * 键盘按键对象包含以下属性：
 * - `key:string` - 按键的唯一标识符
 * - `label:string` - 按键显示的标签
 * - `shifted:string?` - Shift键按下时的字符
 * - `type:string?` - 键的类型，默认为普通键，可选为 "function" (功能键) 或 "space" (空格键)
 */
interface KeyboardKey {
    key: string;
    label: string;
    shifted?: string;
    type?: string;
}

/**
 * 键盘按键的二维数组
 * 每个子数组代表键盘的一行，包含多个键盘按键对象
 */
const keyboardKeys: KeyboardKey[][] = [
    [
        {key: "esc", label: "Esc", type: "function"},
        {key: "1", label: "1", shifted: "!"},
        {key: "2", label: "2", shifted: "@"},
        {key: "3", label: "3", shifted: "#"},
        {key: "4", label: "4", shifted: "$"},
        {key: "5", label: "5", shifted: "%"},
        {key: "6", label: "6", shifted: "^"},
        {key: "7", label: "7", shifted: "&"},
        {key: "8", label: "8", shifted: "*"},
        {key: "9", label: "9", shifted: "("},
        {key: "0", label: "0", shifted: ")"},
        {key: "-", label: "-", shifted: "_"},
        {key: "=", label: "=", shifted: "+"},
        {key: "backspace", label: "Backspace", type: "function"}
    ],
    [
        {key: "tab", label: "Tab", type: "function"},
        {key: "a", label: "Q"},
        {key: "w", label: "W"},
        {key: "e", label: "E"},
        {key: "r", label: "R"},
        {key: "T", label: "T"},
        {key: "Y", label: "Y"},
        {key: "U", label: "U"},
        {key: "I", label: "I"},
        {key: "O", label: "O"},
        {key: "P", label: "P"},
        {key: "[", label: "[", shifted: "{"},
        {key: "]", label: "]", shifted: "}"},
        {key: "\\", label: "\\", shifted: "|", type: "function"}
    ],
    [
        {key: "CapsLock", label: "Caps", type: "function"},
        {key: "A", label: "A"},
        {key: "S", label: "S"},
        {key: "D", label: "D"},
        {key: "F", label: "F"},
        {key: "G", label: "G"},
        {key: "H", label: "H"},
        {key: "J", label: "J"},
        {key: "K", label: "K"},
        {key: "L", label: "L"},
        {key: ";", label: ";", shifted: ":"},
        {key: "'", label: "'", shifted: "\""},
        {key: "enter", label: "Enter", type: "function"}
    ],
    [
        {key: "shift", label: "Shift", type: "function"},
        {key: "Z", label: "Z"},
        {key: "X", label: "X"},
        {key: "C", label: "C"},
        {key: "V", label: "V"},
        {key: "B", label: "B"},
        {key: "N", label: "N"},
        {key: "M", label: "M"},
        {key: ",", label: ",", shifted: "<"},
        {key: ".", label: ".", shifted: ">"},
        {key: "/", label: "/", shifted: "?"},
        {key: "shift_r", label: "Shift", type: "function"}
    ],
    [
        {key: "ctrl", label: "Ctrl", type: "function"},
        {key: "win", label: "Win", type: "function"},
        {key: "alt", label: "Alt", type: "function"},
        {key: "space", label: "", type: "space"},
        {key: "alt_r", label: "Alt", type: "function"},
        {key: "win_r", label: "Win", type: "function"},
        {key: "menu", label: "Menu", type: "function"},
        {key: "ctrl_r", label: "Ctrl", type: "function"}
    ]
]

/**
 * 键盘面板组件
 * 显示一个虚拟键盘，响应键盘事件并高亮显示当前按下的键
 *
 * @returns {JSX.Element} 键盘面板组件
 * @author Lignting
 */
const KeyboardPanel = observer(() => {
    useEffect(() => {
        keyListenerStore.init(key => commandStore.keyboardInput(key))
        console.log("KeyboardPanel mounted, initializing key listener")
        return () => {
            keyListenerStore.stop()
        }
    }, [])
    
    return (
        <div className={styles.keyboardPanel}>
            {keyboardKeys.map((row, rowIndex) => (
                <div key={rowIndex} className={styles.keyboardRow}>
                    {row.map((keyObj) => (
                        <div
                            key={keyObj.key}
                            className={classNames([
                                keyListenerStore.key.toLowerCase() === keyObj.key.toLowerCase() ? styles.activeKey : styles.key,
                                (!keyObj.type || keyObj.type === "normal") && styles.normalKey,
                                keyObj.type === "function" && styles.functionKey,
                                keyObj.type === "space" && styles.spaceKey,
                                keyObj.shifted && styles.shiftedKey,
                                // 功能键高亮
                                (keyObj.key === "shift" && keyListenerStore.isShifted) && styles.activeKey,
                                (keyObj.key === "ctrl" && keyListenerStore.isCtrlPressed) && styles.activeKey,
                                (keyObj.key === "alt" && keyListenerStore.isAltPressed) && styles.activeKey,
                                (keyObj.key === "CapsLock" && keyListenerStore.isCapsLock) && styles.activeKey,
                            ])}
                        >
                            <span>{keyObj.shifted && keyObj.shifted}</span>
                            <span>{keyObj.label}</span>
                        </div>
                    ))}
                </div>
            ))}
        </div>
    )
})

export {KeyboardPanel}