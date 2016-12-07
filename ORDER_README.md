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
|orderDetailItems|List<OrderDetailItem>|y|json|商品详情 产品id和数量|

```
{
	"deliveryTime":1454422271,
	"weddingTime":1454422271,
	"orderDetailItems":[{"productId":0,"productNum":3},{"productId":1,"productNum":3}]
}
```

#### 返回参数
```
{
  "code": 200,
  "msg": "success",
  "data": true
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

