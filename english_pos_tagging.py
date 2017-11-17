# -*- coding: utf-8 -*-
"""
Created on Thu Jul 20 15:03:36 2017

@author: sina
"""
import codecs
import nltk
from nltk.stem import WordNetLemmatizer
import pickle
import os

def get_number_tokens(dic):
    number = 0
    for tag in dic:
        number+= len(dic[tag].split("|"))
    return number
#==============================================================================
# We create a pos-tagged gold-standard corpus for Kurdish using English pos tags.
# strucutre of the tuples:
#   word_kurdish      word_english
#   word_kurdish1, word_kurdish2    word_english => deux entrées ssi word1 et word2 ont pas une taille >1 quand on enlève les espaces
#==============================================================================

if(not os.path.isfile('tag_word_sorani_dic_trnslt.pickle')):
    input_file = [line.strip() for line in codecs.open("./basic_dictionary/dummy_english_kurdish_lexicon_transliterated.txt", "r", "utf-8")]
    
    normalized_lexicon = list()
    tag_word_dic = dict() # words = keys
    word_tag_dic = dict()
        
    for entry in input_file:
        source_token = (entry.split("\t")[1]).strip() # Kurdish
        target_token = entry.split("\t")[0].strip() # English
        if(" " not in target_token):
            if(u"،" in source_token):
                source_tokens = source_token.split(u"،")
                for source_token_item in source_tokens:
                    if(" " not in source_token_item.strip()):
                        normalized_lexicon.append(["".join(source_token_item.split()), target_token])
            else:
                if(" " not in source_token):
                    normalized_lexicon.append([source_token, target_token])
    #==============================================================================
    #   Lemmatization and pos tagging of target tokens (English)  
    #==============================================================================
    wordnet_lemmatizer = WordNetLemmatizer()
    for lexique in normalized_lexicon: 
        source_token = lexique[0]  
        target_token = lexique[1]
        
        lemmatized_token = [wordnet_lemmatizer.lemmatize(target_token)]
        tagged_token = nltk.pos_tag(lemmatized_token)
        if(source_token not in word_tag_dic.keys()):
            word_tag_dic[source_token] = tagged_token[0][1]
        else:
            if(tagged_token[0][1] not in word_tag_dic[source_token]):
                word_tag_dic[source_token] = word_tag_dic[source_token] + "|"+ tagged_token[0][1]
        if(tagged_token[0][1] not in tag_word_dic.keys()):
            tag_word_dic[tagged_token[0][1]] = source_token
        else:
            if(source_token not in tag_word_dic[tagged_token[0][1]]):
                tag_word_dic[tagged_token[0][1]] = tag_word_dic[tagged_token[0][1]] + "|"+ source_token
                
        
    with open('tag_word_sorani_dic_trnslt.pickle', 'w') as var_file:  
        pickle.dump([word_tag_dic, tag_word_dic], var_file)
        print "Lexicon pickled"
else:
    print "Lexicon unpickled"
    with open('tag_word_sorani_dic_trnslt.pickle') as var_file:  
        word_tag_dic, tag_word_dic = pickle.load(var_file)
#==============================================================================
#         Subcategorization based on the tags
#==============================================================================
os.system("rm -r tagsets")
os.makedirs("tagsets")
#        Semi-lemmatization of the words by removing different forms of words (and keeping shorter lenght and different tags)
tag_set = list()
for lexique in word_tag_dic.values():
    lexique = lexique.split("|")
    for item in lexique:
        if(item not in tag_set):
            tag_set.append(item)
            
simple_tag_set = ['NN', 'RB', 'VB', 'JJ', 'NNP', 'IN', 'CD', 'VBN', 'DT', 'PRP', 'JJS', 'PRP$', 'RBR', 'JJR', 'UH', 'CC']

for tag in simple_tag_set:
    output_file = codecs.open("tagsets/"+tag+".txt", "w", "utf-8")
    output_file.write("\n".join(sorted(set(tag_word_dic[tag].split("|")))))
    output_file.close()

other_tags = ""
for tag in tag_word_dic:
    if(tag not in simple_tag_set):
        other_tags = other_tags + tag_word_dic[tag]
output_file = codecs.open("tagsets/ZOTHERSZ.txt", "w", "utf-8")
output_file.write("\n".join(sorted(set(other_tags.split("|")))))
output_file.close()

#==============================================================================
# Basic tags:
# NN 	noun, singular or mass 	tiger, chair, laughter 
# RB 	adverb 	extremely, loudly, hard 
# VB 	verb, base form 	think 
# JJ 	adjective 	nice, easy 
# NNP 	noun, proper singular 	Germany, God, Alice 
# IN 	conjunction, subordinating or preposition 	of, on, before, unless 
# CD 	cardinal number 	five, three, 13%
# VBN 	verb, past participle 	a sunken ship 
# VBG 	verb, gerund or present participle 	thinking is fun 
# DT 	determiner 	the, a, these 
# PRP 	pronoun, personal 	me, you, it 
# PRP$ 	pronoun, possessive 	my, your, our 
# JJS 	adjective, superlative 	nicest, easiest 
# RBR 	adverb, comparative 	better 
# JJR 	adjective, comparative 	nicer, easier
# UH 	interjection 	oh, oops, gosh 
# CC 	conjunction, coordinating 	and, or, but
# 
# Compound tags:
# 
# NNS 	noun, plural 	tigers, chairs, insects 
# WRB 	wh-adverb 	where, when 
# VBD 	verb, past tense 	they thought 
# EX 	existential there 	there were six boys 
# MD 	verb, modal auxillary 	may, should 
# FW 	foreign word 	mais 
# WP 	wh-pronoun, personal 	what, who, whom 
# WDT 	wh-determiner 	which, whatever, whicheve
#==============================================================================

        
