from Lib.tokenizer import tokenizer, tokenizer_porter


text = 'runners like running and thus they run'
print(tokenizer(text))
print(tokenizer_porter(text))