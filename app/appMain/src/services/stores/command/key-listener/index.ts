import {action, observable} from "mobx";

class KeyListenerStore {
    @observable key: string = "";
    @observable isShifted: boolean = false;
    @observable isCapsLock: boolean = false;
    @observable isCtrlPressed: boolean = false;
    @observable isAltPressed: boolean = false;
    
    
}

export const keyListenerStore = new KeyListenerStore();