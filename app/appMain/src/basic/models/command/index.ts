/**
 * 用于记录消息的类型
 *
 * @param {string} id - 消息的唯一标识符
 * @param {string} message - 消息内容
 * @param {number} from - 消息来源，0表示后端，1表示用户
 */
export interface CommandHistory {
    id: string,
    message: string,
    from: 0 | 1,
    traceId: string,
}