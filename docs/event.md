# Event

## 简介

事件设计：

- EventType，定义事件类型



## FxEvent

![image-20250722095136000](./images/image-20250722095136000.png)

`FxEvent` 继承 `java.util.EventObject`，在其基础上添加了事件类型和优先级：

- `EventType`
- `EventPriority`