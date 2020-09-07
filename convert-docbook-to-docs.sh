#!/usr/bin/env sh
echo "Convert docbook to MS word"
pandoc --resource-path publication --from docbook --to docx publication/book.xml --output publication/book.docx --highlight-style espresso