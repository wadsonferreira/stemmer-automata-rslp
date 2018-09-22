# RSLP Automata Stemmer

This projects implements the automata-based version of RSLP algorithm for apply the stemming process in words of the brazilian portuguese language.

The code was developed following the instructions described in the article [Assessing the Efficiency of Suffix Stripping Approaches for Portuguese Stemming](https://dl.acm.org/citation.cfm?id=2952670). A copy of the article can be found at the folder 'article' of this project.

## Getting Started

To use the code, just call the function apply and pass the word to be processed

```java
import stemmer.rslp.RSLPAutomata;

public class Stemming {

	public Stemming() {
		RSLPAutomata rslp = new RSLPAutomata();
		String result = rslp.apply("word-to-be-processed", RSLPAutomata.REMOVE_ACCENTS);
	}
	
}
```

### Dependencies

There is no dependencies for this project

## Documentation

### Articles
suffix-stripping-approaches-paper.pdf
The article [Assessing the Efficiency of Suffix Stripping Approaches for Portuguese Stemming](https://dl.acm.org/citation.cfm?id=2952670) can be found at the folder 'article'.

The suffix stripping rules used in class RSLPSuffixStrippingRules were obtained in the article [A Stemming Algorithm for the Portuguese Language](https://ieeexplore.ieee.org/document/989755/)

The exception lists used in class RSLPException were obtained in the monograph [Stemming para a língua portuguesa: estudo, análise e melhoria do algoritmo RSLP](https://lume.ufrgs.br/handle/10183/23576)

### Visual representation of the algorithm

![RSLP automata-based algorithm](automatas/rslp-automata-algorithm.png)

A series of images (*.png) describing each part of the algorithm can be found at the folder 'automatas' of this project. The automatas images can be generate with the software [Graphviz](http://graphviz.org/) with the *.gv files.