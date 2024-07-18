#!/bin/bash

# Define the directory containing the files
test_dir="phase2_testfiles_v1/files"
output_dir="output"

# Define your Java command (replace with your actual command)
java_command="java -jar cs8803_bin/tigerc.jar -f"  # Assuming -f takes the filename with path
java_test_command="java -jar phase2_testFiles_v1/tigerc-ir.jar -r "

# Loop through all files in the src directory (excluding hidden ones)
for file in "$test_dir/"*.tiger ; do
  # Check if it's a regular file (not a directory or hidden file)
  if [[ -f "$file" ]]; then
    # Construct the full filename with path
    filename="$file"

    # Run the Java command with the full path filename and -i flag after
    echo "Running: $java_command $filename -i"  # Add echo for demonstration
    $java_command "$filename" -i
  fi
done

echo "Finished processing .tiger files in $test_dir"

# Check if the output folder exists and create a unique folder name if it does
FOLDER_NAME="$output_dir"
COUNT=1
while [ -d "$test_dir/$FOLDER_NAME" ]; do
    FOLDER_NAME="${output_dir}${COUNT}"
    COUNT=$((COUNT + 1))
done

mkdir -p "$test_dir/$FOLDER_NAME"

find $test_dir -maxdepth 1 -type f -name "*.ir" -exec mv {} "$test_dir/$FOLDER_NAME" \;

echo "All .ir files have been moved to $test_dir/$FOLDER_NAME"

#for irfile in "$test_dir/$FOLDER_NAME"*.ir ; do
#  # Check if it's a regular file (not a directory or hidden file)
#  if [[ -f "$irfile" ]]; then
#    # Construct the full filename with path
#    filename="$irfile"
#
#    # Run the Java command with the full path filename and -i flag after
#    echo "Evaluate IR: $java_test_command $filename "  # Add echo for demonstration
#    $java_test_command "$filename"
#  fi
#done
#
#echo "Finished processing .ir files in $test_dir/$FOLDER_NAME"

