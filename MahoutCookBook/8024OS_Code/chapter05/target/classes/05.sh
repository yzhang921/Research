http://ichart.yahoo.com/table.csv?s=BAS.DE&a=0&b=1&c=2000%20&d=0&e=31&f=2010&g=w&ignore=.csv



export WORK_DIR=`pwd`
mkdir $WORK_DIR/train
mkdir $WORK_DIR/test
mkdir $WORK_DIR/model


wget --output-document=google.csv http://ichart.finance.yahoo.com/table.csv?s=BAS.DE


cut -d , -f 2-7 google.csv > training.csv

# run App add a new action column

mahout trainlogistic \
--input $WORK_DIR/train/final.csv \
--output $WORK_DIR/model/model \
--target action \
--predictors Open Close High \
--types word \
--features 20 \
--passes 100 \
--rate 50 \
--categories 2

mahout runlogistic --input $WORK_DIR/train/final.csv --model $WORK_DIR/model/model \
--auc --confusion


