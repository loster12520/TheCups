# websocket接口

---

## command

> **URL**: `/command/test`
> 
### 客户端消息：

#### auth

认证消息，必须在连接建立后发送

30s内未发生认证消息，后端会主动断开连接

需要从`/users/login`接口获取token

```json
{
  "type": "auth",
  "timeStamp": 1700000000,
  "data": "your-token",
  "traceId": ""
}
```

#### message

发送命令给后端，让后端执行

```json
{
  "type": "message",
  "timeStamp": 1700000000,
  "data": "1111",
  "traceId": "xxxx"
}
```

#### heartbeat

定时发送心跳包，保持连接

要求每隔30秒发送一次

```json
{
  "type": "heartbeat",
  "timeStamp": 1700000000,
  "data": "",
  "traceId": "xxxx"
}
```

### 服务端消息：

#### auth

认证结果，traceId和传入的traceId保持一致

```json
{
  "type": "auth",
  "timeStamp": 1700000000,
  "data": "success",
  "traceId": ""
}
```

#### message

后端返回的命令执行结果

会不断传递生成的消息片段，需要前端进行拼接

每次发送的时间间隔是0.1s，如果后端检测到没有新的字符需要输出则不会发送命令

通过 `traceId` 来区分不同的命令

```json
{
  "type": "message",
  "timeStamp": 1700000000,
  "data": "2222",
  "traceId": "xxxx"
}
```

#### heartbeat

后端的心跳回包

会复制前端发送过来心跳的 `traceId`到回包中

```json
{
  "type": "heartbeat",
  "timeStamp": 1700000000,
  "data": "",
  "traceId": ""
}
```

#### error

错误信息

```json
{
  "type": "error",
  "timeStamp": 1700000000,
  "data": "error message",
  "traceId": ""
}
```

### 重连逻辑

后端会维护一个计时器，如果1分钟内没有收到前端的心跳，则判定为连接断开，关闭连接

前端在连接断开后，可以每隔5秒尝试重连一次，直到连接成功为止。

连接成功后，后端会重新发送正在执行的命令中已输出的部分，并重启计时器。

若需要获取历史消息，则需要重新获取历史命令列表，并重新发送命令

如果前端3分钟内未重连，则后端会放弃该连接，释放资源

[//]: # (TODO 历史命令列表位置)