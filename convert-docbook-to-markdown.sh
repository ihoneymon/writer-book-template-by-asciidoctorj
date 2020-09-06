#!/usr/bin/env sh
echo "Convert docbook to markdown"
pandoc -f docbook -t markdown_strict publication/book.xml -o publication/book.md