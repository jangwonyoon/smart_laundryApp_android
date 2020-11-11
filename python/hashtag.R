install.packages("rJava")
install.packages("memoise")
install.packages("remotes")
remotes::install_github('haven-jeon/KoNLP',upgrade="never",INSTALL_opts=c("--no-multiarch"))
install.packages("stringr")
install.packages("tidyverse")

library(rJava)
library(memoise)
library(KoNLP)
library(dplyr)
library(stringr)
library(tidyverse)

useNIADic()

dp <- read.csv("example.CSV", quote = "", stringsAsFactors = FALSE, header = TRUE)

names(dp) <- c("ID","review","score")
dp

negative_review <- dp %>% filter(score < 8) %>% select(review)
positive_review <- dp %>% filter(score > 7) %>% select(review)

negative_review
positive_review

SimplePos09('최대한 친절하게 쓴 R')

i=0

for (i in 100) {
  neg <- SimplePos09(negative_review$review)
}
neg

for (i in 100) {
  pos <- SimplePos09(positive_review$review)
}
pos


##################################여기서부터 다시 해야됩니다. 단어 뽑은 것을 명사화 해서 빈도수 검출한 후 필요없는 단어들은 빼고 키워드화 시키기##################

for (i in 100) {
  neg2 <- str_match(neg,'([가-힣]+)/[NP]')
}
neg2

for (i in 100) {
  pos2 <- str_match(pos,'([가-힣]+)/[NP]')
}
pos2

real_neg <- neg2[,2]
real_pos <- pos2[,2]

neg_text <- real_neg[!is.na(real_neg)]
pos_text <- real_pos[!is.na(real_pos)]

neg.df <- data.frame(neg_text,stringsAsFactors = F)
pos.df <- data.frame(pos_text,stringsAsFactors = F)

write.csv(neg.df, file="neg_df.csv")
write.csv(pos.df, file="pos_df.csv")

####################키워드 추출##############################

table(neg.df$neg_text) %>% head(10)











