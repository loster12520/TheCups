import {observer} from "mobx-react-lite";
import styles from "./styles.module.scss";
import {commandStore} from "@/services/stores/command";

const CommandPanel = observer(() => {
    return (
        <div className={styles.commandPanel}>
            <div className={styles.commandListPanelList}>
                {
                    commandStore.commandHistory.map((value)=>{
                        return (
                            <div key={value.id} className={styles.commandListItem}>
                                <span className={value.from === 1 ? styles.userCommand : styles.botCommand}>
                                    {value.message}
                                </span>
                            </div>
                        );
                    })
                }
            </div>
            <div className={styles.commandPanel}>
                <span>{commandStore.command}</span>
            </div>
        </div>
    );
})

export {CommandPanel};