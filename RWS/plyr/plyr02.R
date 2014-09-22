# splat()
head(mtcars,5)
hp_per_cyl <- function(hp, cyl, ...) hp / cyl
splat(hp_per_cyl)(mtcars[1,])
splat(hp_per_cyl)(mtcars)


# m*ply(a_matrix, FUN)的作用和a*ply(a_matrix，1，splat(FUN))一样
data=data.frame(n=c(10,100,50),mean=c(5,5,10),sd=c(1,2,1));data
mlply(data, rnorm)
alply(data, 1, splat(rnorm))




# each()
#fun<-function(x) c(min = min(x), max = max(x),mean=mean(x))
# 与each(min,max,mean)的作用相同
a=c(1,2,3,4,1,5,7,8,9,4,2)
each(min,max,mean)(a)
each(length,mean,var)(rnorm(100))
fun<-function(x) c(min = min(x), max = max(x),mean=mean(x))
fun(a)


# colwise(.fun, .cols, …)
head(baseball,5)
nmissing=function(x)sum(is.na(x))
colwise(nmissing)(baseball)
f=colwise(nmissing) # 封装一下
f(baseball)

# 只作用于指定列
ddply(baseball,.(year),colwise(nmissing))
ddply(baseball,.(year),colwise(nmissing,.(sb,cs,so)))
ddply(baseball,.(year),colwise(nmissing,c("sb","cs","so")))
ddply(baseball,.(year),colwise(nmissing,~sb+cs+so))

# 只作用于指定类型列
ddply(baseball,.(year),colwise(nmissing,is.character))
ddply(baseball,.(year),colwise(nmissing,is.numeric))
ddply(baseball,.(year),colwise(nmissing,is.discrete))
ddply(baseball,.(year),numcolwise(nmissing))
ddply(baseball,.(year),catcolwise(nmissing))


# failwith()函数
# failwith(default=NULL, f, quiet=FALSE)
f=function(x)if(x==1)stop("Error!")else 1
f(1)
f(2)
safef=failwith(NULL,f )
safef(1)
safef(2)
safef=failwith(NULL,f,quiet=TRUE)
safef(1)
safef(2)


# arrange(df , .(var1), .(var2)…)
# 作用：按照列给数据框排序
mtcars
arrange(mtcars,cyl,disp)
arrange(mtcars,disp,cyl)
# 排完序只有没有row names了

# 列名加到新的列里面去
cars=cbind(vehicle=row.names(mtcars), mtcars)
arrange(cars,cyl,disp)
arrange(cars,cyl,desc(disp)) # 降序排列


# rename(x, replace, warn_missing=TRUE)
#  作用：通过名字修改名字，而不是根据它的位置
x=c("a"=1,"b"=2,"c"=3,"d"="c")
x
rename(x,replace=c("b"="c"))
rename(x,replace=c("c"="e "))
rename(x,replace=c("e"="c"))
rename(x,replace=c("e"="c"),warn_missing=FALSE)

# count(df， vars=NULL, wt_var=NULL)
#  作用：数变量中观测值出现的个数
a=data.frame(
  names=c("a","b","c","d","a","a","a","b","b","c"),
  wt=c(1,1,1,1,2,2,2,2,2,2)
)
a
count(a,vars="names")
count(a,vars="names", wt_var="wt")# 权重
count(a,"names","wt")
count(a,c("names","wt"))


# match_df(x, y, on=NULL)
#　 作用：从一个数据框中提取与另一个数据框中相同的行

count(baseball,"id")
longterm=subset(count(baseball, "id"), freq>25);longterm
bb_longterm=match_df(baseball, longterm, on="id"); bb_longterm


# join(x, y, by=NULL, type=“left”, match=“all”)
# 作用：联合两个数据框
# 参数：x，y是两个数据框；by是指定要联合的变量，默认值为所有的变量；
# type是指定联合的方式

x1=c(1,2,3,4)
x2=c(5,6,7,8)
x=data.frame(x1,x2);x
y1=x1*10
y=data.frame(y1,x2);y
y[,2]=c(1,2,6,7);y
join(x,y,by="x2")


join(x,y,"x2",type="inner")
join(x,y,"x2",type="right")
join(x,y,"x2",type="full")
y[,2]=c(6,6,6,6)
join(x,y ,"x2",type="inner",match="all")
join(x,y ,"x2",type="inner",match ="first")