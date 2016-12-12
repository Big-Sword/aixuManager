


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

  | 名称                 | 描述           | 类型           | 备注                                       | 必需   
| ------------------ | ------------ | ------------ | ---------------------------------------- | ---- |
| pageIndex            | 页数         | int       |                                          | Y    |
| pageSize          |    每页数量    | int      |                                          | Y    |




``` 
get
```                   
``` 
{
  "code": 200,
  "msg": "success",
  "data": {
  "shopperInfos":[
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
     ],
     total:4
   }
}
```
##1.4（产品列表）
## http://localhost:8080/product/all
x-token

``` 
get
``` 

  | 名称                 | 描述           | 类型           | 备注                                       | 必需   
| ------------------ | ------------ | ------------ | ---------------------------------------- | ---- |
| pageIndex            | 页数         | int       |                                          | Y    |
| pageSize          |    每页数量    | int      |                                          | Y    |


``` 
{
  "code": 200,
  "msg": "success",
  "data": {
   "productInfos":[
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
  ],
  total:2
  }
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

| picUrl          |    图片   | file      |                                          | Y    |

| content        | 内容      | String         |                                          | Y

| contactWay        | 联系方式      | double         |                                          | Y

| stock        | 库存      | String         |                                          | Y

| modelType        | 型号      | String         |                                          | Y

| colourType        | 色号      | String         |                                          | Y

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

##1.8（商家登录）
##http://localhost:8080/shopper/common/login

x-token

  | 名称                 | 描述           | 类型           | 备注                                       | 必需   
| ------------------ | ------------ | ------------ | ---------------------------------------- | ---- |
| loginName            | 登录名         | string       |                                          | Y    |
| loginPassword          |    密码    | string      |                                          | Y    |


``` 
post
``` 
``` 

{
  "code": 200,
  "msg": "success",
  "data": {
    "token": "5de1233f-a6d5-4517-8baf-cf4a783f39eb",
    "loginName": "155555555_1",
    "isFirstLogin": true
  }
}
 
``` 


##1.9(更改密码)商家自己修改
## http://localhost:8080/shopper/updatePassword
x-token
  
    | 名称                 | 描述           | 类型           | 备注                                       | 必需   
 | 名称                 | 描述           | 类型           | 备注                                       | 必需   
| ------------------ | ------------ | ------------ | ---------------------------------------- | ---- |
| loginName            | 新密码         | string       |                                          | Y    |

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


##2.0(修改商家信息)
##http://localhost:8080/manager/updateShopper

   | 名称                 | 描述           | 类型           | 备注                                       | 必需   
| ------------------ | ------------ | ------------ | ---------------------------------------- | ---- |
| name            | 公司名称         | int       |                                          | N|
| address          |    公司地址    | int      |                                          | N    |
| contactName        | 联系人名称      | String         |                                          | N
| contactWay        | 联系方式      | String         |                                          | N
                      
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

 

##2.0删除商家
## http://localhost:8080/manager/delete/{id}

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
##2.1（产品图片上传）
## http://localhost:8080/product/upload
x-token

``` 
post
``` 
``` 
{
  "code": 200,
  "msg": "success",
  "data": {"picUrl":"c3389425-a808-40a2-86b9-5deaaab37f5c"}
}

```  
  