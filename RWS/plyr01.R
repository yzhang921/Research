# 例子：对a的每列求均值
a=matrix(1:21,nrow=3,ncol=7)

b=0
for(i in 1:7)
{
  b[i]=mean(a[,i])
}
b

b=aaply(a,.margins=2,.fun=mean);b

r=ddply(baseball, .(year), function(df) mean(df[,6:9]))


# a*ply(.data, .margins, .fun, ..., .progress = "none ")   array    用Margin分组
# d*ply(.data, .variables, .fun, ..., .progress = "none ") dataframe用variables分组
# l*ply(.data, .fun, ..., .progress = "none ")

--- # a*ply(.data, .margins, .fun, ..., .progress = "none ") array 用Margin
a=matrix(1:21,nrow=3,ncol=7);a
aaply(.data=a,.margins=1,.fun=mean)
aaply(a,1,mean)
aaply(a,2,mean)
aaply(a,c(1,2),mean)


-- Progress
aaply(a,1,mean,.progress="none")
aaply(a,1,mean,.progress="text")

-- 2
names=c("John","Mary","Alice","Peter","Roger","Phyillis")
age=c(13,15,14,13,14,13)
sex=c("Male","Female","Female","Male","Male","Female")
data=data.frame(names,age,sex)

amean=function(data)
{
  agemean=mean(data[,2])
  return(agemean)
}

# d*ply(.data, .variables, .fun, ..., .progress = "none ") dataframe用variables分组
r<-daply(data, .(age), .fun=amean)
r<-daply(data, .(sex), .fun=amean)
r<-daply(data, .(age,sex), .fun=amean)
r<-ddply(data, .(sex), .fun=amean)
r<-dlply(data, .(sex), .fun=amean)
r<-aaply(a, 1, mean, .progress="win")


# l*ply(.data, .fun, ..., .progress = "none ")  默认分组
a=c(1,2,3,4,1,5,7,8,9,4,2)
b=c(1,2,5,7,6,4,8,7)
c=c(4,8,9,1,2,3,1)
list=list(a,b,c)
llply(list,mean)
laply(list,mean)
ldply(list,mean)


# m*ply()函数
# 把array或者dataframe的参数数值放进函数中，得到dataframe（mdply），array（
maply）或者list（mlply）
data=data.frame(n=c(10,100,50),mean=c(5,5,10),sd=c(1,2,1));data
mlply(data,rnorm)