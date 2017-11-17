# -*- coding: utf-8 -*-
"""
Created on Tue Aug 15 00:53:53 2017

@author: sina
"""

import codecs
import glob
from nltk.probability import FreqDist
#word_freq = FreqDist(
path = "Pewan/Corpora/docs"

unique_words = list()

for filename in glob.glob(path+"/*.txt"):
    input_file = codecs.open(filename, "r", "utf-8").read()
    input_text = input_file.split()
    
    for token in input_text:
        if(token not in unique_words):
            unique_words.append(token)
    print len(unique_words)
    
        