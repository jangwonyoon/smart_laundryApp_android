install.packages("rvest")
install.packages("stringr")

library(rvest)
library(stringr)

## 알라딘 네이버 평점 페이지 주소 ##
main_url = "https://movie.naver.com/movie/bi/mi/pointWriteFormList.nhn?code=163788&type=after&isActualPointWriteExecute=false&isMileageSubscriptionAlready=false&isMileageSubscriptionReject=false&page="

reply_list = character()
star_list = numeric()
date_list = character()



for(page_url in 1:10){
  
  url = paste(main_url, page_url, sep="")
  content = read_html(url)
  
  node_1 = html_nodes(content, ".score_reple p")
  node_2 = html_nodes(content, ".score_result .star_score em")
  node_3 = html_nodes(content, ".score_reple em:nth-child(2)")
  
  reply = html_text(node_1)
  star = html_text(node_2)
  date = html_text(node_3)
  date = as.Date(gsub("\\.","-", date))
  
  reply_list = append(reply_list, reply)
  star_list = append(star_list, star)
  date_list = append(date_list, date)
  
}

df = data.frame(reply_list, star_list, date_list)
colnames(df) = c("reply","rank","date")

write.csv(df, "aladin.csv", row.names = FALSE)