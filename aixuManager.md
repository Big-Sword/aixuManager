


##1.1（后台登录）
## http://localhost:8080/manager/common/login

``` 
post
```



| 名称                 | 描述           | 类型           | 备注                                       | 必需   |
| ------------------ | ------------ | ------------ | ---------------------------------------- | ---- |
| name            |  名称        | int       |                                          | Y    |
| password          |    密码    | int      |                                          | Y    |
                                     

       
                      
    
``` 
true 代表登录成功
false 代表登录失败
```
##1.2（新建商家）
## http://localhost:8080/manager/createShopper


 ``` 
post
```
  
  
  | 名称                 | 描述           | 类型           | 备注                                       | 必需   
| ------------------ | ------------ | ------------ | ---------------------------------------- | ---- |
| name            | 公司名称         | int       |                                          | Y    |
| address          |    公司地址    | int      |                                          | Y    |
| contactName        | 联系人名称      | String         |                                          | Y
| contactWay        | 联系方式      | String         |                                          | Y
                       
   
``` 
true 代表创建成功
false 代表创建失败
```


##1.3（商家列表）
## http://localhost:8080/manager/queryAll
商家列表

``` 
get
```                   
``` 
[
  {
    "id": 1,
    "name": "上海",
    "address": "上海",
    "contactName": "wj",
    "contactWay": "155555555",
    "loginName": "sh_1@AIXU",
    "loginPassword": "443cf80ad049aad9ca81f92a61a1a0f0",
    "isDelete": 0,
    "createdAt": 1480772189000,
    "updatedAt": 1480772189000
  }
]
```
##1.4（产品列表）
## http://localhost:8080/product/all
``` 
get
``` 

``` 
[
  {
    "id": 2,
    "name": "假的",
    "picUrl": "http:hippo",
    "content": "大象",
    "price": 24.85,
    "stock": 0,
    "modelType": "来来来",
    "colourType": "白色",
    "remark": "无",
    "isDelete": 0,
    "createdAt": 1480744527000,
    "updatedAt": 1480766165000
  },
  {
    "id": 3,
    "name": "真假",
    "picUrl": "http:hippo",
    "content": "大象",
    "price": 24.85,
    "stock": 0,
    "modelType": "来来来",
    "colourType": "白色",
    "remark": "无",
    "isDelete": 0,
    "createdAt": 1480769581000,
    "updatedAt": 1480769581000
  }
]
```

##1.5（产品删除）
## http://localhost:8080/product/delete/{id}
``` 
post
``` 

``` 
true 代表删除成功
false 代表删除失败
```

##1.6（产品创建或更新）
## http://localhost:8080/product/saveOrUpdate
``` 
post
``` 
  | 名称                 | 描述           | 类型           | 备注                                       | 必需   
| ------------------ | ------------ | ------------ | ---------------------------------------- | ---- |
| name            | 产品名称         | string       |                                          | Y    |
| picUrl          |    图片   | string      |                                          | Y    |
| content        | 内容      | String         |                                          | Y
| contactWay        | 联系方式      | double         |                                          | Y
| stock        | 库存      | String         |                                          | Y
| modelType        | 型号      | String         |                                          | Y
| colourType        | 色号      | String         |                                          | Y
| remark        | 描述      | String         |                                          | Y

id: 更新时需要 传递  
     

``` 
true 代表创建或更新成功
false 代表创建或更新失败
```
##1.7（产品信息）
## http://localhost:8080/product/get/{id}
``` 
post
``` 

``` 
{
    "id": 3,
    "name": "真假",
    "picUrl": "http:hippo",
    "content": "大象",
    "price": 24.85,
    "stock": 0,
    "modelType": "来来来",
    "colourType": "白色",
    "remark": "无",
    "isDelete": 0,
    "createdAt": 1480769581000,
    "updatedAt": 1480769581000
}

```

  
  
  
  
  
  