# 薪水支付系统

## 1.问题描述

下面是薪水支付系统的需求描述（与ASD相比进行了简化）：
（1）有些雇员完全以月薪进行支付。在他们的雇员记录中有一个月薪字段。
（2）有些雇员是钟点工。按照他们雇员记录中每小时报酬数和他们的工作时间支付薪水。他们每天会提交工作时间卡，其中记录了日期和工作小时数。如果他们每天工作超过8小时，那么超过的部分会按照正常报酬的1.5倍支付薪水。
（3）对于一些带薪雇员，会根据他们的销售情况，支付一定数量的酬金。他们会提交销售凭条，其中记录了日期和销售金额。在他们的雇员记录中有一个酬金报酬字段。
（4）雇员可以选择支付方式。可以选择把支付支票邮寄到他们指定的邮政地址；也可以把支票保存在出纳人员那里随时支取；或者要求将薪水直接存入他们指定的银行账户。
（5）薪水支付程序于每月发薪日运行一次，为相应的雇员支付薪水。

## 2.需求分析

系统用例图

```plantuml
left to right direction

actor User as u

usecase "添加雇员" as uc1
usecase "删除雇员" as uc2
usecase "登记时间卡" as uc3
usecase "登记销售凭条" as uc4
usecase "更新雇员明细" as uc5
usecase "发薪日" as uc6

u --> uc1
u --> uc2
u --> uc3
u --> uc4
u --> uc5
u --> uc6
```

### 用例1：增加雇员

使用AddEmp操作(transaction)增加新雇员。该操作包含有分配的雇员号、雇员的名字及其地址。该操作有3种形式：
AddEmp EmpId “name” “address” H hourly-rate
AddEmp EmpId “name" “address” S monthly-salary
AddEmp EmpId “name” “address” C monthly-salarycommosion-rate
雇员记录是根据对应字段的值来创建的。
异常情况1：描述操作的结构有错误。
如果描述操作的结构不正确，会打印一条错误消息，不进行任何处理。

### 用例2：删除雇员

使用DelEmp操作来删除雇员。操作形式：
DelEmp EmpId
当执行该操作时，会删除对应的雇员记录。
异常情况1：无效或者未知的EmpId。
如果EmpId字段不具有正确的结构，或者它没有引用到一条有效的雇员记录，会打印一条错误消息，不进行其他处理。

### 用例3：登记时间卡

执行TimeCard操作时，系统会创建一条时间卡记录，并把该记录和对应的雇员记录关联起来。
TimeCard EmpId date hours
异常情况1：所选择的雇员不是钟点雇员。
系统会打印一条错误信息，并且不进行进一步的处理。
异常情况2：描述操作的结构中有错误。
系统会打印一条错误信息，不进行进一步的处理。

### 用例4：登记销售凭条

执行SalesReceipt操作时，系统会创建一条新的销售凭条记录，并把该记录和相应的应支付酬金的雇员关联起来。
SalesReceipt EmpId date amount
异常情况1：所选择的雇员不是应该支付酬金的。
系统会打印一条错误消息，不进行进一步的处理。
异常情况2：描述操作的结构中有错误。
系统会打印一条错误信息，不进行进一步的处理。

### 用例5：更改雇员明细

执行ChgEmp操作时，系统会更改对应雇员记录的详细信息之一。该操的几种操作形式如下：
ChgEmp EmpId name “name”	更改雇员名
ChgEmp EmpId address “address”	更改雇员地址
ChgEmp EmpId hourly hourly-rate	更改每小时报酬
ChgEmp EmpId salaried salary	更改薪水
ChgEmp EmpId commissioned salary rate	更改酬金
ChgEmp EmpId hold	持有支票
ChgEmp EmpId direct “bank” “account”	直接存款
ChgEmp EmpId mail “address”	邮寄支p
异常情况：操作错误。
如果描述操作的结构不正确，或者EmpId没有引用到真正的雇员，那么打印一条错误信息，不进行进一步的处理。

### 用例6：发薪日

执行Payday操作时，系统计算所有雇员的薪金，并根据他们所选择的支付方式对他们进行支付。
Payday

## 系统设计

```plantuml

abstract class PaymentClassification
abstract class PaymentMethod

class Employee{
    - empId : int
    - name : String
    - address : PaymentClassification
    - paymentMethod : PaymentMethod
}

PaymentClassification -* Employee
Employee *- PaymentMethod

class HourlyClassification extends PaymentClassification{
    - hourlyRate : double
}
class SalariedClassification extends PaymentClassification{
    - salary : double
}
class CommissionedClassification extends PaymentClassification{
    - salary : double
    - commissionRate : double
}
class TimeCard{
    - date : String
    - hours : double
}

HourlyClassification *-- TimeCard

class SalesRecipt{
    - date : String
    - amount : double
}

CommissionedClassification *-- SalesRecipt

class HoldMethod extends PaymentMethod
class MailMethod extends PaymentMethod{
    - address : String
}
class DirectMethod extends PaymentMethod{
    - bank : String
    - account : String
}
```
