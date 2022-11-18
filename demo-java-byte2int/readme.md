## byte数组 和 int相互转换
在网络层进行编码时，需要对byte数组进行各种操作，这里介绍一下在java中byte数组和int之间的相互转换。

## 简介

int是32位，byte是8位，所以1个int可以转化为4个byte，同样4个byte也可以转化为1个int。

![img](https://raw.githubusercontent.com/xiejinjie/xiejinjie.github.io/gh-pages/assets/img/20221103223054.png)

我们会遇到一个问题，就是4个byte实际可以有不同的排列方式。在计算机中有两种存放模式，即大端模式和小端模式。

- 大端模式

  大端模式，是指数据的高字节保存在低地址中，而数据的低字节保存在内存的高地址中。这种方式更符合直观感受，地址从低到高排列，数据从高到低放。一般网络协议中使用的是大端存储。

- 小端模式

  小端模式（Little-endian），是指数据的高字节保存在高地址中，而数据的低字节保存在内存的低地址中。

比如，0x12345678使用两种不同的方式存放：

![](https://raw.githubusercontent.com/xiejinjie/xiejinjie.github.io/gh-pages/assets/img/20221117225035.png)

## 转换逻辑

我们以大端模式为例，介绍具体的转换逻辑。

- int转byte数组

  需要将int分为4个byte，存入数组。在java中可以将int强制转换为byte，截取int的低8位。通过对int进行右移位，就可以截取int的不同部分。

- byte数组转int

  需要将4个byte填入int中。大端模式：通过int与byte按位或运算，将byte放到int的低8位。再将int进行左移位，放入下一个byte；小端模式，将byte左移位之后，与int按位或运算，放入int中不同位置。
  注意的是，byte在与int运算过程会自动转换为int，如果byte符号位是1时，便会用1补齐所缺的位。所以需要将byte和0xFF按位与运算，获得与byte位相同的int，再进行之后运算。