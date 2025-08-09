import {action, observable} from "mobx";

/**
 * `KeyListenerStore`是一个用于监听键盘事件的store。
 * 它可以处理按键按下和释放事件，并提供当前按下的键和状态。
 */
class KeyListenerStore {
    /**
     * 当前按下的键
     */
    @observable key: string = "";
    @observable isShifted: boolean = false;
    @observable isCapsLock: boolean = false;
    // TODO 目前还没有做快捷键，之后计划添加
    @observable isCtrlPressed: boolean = false;
    @observable isAltPressed: boolean = false;
    
    /**
     * 输入函数，当按下键时调用
     * 如果没有提供，则不执行任何操作
     */
    private inputFunction: ((key: string) => void) | null = null;
    
    /**
     * 初始化键盘监听器
     * @param inputFunction 可选的输入函数，当按下键时调用
     */
    @action init = (inputFunction?: (key: string) => void) => {
        window.addEventListener("keydown", this.handleKeyDown);
        window.addEventListener("keyup", this.handleKeyUp);
        if (inputFunction)
            this.inputFunction = inputFunction;
        else
            this.inputFunction = null;
    }
    /**
     * 停止监听键盘事件
     */
    @action stop = () => {
        this.key = "";
        this.isShifted = false;
        this.isCapsLock = false;
        this.isCtrlPressed = false;
        this.isAltPressed = false;
        window.removeEventListener("keydown", this.handleKeyDown);
        window.removeEventListener("keyup", this.handleKeyUp);
    }
    
    /**
     * 处理键盘按下事件
     * @param event 键盘事件对象
     */
    private handleKeyDown = (event: KeyboardEvent) => {
        // 过滤按下的功能键
        if (event.key === "Shift") {
            this.isShifted = true;
        } else if (event.key === "CapsLock") {
            this.isCapsLock = !this.isCapsLock;
        } else if (event.key === "Control") {
            this.isCtrlPressed = true;
        } else if (event.key === "Alt") {
            this.isAltPressed = true;
        } else {
            // TODO 处理快捷键部分
            this.key = event.key;
            if (this.inputFunction) {
                const inputKey = this.isCapsLock ?
                    (this.isShifted ? event.key.toLowerCase() : event.key.toUpperCase()) :
                    (this.isShifted ? event.key.toUpperCase() : event.key.toLowerCase());
                this.inputFunction(inputKey);
            }
        }
    }
    
    /**
     * 处理键盘按键释放事件
     * @param event 键盘事件对象
     */
    private handleKeyUp = (event: KeyboardEvent) => {
        // 处理按键释放事件
        if (event.key === "Shift") {
            this.isShifted = false;
        } else if (event.key === "CapsLock") {
            // 不做任何操作，因为CapsLock状态已经在handleKeyDown中处理
        } else if (event.key === "Control") {
            this.isCtrlPressed = false;
        } else if (event.key === "Alt") {
            this.isAltPressed = false;
        } else {
            // 清除当前按下的键
            this.key = "";
        }
    }
}

export const keyListenerStore = new KeyListenerStore();