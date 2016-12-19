# aixuManager
## 1 用户下单
#### 描述
*  

#### WEB API 地址
```
post
/order/ordering
```

#### 输入参数
| 名称 | 类型  | 必填 | 传参位置 | 说明
|-----|------|------|--------|--------|
|deliveryTime|long|y|json|送货时间|
|weddingTime|long|y|json|婚礼时间|
|customer|varchar|y|json|收货人|
|contact|varchar|y|json|联系方式|
|address|varchar|y|json|地址|
|orderDetailItems|List<OrderDetailItem>|y|json|商品详情 产品id和数量|

```
{
	"deliveryTime":1482128236000,
	"weddingTime":1482128236000,
	"customer":"飞",
	"contact":"18515282828",
	"address":"曹杨路",
	"orderDetailItems":[{"productId":1,"productNum":3},{"productId":6,"productNum":1}]
}
```

#### 返回参数
```
{
  "code": 200,
  "msg": "success",
  "data": {
    "orders": {
      "id": 30,
      "orderNum": "2016121914200893",
      "shopperId": 27,
      "shopperName": "gggggg",
      "orderPrice": 197.55,
      "customer": "韩若飞",
      "contact": "18515282828",
      "address": "曹杨路",
      "status": -1,
      "deliveryTime": 1482128236000,
      "weddingTime": 1482128236000,
      "orderTime": 1482128408000,
      "deliveryTimeS": null,
      "weddingTimeS": null,
      "orderTimeS": null,
      "deliveryTimeE": null,
      "weddingTimeE": null,
      "orderTimeE": null,
      "isDelete": 0,
      "updatedAt": 1482128081000
    },
    "orderDetails": [
      {
        "id": 15,
        "orderId": 30,
        "productId": 1,
        "productCount": 3,
        "name": "真假",
        "picUrl": "http:hippo",
        "content": "大象",
        "price": 24.85,
        "modelType": "来来来",
        "colourType": "白色",
        "isDelete": 0,
        "updatedAt": 1482128081000
      },
      {
        "id": 16,
        "orderId": 30,
        "productId": 6,
        "productCount": 1,
        "name": "程序安师大",
        "picUrl": "地方",
        "content": "地方",
        "price": 123,
        "modelType": "地方",
        "colourType": "发",
        "isDelete": 0,
        "updatedAt": 1482128081000
      }
    ]
  }
}
```
## 2 用户订单
#### 描述
*  用邵总的分页

#### WEB API 地址
```
post
/order/myorder
```

#### 输入参数
| 名称 | 类型  | 必填 | 传参位置 | 说明
|-----|------|------|--------|--------|


#### 返回参数
```
```
## 3 订单详情
#### 描述
*  

#### WEB API 地址
```
post
order/orderdetail/{id}
```

#### 输入参数
| 名称 | 类型  | 必填 | 传参位置 | 说明
|-----|------|------|--------|--------|
|id|long|y|url|订单号|

#### 返回参数
```
{
  "code": 200,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "orderId": 18,
      "productId": 1,
      "productCount": 3,
      "name": "真假",
      "picUrl": "http:hippo",
      "content": "大象",
      "price": 24.85,
      "modelType": "来来来",
      "colourType": "白色",
      "isDelete": 0,
      "updatedAt": 1481128482000
    },
    {
      "id": 2,
      "orderId": 18,
      "productId": 6,
      "productCount": 2,
      "name": "程序安师大",
      "picUrl": "地方",
      "content": "地方",
      "price": 123,
      "modelType": "地方",
      "colourType": "发",
      "isDelete": 0,
      "updatedAt": 1481128482000
    }
  ]
}
```
## 4 确认订单
#### 描述
*  

#### WEB API 地址
```
post
order/ordercheck/{id}
```

#### 输入参数
| 名称 | 类型  | 必填 | 传参位置 | 说明
|-----|------|------|--------|--------|
|id|long|y|url|订单号|

#### 返回参数
```
{
  "code": 200,
  "msg": "success",
  "data": true
}
```

