## byte数组 和 int相互转换
int从高到低有32位，byte有8位，一个int可以长度为4的byte数组互转。
![](https://raw.githubusercontent.com/xiejinjie/xiejinjie.github.io/gh-pages/assets/img/20221103223054.png)


int转byte数组，我们需要从高到低取出4组二进制数，依次放入byte数组。java中可以通过强制转换，将int强制转换为byte，取出int的低8位。这样，我们通过右移位运算符，便可以取出int的4组二进制数了。

byte数组转int，我们需要数值中4个byte从高到低放到int中。通过按位或运算，可以将byte中的二进制数，放到int的低8位。但是java中按位或运算时会自动将byte自动转换为int，并用符号位补齐所缺的位。当byte第一位是1时，便会用1补齐前面的位，计算结果就会出错。所以我们将byte先和0xFF按位与运算，将可能用1补齐的位还原，之后与int按位或运算将byte中的数放到int中。将int进行左移8位后，便可继续放入下一个byte。