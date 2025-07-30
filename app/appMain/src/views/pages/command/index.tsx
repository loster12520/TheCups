import styles from "./styles.module.scss"
import {KeyboardPanel} from "@/views/pages/command/components/keyboard-panel";

const CommandPage = () => {
    return (
        <div className={styles.commandPage}>
            <div className={styles.leftPanel}>
                <div className={styles.staticPanel}>
                    <div className={styles.blankPanel}>立绘区域</div>
                    <div className={styles.operationPanel}>操作区域</div>
                </div>
                <div className={styles.folderPanel}>文件夹区域</div>
            </div>
            <div className={styles.rightPanel}>
                <div className={styles.cmdPanel}>cmd区域</div>
                <div className={styles.keyboardPanel}>
                    <KeyboardPanel/>
                </div>
            </div>
        </div>
    );
}

export {CommandPage};