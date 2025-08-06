import {action, observable} from "mobx";
import type {CommandHistory} from "@/basic/models/command";

/**
 * `CommandStore`是一个储存`command`页面逻辑的store。
 *
 * @class CommandStore
 * @author Lignting
 */
class CommandStore {
    /**
     * 当前正在编辑或执行的命令字符串。
     */
    @observable command: string = "";
    
    /**
     * 命令历史记录数组，存储之前执行过的命令。
     */
    @observable commandHistory: CommandHistory[] = [];
    
    /**
     * 发送命令
     */
    @action send = () => {
        if (this.command.trim() === "") return;
        
        // 创建新的命令历史记录
        const newCommand: CommandHistory = {
            id: Date.now().toString(),
            message: this.command,
            from: 1 // 1表示用户发送的命令
        };
        
        // 将新命令添加到历史记录中
        this.commandHistory.push(newCommand);
        
        // 清空当前命令输入框
        this.command = "";
    }
    
    @action keyboardInput = (key: string) => {
        // 处理键盘输入
        // if (key === "enter") {
        //     this.send();
        // } else if (key === "backspace") {
        //     this.command = this.command.slice(0, -1);
        // } else if (key === "clear") {
        //     this.command = "";
        // } else {
        //     this.command += key;
        // }
        
        console.log(`Keyboard input received: ${key}`);
    }
}

export const commandStore = new CommandStore();