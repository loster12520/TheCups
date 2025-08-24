const base_url = "ws://127.0.0.1:7868/command/test";

type MessageType = 'message' | 'heartbeat';

interface CommandMessage {
    type: MessageType;
    timeStamp: number;
    data: string;
    traceId: string;
}

export class WebSocketManager {
    private ws: WebSocket | null = null;
    private readonly baseUrl: string;
    private heartbeatInterval: any = null;
    private genTraceId = () => crypto.randomUUID();
    private readonly onMessage: (type: string, data: string, traceId: string) => void;
    
    constructor(onMessage: (type: string, data: string, traceId: string) => void, baseUrl?: string) {
        this.baseUrl = baseUrl ?? base_url;
        this.onMessage = onMessage;
        this.connect();
        this.startHeartbeat();
    }
    
    private connect() {
        this.ws = new WebSocket(this.baseUrl);
        this.ws.onmessage = (event) => {
            try {
                const msg = JSON.parse(event.data) as CommandMessage;
                this.onMessage(msg.type, msg.data, msg.traceId);
            } catch (e) {
                // 可根据需要处理解析错误
            }
        };
        this.ws.onclose = () => {
            // 这里只做简单处理，自动重连暂不实现
            this.ws = null;
        };
    }
    
    private startHeartbeat() {
        this.heartbeatInterval = setInterval(() => {
            this.sendHeartbeat();
        }, 30000);
    }
    
    private sendHeartbeat() {
        this.sendRaw({
            type: 'heartbeat',
            timeStamp: Date.now(),
            data: '',
            traceId: this.genTraceId(),
        });
    }
    
    private sendRaw(msg: CommandMessage): boolean {
        if (this.ws && this.ws.readyState === WebSocket.OPEN) {
            this.ws.send(JSON.stringify(msg));
            return true;
        }
        return false;
    }
    
    public send(command: string): boolean {
        return this.sendRaw({
            type: 'message',
            timeStamp: Date.now(),
            data: command,
            traceId: this.genTraceId(),
        });
    }
    
    public close() {
        if (this.heartbeatInterval) clearInterval(this.heartbeatInterval);
        if (this.ws) this.ws.close();
    }
}