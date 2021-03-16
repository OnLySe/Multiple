## 规范



### ConvertFactory

Android目前有两个比较好的ConvertFactory：

- Gson
- Moshi

其中Gson用得比较多，而Moshi是今年（2021年）才知道，当然这个框架已经推出很多年了。为了避免后续可以更加方便相互替换，无论是从gson转moshi，还是moshi转gson，都只需要更改最少就能达到目的。那么就需要封装！

