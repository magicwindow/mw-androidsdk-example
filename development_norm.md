魔窗demo app项目
===
我们需要遵循一定的开发规范。创建的java文件需要加上自己的`@author`，以及该文件的描述和用途。

##命名规则

###常用的package
* com.zxinsight.magicwindow.activity:存放各个Activity
* com.zxinsight.magicwindow.adapter:存放各个Adapter
* com.zxinsight.magicwindow.app:存放一些基类比如BaseActivity、BaseFragment、DemoApp等
* com.zxinsight.magicwindow.config:存放APIConstant、Config等
* com.zxinsight.magicwindow.fragment:存放各个Fragment
* com.zxinsight.magicwindow.ui:存放自定义的组件
* com.zxinsight.magicwindow.ui.dialog:存放自定义的dialog组件
* com.zxinsight.magicwindow.utils:存放各个常用的Utils

###layout布局命名
* activity的布局以activity_开头
* fragment的布局以fragment_开头
* dialog的布局以dialog_开头
* listview、gridview等中的item的布局以cell_开头
* include的布局以layout_开头

###layout的id命名
使用小驼峰法，方便使用InjectView的规定大于配置

###style命名
style名称统一按照AppBaseTheme这种风格命名
 
###activity 和 fragment 
命名规范名称统一按照xxxxActivity 或者xxxxxFragment来  
@InjectView在activity、fragment里尽量使用约定大于配置的方法，如果代码中的组件名称跟layout中要注入的组件id相同，则无需写(id=R.id.xxxx)

##开发规范
* adapter尽量使用SAF里面的SAFAdapter
* 日志尽量使用SAF的日志框架L

##日志规范
* 日志尽量使用L类,并且使用debug的相关方法
* 开发、测试、预发布环境可以看到全部的日志
* 生产环境看不到http请求和body体内容
* 生产环境可以通过进入工程模式看到全部日志
* 在左侧菜单栏多次点击版本号可以进入工程模式

##第三方库
* SAF