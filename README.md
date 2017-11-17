# Sorani Kurdish PoS-tagger

The process of classifying words into their parts of speech and labeling them accordingly is known as part-of-speech (PoS) tagging, or grammatical tagging. In the current project, we build, to the best of our knowledge, the first accurate automatic tagger for a less-resourced language, [Sorani Kurdish](https://en.wikipedia.org/wiki/Central_Kurdish).

We can consider the task of tagging in two levels:

  * _morphological tagging_ which is about the tags of the morphological parts of a word. For example, "hełhatim" = "heł" → verbal suffix, "hat" → verb stem, "im" → 1 st person verb ending.
  * _syntactic tagging_ which concerns each word of a phrase. For instance, "baran debarê" = "baran" → noun, "debarê" → verb.

We have already collected the morphological rules of Sorani Kurdish and the grammatical tags. This will partially solve the problem of morphological tagging. However, in order to create accurate taggers, manually tagged corpus are essential. In this stage of the project, we are aiming at creating a manually tagged corpus that will enable us to tackle more challenges in Kurdish language processing. Therefore, __we are constantly looking for research volunteers interested in Sorani Kurdish annotation for the task of annotation.__

### Methodology

More information will be provided soon.

### Requirements
  * Python 2.7

### Reference
If you're using the tagger in your researches, please don't forget to cite our paper. 

~~~
(To appear) Sina Ahmadi, Jalal Sajadi, ”A Kurdish Part-Of-Speech Tagger Based on Morphological Analysis” (under preparation).
~~~

This helps us to also find your work easily.

### License

MIT License

Copyright (c) 2017 Sina Ahmadi and Jalal Sajadi.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

### Contact

If you have any questions, suggestions or bug reports, you can contact me at sina.ahmadi at etu.parisdescartes.fr. 