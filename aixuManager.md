


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
{
  "code": 200,
  "msg": "success",
  "data": {
    "token": "354553f2-85f8-4fa2-91f4-cf1fbb56273f",
    "loginName": "aixu"
  }
}
```
##1.2（新建商家）
## http://localhost:8080/manager/createShopper
x-token


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
{
  "code": 200,
  "msg": "success",
  "data": {
    "userName": "sh_4@AIXU",
    "password": "DyTpTm2$"
  }
}
```


##1.3（商家列表）
## http://localhost:8080/manager/queryAll
x-token

``` 
get
```                   
``` 
{
  "code": 200,
  "msg": "success",
  "data": [
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
    },
        {
      "id": 8,
      "name": "上海",
      "address": "上海",
      "contactName": "eeee",
      "contactWay": "155555555",
      "loginName": "sh_1@AIXU",
      "loginPassword": "e0923945f318f0d9a7b9de93c9cc799b",
      "isDelete": 0,
      "createdAt": 1480822171000,
      "updatedAt": 1480822171000
    },
    {
      "id": 9,
      "name": "啊大地",
      "address": "123ADS",
      "contactName": "SD安师大",
      "contactWay": "123124",
      "loginName": "add_1@AIXU",
      "loginPassword": "9aece3321b0357886b35e694abd95ed7",
      "isDelete": 0,
      "createdAt": 1480822180000,
      "updatedAt": 1480822180000
    },
    {
      "id": 10,
      "name": "安师大",
      "address": "123",
      "contactName": "安师大",
      "contactWay": "安师大",
      "loginName": "asd_1@AIXU",
      "loginPassword": "138d7cf77b61a748fd11d3254896435b",
      "isDelete": 0,
      "createdAt": 1480822239000,
      "updatedAt": 1480822239000
    }
     ]
}```
##1.4（产品列表）
## http://localhost:8080/product/all
x-token
``` 
get
``` 

``` 
{
  "code": 200,
  "msg": "success",
  "data": [
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
}
```

##1.5（产品删除）
## http://localhost:8080/product/delete/{id}
x-token
``` 
post
``` 

``` 
{
  "code": 200,
  "msg": "success",
  "data": true
}
```

##1.6（产品创建或更新）
## http://localhost:8080/product/saveOrUpdate
x-token
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
{
  "code": 200,
  "msg": "success",
  "data": true
}
```
##1.7（产品信息）
## http://localhost:8080/product/get/{id}
x-token
``` 
post
``` 

``` 
{
  "code": 200,
  "msg": "success",
  "data": {
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
  }
}

```

  
  
  
  
  
  