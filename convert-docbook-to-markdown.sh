#!/usr/bin/env sh
echo "Convert docbook to markdown"
pandoc --from docbook --to markdown_strict publication/book.xml --output publication/book.md