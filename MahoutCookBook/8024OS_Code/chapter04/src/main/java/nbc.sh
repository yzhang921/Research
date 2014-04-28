Study/Workspaces/Research/MahoutCookBook/8024OS_Code/chapter04/input


./mahout seq2sparse -i input/mahoutck/04/news-se -o input/mahoutck/04/newsvectors -lnorm -nv -wt tfidf


/mahout split \
-i input/mahoutck/04/newsvectors/tfidf-vectors \
--trainingOutput output/mahoutck/04/news-train-vectors \
--testOutput output/mahoutck/04/news-test-vectors \
--randomSelectionPct 40 --overwrite --sequenceFiles -xm sequential


mahout trainnb \
-i output/mahoutck/04/news-train-vectors -el \
-o output/mahoutck/04/model \
-li output/mahoutck/04/labelindex \
-ow 

mahout testnb \
--input output/mahoutck/04/news-test-vectors \
-m output/mahoutck/04/model \
--labelIndex output/mahoutck/04/labelindex \
--output output/mahoutck/04/20news-testing


hdfs dfs -text input/mahoutck/04/newsvectors/dictionary.file-0 | more

# Failed
mahout vectordump -i input/mahoutck/04/newsvectors/tfidf-vectors/part-r-00000 -o output/mahoutck/04/newsvectors/tfidf-vectors/