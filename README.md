# Battle Dance #
## 基本介绍 ##
Battle Dance（战舞）是个人开发的一款基于物理模拟的 2D 格斗类游戏，通过跨平台的开源游戏框架 LibGDX 进行开发，通过其封装好的物理引擎 Box2D 的 JNI 接口进行游戏的物理模拟。代码分为安卓端和核心端，安卓端负责实现游戏的界面，核心端负责实现游戏的主要逻辑功能。  
![](https://github.com/IOSurfer/BattleDance/blob/main/imgs/%E6%BC%94%E7%A4%BA.gif) 
## 环境依赖 ##
Java 15.0.1  
Gradle 7.3.3  
Android API 29  
Android Gradle Plugin 4.0.0   
LibGDX 1.10.0
## 部署流程 ##
安装 Java 15.0.1 以及 Android API 29。使用 IDEA 或 Eclipse 等 IDE，IDE须支持Android Gradle Plugin 4.0.0，用对应 IDE 打开 build.gradle 文件，等待依赖配置完成即可。 
## 程序架构 ##
### 目录结构 ###
	BattleDance  
	│                
	├─android 安卓端代码  
	│   
	├─assets 游戏素材  
	│          
	├─core 核心代码                
	│                      
	└─gradle gradle包装器 
### 功能模块 ###
主活动模块的生命周期和应用的生命周期重叠，模块是 LibGDX 指定的程序入口，在这个模块中，我们进行界面的更换，该模块下辖三个界面，分别是游戏界面、关卡管理界面和开始界面。  
开始界面主要提供音乐服务支持，其子模块音乐服务模块会通过 Intent 启动 Service 来为游戏全程播放背景音乐。  
在关卡管理界面中，用户选择游戏关卡，通过 JSON 解析模块对关卡信息进行读写，并将关卡信息在下拉框模块中进行实现。  
在游戏界面中，进行游戏主模块初始化，进行游戏内容的显示，游戏主模块通过通信模块实现核心端代码与安卓端代码的通信，通过姿态检测模块检测用户的输入手势，通过物理引擎进行物理模拟，最后通过渲染模块将游戏画面绘制到屏幕上。  
![](https://github.com/IOSurfer/BattleDance/blob/main/imgs/%E5%8A%9F%E8%83%BD%E6%A8%A1%E5%9D%97%E5%9B%BE.png)  
### 业务逻辑 ###
在应用开始后，开启音乐服务，进入开始界面，通过点击退出按钮退出应用，点击开始按钮进入关卡选择界面。在关卡选择界面中，通过点击返回按钮返回开始界面，点击已解锁的关卡进入对应关卡的游戏界面。在游戏界面中，用户拖动手指控制小人行动，与敌人进行对战，其间，可以点击返回按钮返回关卡选择界面，等到一方死亡，则会显示提示信息。  
![](https://github.com/IOSurfer/BattleDance/blob/main/imgs/%E4%B8%9A%E5%8A%A1%E9%80%BB%E8%BE%91%E5%9B%BE.png) 
### 代码实现 ###
![](https://github.com/IOSurfer/BattleDance/blob/main/imgs/%E4%BB%A3%E7%A0%81%E6%9E%B6%E6%9E%84.png) 
#### 主活动 ####
主活动为 AndroidLauncher 类继承 LibGDX 给定的入口类 FragmentActivity ，并实现接口类 AndroidFragmentApplication.Callbacks 来支持 Fragment 的显示和替换。  
在主活动中我们将动态隐藏顶部状态栏和底部栏，同时隐藏应用标题，我们需要通过 getWindow 函数获取当前的窗口，通过窗口的getAttributes方法获取布局参数，将参数的成员 systemUiVisibility 设为` View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |View.SYSTEM_UI_FLAG_IMMERSIVE`  
对于底部栏，我们需要调用窗口的 getDecorView 方法获取装饰控件，通过 setSystemUiVisibility 方法为其设置参数 `View.SYSTEM_UI_FLAG_FULLSCREEN`。至于 ActionBar ，则通过 getActionBar 方法获取，并调用 hide 方法隐藏。  
#### 开始界面 ####
开始界面由类 StartFragment 实现， StartFragment 继承自 Fragment ，其布局包括背景图片，一个开始按钮和一个退出按钮，重载 onCreateView 方法以实现布局加载，重载 onActivityCreated 方法以获取按钮控件并为其加载监听器，开始按钮的监听事件为改变 Fragment ，通过 getActivity 方法获取当前活动，通过活动的 getSupportFragmentManager 方法获取 SupportFragmentManager ，通过 beginTransaction 创建 FragmentTransaction 类，调用 FragmentTransaction 类的 replace 方法加载新的 Fragment ，并通过 commit 方法进行提交。
#### 音乐服务 ####
音乐服务通过 MyMusicService 实现，其继承自 Service 类，拥有一个 MediaPlayer 类的成员，重载 onCreate 类完成 MediaPlayer 的初始化，重载 onDestroy 类暂停音乐播放。我们通过 Intent 来暂停和启动服务，通过重载 AndroidLauncher 的 onResume 和 onPause 方法来实现暂停和启动服务。
#### 关卡选择界面 ####
关卡选择界面通过 LevelSelectFragment 实现，LevelSelectFragment 继承自 Fragment ，其布局包括背景图片，一个 RecyclerView 以及一个返回按钮，重载 onCreateView 方法以实现布局加载，在其中完成 RecyclerView 的数据加载和按钮监听器的设置。
#### JSON解析 ####
JSON 解析由 MyJSON 类实现，其包含三个 String 参数 Level、isFinish、Score，分别存储关卡、关卡解锁和分数信息。其方法 parseJSONWithJSONObject 接受一个 String 类型的参数，通过 JSONObject 类将其中包含的数据解析，返回一个 ArrayList<MyJSON> 的结果。方法 saveToLocal 接受一个 ArrayList<MyJSON> 的参数，使用 File 类和 BufferedWriter 将 ArrayList<MyJSON> 的的结果写入文件。
#### 下拉框 ####
下拉框由一个 RecyclerView 实现，其成员的布局采用 level_item.xml ，其中包含三个横向排布的 TextView ，通过 LinearLayoutManager 的 setOrientation 方法，我们将其设置为竖直的线性布局，通过 RecyclerView 的 addItemDecoration 方法，我们为 RecyclerView 增加了一个装饰器，实现横线分割的效果。  
LevelAdapter 是对应的适配器，继承自 RecyclerView.Adapter<LevelAdapter.ViewHolder>，其有一个 List<MyJSON> 类的成员，还有一个 FragmentActivity 的成员，前着为适配器提供数据，后者在设置监听器的时候发挥作用。通过重载方法 onCreateViewHolder 完成布局加载，重载 onBindViewHolder 为控件设置数据，根据当前位置 MyJSON 的 isfinish 属性，我们决定是否为该项目添加监听器以及改变其背景图标，如果 isfinish 为 true，则使用对应控件的 setBackgroundResource 函数将其背景设为黑色，并设置一个监听器提供改变Fragment的效果。适配器拥有一个子类 ViewHolder ，继承自 RecyclerView.ViewHolder ，负责存储 RecyclerView.ViewHolder 中的控件。在创建 ViewHolder 时，使用 setIsRecyclable(false) 方法使其不可回收，以免其上的监听器出错。
#### 游戏界面 ####
游戏界面由 GameFragment 实现 GameFragment 继承 LibGDX 提供的 AndroidFragmentApplication 类，是负责加载游戏的 Fragment ，包含一个 MyGdxGame 类的成员。重载 onCreateView 方法以实现游戏加载，通过方法 initializeForView 将游戏画面转化为 View 。在 onActivityCreated 中，使用 MyGdxGame 的方法 setActionResovler 为游戏模块加载通信类 AndroidActionResovler。
#### 通信模块 ####
在核心代码端，提供名为 ActionResovler 的接口，提供抽象方法 changeFragment ，在安卓代码端，类 AndroidActionResovler 实例化接口，包含一个 FragmentActivity 类的成员和一个 MyGdxGame 类的成员，借此完成两者通信，重载 changeFragment 方法，使得核心代码运行时可以进行 Fragment 的取代。
#### 游戏主模块 ####
游戏主模块由类 MyGdxGame 类提供，继承了 LibGDX 提供的 ApplicationAdapter 类，其成员分为四类，一类是游戏中所用的 Music 和 Texture 资源,一类是游戏的状态信息，还有渲染所用的 Renderer，OrthographicCamera，SpriteBatch 以及游戏中设计的实体对象 World，Man，Enemy。  
其生命周期如图所示，在 create 阶段，对所有资源初始化，在 dispose 阶段，释放所用资源，在 render 阶段对对象进行更新，并渲染游戏画面。    
![](https://github.com/IOSurfer/BattleDance/blob/main/imgs/%E7%94%9F%E5%91%BD%E5%91%A8%E6%9C%9F.png)  
#### 渲染 ####
我们将渲染分为四个部分，分别是背景，前景，UI,控制器。通过调用 render 方法完成渲染。
背景渲染器 BackgroundRenderer 类， render 方法负责将画面以黑色填充，使用了 LibGDX 的 ScreenUtils.clear 方法。  
前景渲染器 ForegroundRenderer 类继承自 Box2D 的渲染器类 Box2DDebugRenderer，对一系列参数设置初始值，在 render 方法中将所有物理实体绘制在屏幕上。  
UI 渲染器 UiRenderer 负责渲染 UI 视图， render 方法绘制胜利/失败提示信息以及退出钮。
控制器渲染器 ControllerRenderer 类继承了 LibGDX 的图形渲染器 ShapeRenderer，render 方法负责更新玩家的状态和绘制控制器。  
四个渲染器的渲染顺序为背景渲染器>前景渲染器>UI 渲染器>控制器渲染器。  
#### 手臂控制与控制器 ####
控制器分左右手，其中大圆代表了人物手臂的活动范围，小圆代表了将前臂外端控制锚点移动到的位置，大圆圆心位置由初次触碰屏幕的位置决定，通过拖动手指，将小圆移动到大圆边界，大圆的也会随着小圆同步运动。当手指离开屏幕是，对应的控制器也会随之消失  
以左手为例，通过人物实时姿态和控制器状态，可以得到两对方向向量 v1(X01-X00,X01-Y00)，v2(X21-X20,Y21-X20) ,将两对向量归一化后作差，就得到了手臂运动的方向向量，使用 Box2D 提供的 Body 类的 applyForce 方法，将一个沿着该方向的力作用于手臂外端的控制锚点，就实现了手臂控制的效果。  
控制器的绘制由 ControllerRender 执行，而手臂挥舞则为 Man 的 public void wave(Vector2 v, Vector2 a, boolean side) 方法。      
![](https://github.com/IOSurfer/BattleDance/blob/main/imgs/%E4%BA%BA%E7%89%A9%E7%BB%93%E6%9E%84%E5%9B%BE.png)    
![](https://github.com/IOSurfer/BattleDance/blob/main/imgs/%E4%BA%BA%E7%89%A9%E5%A7%BF%E5%8A%BF.png)    
![](https://github.com/IOSurfer/BattleDance/blob/main/imgs/%E6%8E%A7%E5%88%B6%E5%99%A8%E8%AE%BE%E7%BD%AE.png)    
#### 手指姿态检测 ####
MyGestureListener 类实例化了 LibGDX 提供的 GestureDetector.GestureListener 接口，其对底层的传感器接口进行了封装，通过重载GestureListner下的各个方法来实 touchDown (触碰)，tap （点按），longPress （长按），fling （滑动），pan （拖动），zoom （放缩），pinch （双指拖动）等姿态的检测。  
我们根据当前的手势确定人物的行为，通过 MyGdxGame 的方法 setWave ，setStand 更新状态，并将手指在屏幕上的坐标传回 MyGdxGame 。对于左半屏的点按，只设置左半屏点按坐标的值，右半亦复如是。  
#### 碰撞检测 ####
MyContactListener 类实例化自Box2d提供的监听器类 ContactListener，其负责监听 World 对象中的刚体碰撞，通过重载 public void beginContact(Contact contact) 回调函数，我们得到 Contact 对象的碰撞对象A和B，通过 getUserData() 获取对应的用户信息，我们可以得到物理实体对应的 Limb 对象，并通过 Limb 对象来实现部位血量的加减。
#### 游戏AI ####
游戏的敌人类 Enemy 继承自 Man，成员中增加了状态机类 StateMachine<Enemy, EnemyState>，该类是 libgdx 提供的 ai 接口，在 update 方法中，通过调用自动机的 update 方法来更新敌人的状态。  
敌人增加了的四个方法： public boolean isUnderAttack()， public boolean isInRange() ，public boolean isHealthLow()，public boolean isManHealthLow()  来判断敌我状态，当然用户操作对象 Man 也是 Enemy 的成员之一，以此来获取用户的人物状态。
敌人增加了的四个方法： public void denfend()，public void escape()，public void range()，public void attack()  是用来控制敌人的行动的。  
敌人的状态类 EnemyState 是接口 State<Enemy> 的实例化，我们通过枚举类型来创建所有的状态，通过重载回调函数 public void enter(Enemy entity)，public void update(Enemy entity)，public void exit(Enemy entity)，public boolean onMessage(Enemy entity, Telegram telegram) 来为敌人的状态变更提供具体的行为。  
   
