# MobApiSample
## 一个基于MobApi的示例应用；使用了Rxjava2，Retrofit2，Databinding三者结合开发。
## 天气动画控件是自定义开发的，考虑到动画复杂，优化性能的问题，天气动画的控件继承SurfaceView。由于SurfaceView是在一个新起的单独线程中重新绘制画面，使得动画效果更佳流畅，曾经继承ViewGroup，View，结果很惨，内存占用大，动画也不流畅，很卡。注:天气动画的灵感来源于小米天气。
![weathersample.png](https://github.com/FlyMyFish/MobApiSample/blob/master/app/samplepic/weather_sample.gif) 

## 2017-11-22添加了空气质量信息页面
![airQuality.gif](https://github.com/FlyMyFish/MobApiSample/blob/master/app/samplepic/airQuality.gif)  

## 2017-11-22添加了城市背景，并结合传感器优化动画效果
![citybg.gif](https://github.com/FlyMyFish/MobApiSample/blob/master/app/samplepic/citybg.gif)
