# plyr01_excercise

书面作业：
1.对于R的内置数据集mtcars，利用plyr包的函数分别做以下操作：
1)把数据集按汽车的换挡方式"am"进行分组，分别计算自动挡和手动挡中各个变量的平均数，
要求分别做出输出格式为list，array和dataframe的结果；
答复：
dlply(mtcars, .(am), colwise(mean))
daply(mtcars, .(am), colwise(mean))
ddply(mtcars, .(am), colwise(mean))
dlply(mtcars, .(am), colwise(mean))
daply(mtcars, .(am), colwise(mean))
ddply(mtcars, .(am), colwise(mean))

2)把数据集按照车的重量"wt"分为三组（wt<=2;2<wt<=4; 4<wt）,分别计算不同车重范围的每英里
耗油量"mpg"，要求分别做出输出格式为list，array和dataframe的结果；
答复：
mtcars$dd<-mtcars$wt
attach(mtcars)
mtcars[which(wt <= 2), ]$dd <-"第一组"
mtcars[which(wt > 2 & wt<= 4), ]$dd <-"第二组"
mtcars[which(wt > 4), ]$dd <- "第三组"
mean_mpg <- function(x)mean(x$mpg)
dlply(mtcars, .(dd), mean_mpg)
daply(mtcars, .(dd), mean_mpg)
ddply(mtcars, .(dd), mean_mpg)

(3)把数据集按照汽车的换挡方式"am"和上面设置的车重范围”wt”进行分组，
计算不同换挡方式和车重范围的每英里耗油量"mpg"。
答复：
dlply(mtcars, .(am, dd),mean_mpg)
daply(mtcars, .(am, dd), mean_mpg)
ddply(mtcars, .(am, dd),mean_mpg)


2.对于R的内置数据集iris，把species这一列数据除去后，生成一个新的矩阵，计算
(1)按照列的方向的数值平均值，要求做出输出格式分别为list,array和dataframe的结果；
答复：
newiris <- as.matrix(iris[,-5])
alply(newiris, 2, mean)
aaply(newiris, 2, mean)
adply(newiris, 2, mean)

(2)按照行的方向的数值总和，要求做出输出格式分别为list,array和dataframe的结果。
答复：
alply(newiris, 1, sum)
aaply(newiris, 1, sum)
adply(newiris, 1, sum)