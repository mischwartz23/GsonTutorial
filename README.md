% Title: README
% Author: M I Schwartz
% Date: 2021-12-01

# Introduction

This is a brief tutorial on the use of JSON in object-oriented uses of Java using Google's Gson library..

# A work about JSON

JSON is a popular data format for data exchange between applications.

It stands for JavaScript Object Notation, though nowadays there are libraries in every language which can easily manipulate JSON objects.

Java used to be more difficult to use with JSON than other languages; Google's Gson library is intended to make this easy.

This tutorial is geared to establishing a working knowledge of the Gson library, which reads and writes JSON.

You will note the examples are lacking in getters, setters, costructors, and other niceties of Java classes.

This is done deliberately in order to strip th Gson capabilities to their most basic, and avoid "magic" invocations based on false assumptions about the library.

I welcome suggestions on simplifications and improvements, as well as non-obscure extensions for common situations an entry-level programmer is likelyl to encounter.

# Notes and references:

Here are resources I used to establish the approach in the code for this tutorial.

https://github.com/google/gson/blob/master/UserGuide.md

# Assumptions

I assume the reader has basic Java knowledge, and can build small applications.

I assume the reader has access to the code that accompanies this tutorial; only snippets or redactions are copied into the tutorial text below.

I assume the reader has a basic understanding of JSON, and can find syntax errors in a JSON literal if one is encountered.

I am assuming use of Java in the Netbeans IDE for my illustrations; the provided code assumes Maven (and thus a `pom.xml` file for dependencies). I believe this easily translates to a non-Maven implementation if needed, by including the Gson jar file as appropriate to the desired target environmnent.

I assume a current Java compiler. I believe any compiler beyond version 11 can handle the occassional multi-line literal and other such constructs in the code. I believe the small number of these extensions can be easily removed, and the code base can readily be converted to version 8 if needed.

I do not use lambdas in this exposition.

