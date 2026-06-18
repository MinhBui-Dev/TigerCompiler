#!/bin/bash

# Assign arguments to variables for better readability
file1="Tiger.g4"
file2="Makefile"
file3="phase3_design.pdf"
file4="phase3_analysis.pdf"
folder="src"
output_zip="mbui34"

# Create the zip archive
zip -r "$output_zip" "$file1" "$file2" "$file3" "$file4" "$folder"

# Check if the zip command was successful

echo "Successfully created $output_zip with $file1, $file2, $file3, $file4 and $folder."
